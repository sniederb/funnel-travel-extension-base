/*
 * Created on 16 Jan 2019
 */
package ch.want.funnel.extension.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
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

    protected Map<String, Object> parsePayloadToMap(final String payload) throws IOException {
        if (payload == null) {
            return Collections.emptyMap();
        }
        if (payload.startsWith("<")) {
            return parseXmlPayloadToMap(payload);
        }
        return parseJsonPayloadToMap(payload);
    }

    private Map<String, Object> parseJsonPayloadToMap(final String payload) throws IOException {
        final JsonNode payloadNode = OBJECTMAPPER.readTree(payload);
        return OBJECTMAPPER.convertValue(payloadNode, new TypeReference<Map<String, Object>>() {
        });
    }

    private Map<String, Object> parseXmlPayloadToMap(final String payload) throws IOException {
        final JsonNode payloadNode = XMLMAPPER.readTree(payload);
        return XMLMAPPER.convertValue(payloadNode, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * Load a template by resource name. Note that the template is cached internally already, so there
     * is no need for caching on the caller side.
     *
     * @param templateResourceName
     *            The full resource name, ie. incl. package path (even if template is in the same package as the caller)
     * @return
     * @throws IOException
     */
    protected Template loadTemplate(final String templateResourceName) throws IOException {
        assertConfigurationPublished();
        return freemarkerConfig.getTemplate(templateResourceName);
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

    protected String render(final Template template, final Map<String, Object> templateData) throws IOException {
        try {
            final StringWriter result = new StringWriter();
            template.process(templateData, result);
            return result.toString();
        } catch (final TemplateException e) {
            throw new IOException(e.getMessage(), e);
        }
    }
}
