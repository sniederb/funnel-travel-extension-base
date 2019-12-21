package ch.want.funnel.extension.util;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Encapsulates the JSON structure provided by Faces (via funnel.travel)
 */
public class ExtendedProfileDataAccessor {

    private final TravelerProfileDataFormat dataFormat;

    public ExtendedProfileDataAccessor(final JsonNode extendedProfileData) {
        dataFormat = getDataFormat(extendedProfileData);
    }

    private TravelerProfileDataFormat getDataFormat(final JsonNode extendedProfileData) {
        if (extendedProfileData.hasNonNull("funnelTravelerProfileDataFormat")) {
            final String profileClient = extendedProfileData.get("funnelTravelerProfileDataFormat").asText();
            switch (profileClient) {
            case "UmbrellaFacesClient":
                return new UmbrellaFacesProfileDataAccessor(extendedProfileData);
            }
        }
        return new NoopProfileDataAccessor();
    }

    public Optional<JsonNode> getPassport() {
        return dataFormat.getPassport();
    }

    /**
     * Get 'country' information from passport node
     *
     * @param passportNode
     * @return
     */
    public Optional<String> getCountry(final JsonNode passportNode) {
        return dataFormat.getCountry(passportNode);
    }

    public Optional<String> getPassportNumber(final JsonNode passportNode) {
        return dataFormat.getPassportNumber(passportNode);
    }

    public Optional<String> getPassportPlaceOfIssue(final JsonNode passportNode) {
        return dataFormat.getPassportPlaceOfIssue(passportNode);
    }

    public Optional<Date> getPassportExpiration(final JsonNode passportNode) {
        return dataFormat.getPassportExpiration(passportNode);
    }

    public Optional<JsonNode> getContactData() {
        return dataFormat.getContactData();
    }

    public Optional<String> getContactPhone(final JsonNode contactNode) {
        return dataFormat.getContactPhone(contactNode);
    }

    public Optional<Locale> getContactLocale(final JsonNode contactNode) {
        return dataFormat.getContactLocale(contactNode);
    }

    public Optional<String> getPrimaryEmail() {
        return dataFormat.getPrimaryEmail();
    }

    public Optional<Date> getBirthdate() {
        return dataFormat.getBirthdate();
    }

    public Optional<JsonNode> getCompanyData() {
        return dataFormat.getCompanyData();
    }

    public Optional<String> getCompanyName(final JsonNode companyNode) {
        return dataFormat.getCompanyName(companyNode);
    }

    public Optional<String> getCompanyStreet(final JsonNode companyNode) {
        return dataFormat.getCompanyStreet(companyNode);
    }

    public Optional<String> getCompanyStreet2(final JsonNode companyNode) {
        return dataFormat.getCompanyStreet2(companyNode);
    }

    public Optional<String> getCompanyPlace(final JsonNode companyNode) {
        return dataFormat.getCompanyPlace(companyNode);
    }

    public Optional<String> getCompanyZip(final JsonNode companyNode) {
        return dataFormat.getCompanyZip(companyNode);
    }

    public Optional<String> getCompanyCountry(final JsonNode companyNode) {
        return dataFormat.getCompanyCountry(companyNode);
    }

    public Optional<String> getCompanyPhone(final JsonNode companyNode) {
        return dataFormat.getCompanyPhone(companyNode);
    }
}
