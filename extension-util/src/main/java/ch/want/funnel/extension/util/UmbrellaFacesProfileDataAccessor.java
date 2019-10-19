package ch.want.funnel.extension.util;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

class UmbrellaFacesProfileDataAccessor implements TravelerProfileDataFormat {

    private final JsonNode extendedProfileData;

    UmbrellaFacesProfileDataAccessor(final JsonNode extendedProfileData) {
        this.extendedProfileData = extendedProfileData;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getPassport()
     */
    @Override
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

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCountry(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCountry(final JsonNode passportNode) {
        return Optional.ofNullable(passportNode)
                .map(json -> json.get("country"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getContactData()
     */
    @Override
    public Optional<JsonNode> getContactData() {
        return Optional.ofNullable(extendedProfileData.get("data"))
                .map(data -> data.get("generalData"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getContactPhone(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getContactPhone(final JsonNode contactNode) {
        return Optional.ofNullable(contactNode)
                .map(json -> json.get("businessPhone"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyData()
     */
    @Override
    public Optional<JsonNode> getCompanyData() {
        return Optional.ofNullable(extendedProfileData.get("data"))
                .map(data -> data.get("company"));
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyName(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyName(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("name"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyStreet(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyStreet(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("street"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyStreet2(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyStreet2(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("street2"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyPlace(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyPlace(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("place"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyZip(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyZip(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("zipCode"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyCountry(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyCountry(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("countryCode"))
                .map(JsonNode::asText);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ch.want.funnel.extension.util.TravelerProfileDataFormat#getCompanyPhone(com.fasterxml.jackson.databind.JsonNode)
     */
    @Override
    public Optional<String> getCompanyPhone(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
                .map(json -> json.get("phone"))
                .map(JsonNode::asText);
    }
}
