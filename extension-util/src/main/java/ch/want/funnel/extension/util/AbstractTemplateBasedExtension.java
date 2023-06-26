/*
 * Created on 16 Jan 2019
 */
package ch.want.funnel.extension.util;

import java.io.IOException;
import java.io.StringWriter;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import ch.want.funnel.extension.model.Booking;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleDate;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

/**
 * Base implementation to process payloads using a Freemarker template. Templates are expected to be on the classpath, and in UTF-8
 * encoding.
 */
public abstract class AbstractTemplateBasedExtension {

    private static final Version VERSION_2_3_28 = new Version(2, 3, 28);
    private static Configuration freemarkerConfig;
    private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
    private static final XmlMapper XMLMAPPER = new XmlMapper();
    private static final String XML_DATE_PATTERN = "yyyy-MM-dd";

    protected Map<String, Object> parsePayloadToMap(final String payload) throws IOException {
        if (payload == null) {
            return Collections.emptyMap();
        }
        if (payload.startsWith("<")) {
            return parseXmlPayloadToMap(payload);
        }
        return parseJsonPayloadToMap(payload);
    }

    /**
     * Load the provided JSON into a Java map. Beware that this method does NOT
     * attempt any date/time conversion. Use {@link #convertPayloadDates(Map)} if value objects
     * need to be dates.
     *
     * @param payloadNode
     * @return
     */
    protected Map<String, Object> parsePayloadToMap(final JsonNode payloadNode) {
        return OBJECTMAPPER.convertValue(payloadNode, new TypeReference<Map<String, Object>>() {
        });
    }

    private Map<String, Object> parseJsonPayloadToMap(final String payload) throws IOException {
        final JsonNode payloadNode = OBJECTMAPPER.readTree(payload);
        return parsePayloadToMap(payloadNode);
    }

    private Map<String, Object> parseXmlPayloadToMap(final String payload) throws IOException {
        final JsonNode payloadNode = XMLMAPPER.readTree(payload);
        return XMLMAPPER.convertValue(payloadNode, new TypeReference<Map<String, Object>>() {
        });
    }

    @SuppressWarnings("unchecked")
    protected void convertPayloadDates(final Map<?, Object> payload) {
        for (final Map.Entry<?, Object> entry : payload.entrySet()) {
            if (entry.getValue() instanceof Map) {
                convertPayloadDates((Map<?, Object>) entry.getValue());
            } else if (entry.getValue() instanceof Collection<?>) {
                final Collection<?> items = (Collection<?>) entry.getValue();
                items.stream().forEach(item -> {
                    if (item instanceof Map) {
                        convertPayloadDates((Map<?, Object>) item);
                    }
                });
            } else if (entry.getValue() instanceof String) {
                getAsDate((String) entry.getValue()).ifPresent(entry::setValue);
            }
        }
    }

    private Optional<Date> getAsDate(final String candidate) {
        if ((candidate != null) && (candidate.length() == XML_DATE_PATTERN.length()) && (candidate.charAt(4) == '-')) {
            try {
                return Optional.of(new SimpleDateFormat(XML_DATE_PATTERN).parse(candidate));
            } catch (final ParseException ex) {
                // no-op, apparently not a date
            }
        }
        return Optional.empty();
    }

    /**
     * Load a template by resource name. Note that the template is cached internally already, so there
     * is no need for caching on the caller side.
     *
     * @param templateResourceName
     *            The full resource name, ie. incl. package path (even if template is in the same package as the caller)
     * @return a Freemarker template
     * @throws IOException
     *             If the {@code templateResourceName} is invalid
     */
    protected Template loadTemplate(final String templateResourceName) throws IOException {
        assertConfigurationPublished();
        return freemarkerConfig.getTemplate(templateResourceName);
    }

    /**
     * Parse a simple template from a string. Note that this method does NOT support template referencing.
     *
     * @param templateResourceName
     *            The full resource name, ie. incl. package path (even if template is in the same package as the caller)
     * @return a Freemarker template
     * @throws IOException
     *             If the {@code templateResourceName} is invalid
     */
    protected Template parseTemplate(final String template) throws IOException {
        assertConfigurationPublished();
        return new Template("adhoc-" + template.hashCode(), template, freemarkerConfig);
    }

    private void assertConfigurationPublished() {
        if (freemarkerConfig == null) {
            initConfigurationSynchronized();
        }
    }

    private synchronized void initConfigurationSynchronized() {
        if (freemarkerConfig == null) {
            freemarkerConfig = new Configuration(VERSION_2_3_28);
            freemarkerConfig.setClassForTemplateLoading(getClass(), "/");
            freemarkerConfig.setDefaultEncoding("UTF-8");
            freemarkerConfig.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            freemarkerConfig.setObjectWrapper(new CustomObjectWrapper());
            updateFreemarkerConfiguration(freemarkerConfig);
        }
    }

    /**
     * This method allows for changing the Freemarker configuration before it is 'published' internally.
     * Note that this method runs within a {@code synchronized} block.
     *
     * @param freemarkerConfiguration
     */
    protected void updateFreemarkerConfiguration(final Configuration freemarkerConfiguration) {
    }

    protected String render(final Template template, final Booking bookingData) throws IOException {
        try {
            final StringWriter result = new StringWriter();
            template.process(bookingData, result);
            return result.toString();
        } catch (final TemplateException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    protected String render(final Template template, final Map<String, Object> templateData) throws IOException {
        try {
            final StringWriter result = new StringWriter();
            template.process(templateData, result);
            return result.toString();
        } catch (final TemplateException e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * Freemarker has terrible support for Java 8 time. This class wraps those classes into a
     * freemarker {@link SimpleDate}.
     */
    private static class CustomObjectWrapper extends DefaultObjectWrapper {

        CustomObjectWrapper() {
            super(getCtorVersion());
        }

        @Override
        public TemplateModel wrap(final Object obj) throws TemplateModelException {
            if (obj instanceof LocalDateTime) {
                final Timestamp timestamp = Timestamp.valueOf((LocalDateTime) obj);
                return new SimpleDate(timestamp);
            }
            if (obj instanceof LocalDate) {
                final java.sql.Date date = java.sql.Date.valueOf((LocalDate) obj);
                return new SimpleDate(date);
            }
            if (obj instanceof LocalTime) {
                final Time time = Time.valueOf((LocalTime) obj);
                return new SimpleDate(time);
            }
            return super.wrap(obj);
        }

        private static Version getCtorVersion() {
            final Configuration configuration = new Configuration(VERSION_2_3_28);
            return configuration.getIncompatibleImprovements();
        }
    }
}
