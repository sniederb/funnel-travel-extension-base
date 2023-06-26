/*
 * Created on 16 Jan 2019
 */
package ch.want.funnel.extension.util;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Map;

import org.junit.jupiter.api.Test;

import freemarker.template.Template;

class JsonTemplateBasedExtensionTest {

    private static final String PAYLOAD = "{\"name\":\"John\",\"city\":\"London\"}";

    @Test
    void parsePayloadToMap() throws Exception {
        // arrange
        final AbstractTemplateBasedExtension testee = new JsonTemplateBasedExtension();
        // act
        final Map<String, Object> result = testee.parsePayloadToMap(PAYLOAD);
        // assert
        assertEquals("John", result.get("name"));
    }

    @Test
    void loadTemplate() throws Exception {
        // arrange
        final AbstractTemplateBasedExtension testee = new JsonTemplateBasedExtension();
        // act
        final Template template = testee.loadTemplate("ch/want/funnel/extension/util/test-template.ftlh");
        // assert
        assertNotNull(template);
    }

    @Test
    void render() throws Exception {
        // arrange
        final AbstractTemplateBasedExtension testee = new JsonTemplateBasedExtension();
        final Map<String, Object> payloadMap = testee.parsePayloadToMap(PAYLOAD);
        payloadMap.put("entrydate", LocalDate.parse("2023-10-04"));
        final Template template = testee.loadTemplate("ch/want/funnel/extension/util/test-template.ftlh");
        // act
        final String result = testee.render(template, payloadMap);
        // assert
        assertThat(result, containsString("Please be aware that John will be traveling to London"));
        assertThat(result, containsString("04.10.2023"));
    }

    private static class JsonTemplateBasedExtension extends AbstractTemplateBasedExtension {
    }
}
