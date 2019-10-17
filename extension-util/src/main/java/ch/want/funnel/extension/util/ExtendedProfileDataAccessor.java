package ch.want.funnel.extension.util;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Encapsulates the JSON structure provided by Faces (via funnel.travel)
 */
public class ExtendedProfileDataAccessor {

    private final JsonNode extendedProfileData;

    public ExtendedProfileDataAccessor(final JsonNode extendedProfileData) {
        this.extendedProfileData = extendedProfileData;
    }

    /**
     * Search {@code data -> papers -> passports [primary == true]}
     */
    public Optional<JsonNode> getPassport() {
        final JsonNode passportsNode = Optional.ofNullable(extendedProfileData.get("data"))
                .map(data -> data.get("papers"))
                .map(papers -> papers.get("passports"))
                .orElse(null);
        JsonNode result = null;
        if (passportsNode != null) {
            for (final JsonNode passport : passportsNode) {
                if (passport.get("primary").asBoolean(false) || (result == null)) {
                    result = passport;
                }
            }
        }
        return Optional.ofNullable(result);
    }

    public Optional<String> getCountry(final JsonNode passportNode) {
        return Optional.ofNullable(passportNode)
                .map(json -> json.get("country"))
                .map(JsonNode::asText);
    }

    /**
     * Search {@code data -> generalData}
     */
    public Optional<JsonNode> getContactData() {
        return Optional.ofNullable(extendedProfileData.get("data"))
                .map(data -> data.get("generalData"));
    }

    /**
     * {@code contactNode} is obtained using {@link #getContactData()}
     */
    public Optional<String> getContactPhone(final JsonNode contactNode) {
        return Optional.ofNullable(contactNode)
                .map(json -> json.get("businessPhone"))
                .map(JsonNode::asText);
    }

    /**
     * Search {@code data -> company}
     */
    public Optional<JsonNode> getCompanyData() {
        return Optional.ofNullable(extendedProfileData.get("data"))
                .map(data -> data.get("company"));
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyName(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("name"))
                .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyStreet(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("street"))
                .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyStreet2(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("street2"))
                .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyPlace(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("place"))
                .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyZip(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("zipCode"))
                .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyCountry(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("countryCode"))
                .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    public Optional<String> getCompanyPhone(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("phone"))
                .map(JsonNode::asText);
    }
}
