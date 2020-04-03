package ch.want.funnel.extension.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;
import java.util.TimeZone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import ch.want.funnel.extension.model.Booking;
import ch.want.funnel.extension.model.Trip;

public class ExtensionDataAccessTest {

    private final ObjectMapper objectMapper;

    public ExtensionDataAccessTest() {
        objectMapper = new ObjectMapper()//
            .registerModule(new JavaTimeModule())//
            .setTimeZone(TimeZone.getTimeZone("UTC"))
            .setSerializationInclusion(Include.NON_NULL)
            .enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }

    @Test
    public void searchAllExtensionDataForRemark() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/N-");
        assertTrue(result.isPresent());
        assertEquals("VISA/N-DE", result.get());
    }

    @Test
    public void searchAllExtensionDataForRemark_withTattoo() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/E-", "1");
        assertTrue(result.isPresent());
        assertEquals("VISA/E-FOO.BAR@NOWHERE.COM", result.get());
    }

    @Test
    public void searchAllExtensionDataForRemark_withNonNumericTattoo() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/E-", "P1");
        assertFalse(result.isPresent());
    }

    @Test
    public void searchAllExtensionDataForRemark_withNullTattooOnRestrictedEntry() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/E-", null);
        assertFalse(result.isPresent());
    }

    @Test
    public void searchAllExtensionDataForRemark_withNullTattooOnUnrestrictedEntry() throws Exception {
        final Trip trip = getTrip();
        final Optional<String> result = ExtensionDataAccess.searchAllExtensionDataForRemark(trip, "VISA/N-", null);
        assertTrue(result.isPresent());
        assertEquals("VISA/N-DE", result.get());
    }

    @Test
    void setNode() throws Exception {
        final Booking booking = new Booking();
        final ExtensionDataAccess testee = new ExtensionDataAccess(booking);
        final ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("amount", 1234);
        // act
        testee.setNode("com.foobar.MyExtension", "test", node);
        // assert
        final JsonNode extensionData = booking.getExtensionData();
        Assertions.assertNotNull(extensionData);
        Assertions.assertFalse(extensionData.isNull());
        final JsonNode myExtensionNode = extensionData.get("com.foobar.MyExtension");
        Assertions.assertNotNull(myExtensionNode);
        Assertions.assertFalse(myExtensionNode.isNull());
        Assertions.assertTrue(myExtensionNode.has("test"));
    }

    @Test
    void setNodeWithNullNodeShouldReplace() throws Exception {
        final Booking booking = new Booking();
        booking.setExtensionData(JsonNodeFactory.instance.nullNode());
        final ExtensionDataAccess testee = new ExtensionDataAccess(booking);
        final ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("amount", 1234);
        // act
        testee.setNode("com.foobar.MyExtension", "test", node);
        // assert
        final JsonNode extensionData = booking.getExtensionData();
        Assertions.assertNotNull(extensionData);
        Assertions.assertFalse(extensionData.isNull());
    }

    @Test
    void getOrCreateNodeShouldReturnExistingNode() throws Exception {
        final Booking booking = new Booking();
        final ExtensionDataAccess testee = new ExtensionDataAccess(booking);
        final ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("amount", 1234);
        testee.setNode("com.foobar.MyExtension", "test", node);
        // act
        final JsonNode result = testee.getOrCreateNode("com.foobar.MyExtension", "test", true);
        // assert
        Assertions.assertNotNull(result);
        Assertions.assertSame(node, result);
    }

    @Test
    void getOrCreateNodeWithNonExistentNodeShouldCreateOne() throws Exception {
        final Booking booking = new Booking();
        final ExtensionDataAccess testee = new ExtensionDataAccess(booking);
        // act
        final JsonNode result = testee.getOrCreateNode("com.foobar.MyExtension", "test", true);
        // assert
        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isArray());
        final JsonNode extensionData = booking.getExtensionData();
        Assertions.assertNotNull(extensionData);
        Assertions.assertFalse(extensionData.isNull());
    }

    private Trip getTrip() throws IOException {
        return objectMapper.readValue(this.getClass().getResourceAsStream("/example-trip-with-remarks.json"), Trip.class);
    }
}
