package ch.want.funnel.extension.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtendedProfileDataAccessorTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getPassport() throws Exception {
        // arrange
        final JsonNode json = getProfileJson();
        final ExtendedProfileDataAccessor testee = new ExtendedProfileDataAccessor(json);
        // act
        final Optional<JsonNode> passport = testee.getPassport();
        // assert
        assertTrue(passport.isPresent());
    }

    @Test
    public void getCountry() throws Exception {
        // arrange
        final JsonNode json = getProfileJson();
        final ExtendedProfileDataAccessor testee = new ExtendedProfileDataAccessor(json);
        // act
        final Optional<JsonNode> passport = testee.getPassport();
        assertTrue(passport.isPresent());
        final Optional<String> result = testee.getCountry(passport.get());
        // assert
        assertTrue(result.isPresent());
        assertEquals("CH", result.get());
    }

    @Test
    public void getContactData() throws Exception {
        // arrange
        final JsonNode json = getProfileJson();
        final ExtendedProfileDataAccessor testee = new ExtendedProfileDataAccessor(json);
        // act
        final Optional<JsonNode> contactData = testee.getContactData();
        // assert
        assertTrue(contactData.isPresent());
    }

    @Test
    public void getContactPhone() throws Exception {
        // arrange
        final JsonNode json = getProfileJson();
        final ExtendedProfileDataAccessor testee = new ExtendedProfileDataAccessor(json);
        // act
        final Optional<JsonNode> contactData = testee.getContactData();
        assertTrue(contactData.isPresent());
        final Optional<String> result = testee.getContactPhone(contactData.get());
        // assert
        assertTrue(result.isPresent());
        assertEquals("+41441234567", result.get());
    }

    @Test
    public void getCompanyData() throws Exception {
        // arrange
        final JsonNode json = getProfileJson();
        final ExtendedProfileDataAccessor testee = new ExtendedProfileDataAccessor(json);
        // act
        final Optional<JsonNode> companyData = testee.getCompanyData();
        // assert
        assertTrue(companyData.isPresent());
    }

    @Test
    public void getCompanyName() throws Exception {
        // arrange
        final JsonNode json = getProfileJson();
        final ExtendedProfileDataAccessor testee = new ExtendedProfileDataAccessor(json);
        // act
        final Optional<JsonNode> companyData = testee.getCompanyData();
        assertTrue(companyData.isPresent());
        final Optional<String> result = testee.getCompanyName(companyData.get());
        // assert
        assertTrue(result.isPresent());
        assertEquals("Umbrella AG", result.get());
    }

    private JsonNode getProfileJson() throws IOException {
        return objectMapper.readTree(this.getClass().getResourceAsStream("/faces-profile.json"));
    }
}
