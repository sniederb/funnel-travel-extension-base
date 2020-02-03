package ch.want.funnel.extension.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

class UmbrellaFacesProfileDataAccessor implements TravelerProfileDataFormat {

    private final JsonNode extendedProfileData;

    UmbrellaFacesProfileDataAccessor(final JsonNode extendedProfileData) {
        this.extendedProfileData = extendedProfileData;
    }

    /**
     * Search {@code data -> papers -> passports [primary == true]}
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

    /**
     * Search {@code email}
     */
    @Override
    public Optional<String> getPrimaryEmail() {
        return Optional.ofNullable(extendedProfileData.get("email")).map(JsonNode::asText);
    }

    @Override
    public Optional<Date> getBirthdate() {
        return getContactData()
            .map(n -> n.get("birthdate"))
            .map(JsonNode::asText)
            .map(s -> s.length() > 0 ? s : null)
            .map(s -> {
                try {
                    return new SimpleDateFormat("dd.MM.yyyy").parse(s);
                } catch (final ParseException e) {
                    return null;
                }
            });
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

    @Override
    public Optional<String> getPassportNumber(final JsonNode passportNode) {
        return Optional.ofNullable(passportNode)
            .map(json -> json.get("number"))
            .map(JsonNode::asText);
    }

    @Override
    public Optional<String> getPassportPlaceOfIssue(final JsonNode passportNode) {
        return Optional.ofNullable(passportNode)
            .map(json -> json.get("issuePlace"))
            .map(JsonNode::asText);
    }

    @Override
    public Optional<Date> getPassportExpiration(final JsonNode passportNode) {
        return Optional.ofNullable(passportNode)
            .map(json -> json.get("expiration"))
            .map(JsonNode::asText)
            // expect format dd.MM.yyyy
            .map(s -> {
                try {
                    return new SimpleDateFormat("dd.MM.yyyy").parse(s);
                } catch (final ParseException e) {
                    return null;
                }
            });
    }

    /**
     * Search {@code data -> generalData}
     */
    @Override
    public Optional<JsonNode> getContactData() {
        return Optional.ofNullable(extendedProfileData.get("data"))
            .map(data -> data.get("generalData"));
    }

    /**
     * {@code contactNode} is obtained using {@link #getContactData()}
     */
    @Override
    public Optional<String> getContactPhone(final JsonNode contactNode) {
        return Optional.ofNullable(contactNode)
            .map(json -> json.get("businessPhone"))
            .map(JsonNode::asText);
    }

    @Override
    public Optional<String> getNationality(final JsonNode contactNode) {
        return Optional.ofNullable(contactNode)
            .map(json -> json.get("nationality"))
            .map(JsonNode::asText);
    }

    @Override
    public Optional<Locale> getContactLocale(final JsonNode contactNode) {
        return Optional.ofNullable(contactNode)
            .map(json -> json.get("language"))
            .map(JsonNode::asText)
            .map(UmbrellaFacesProfileDataAccessor::fromIsoString);
    }

    private static Locale fromIsoString(final String str) {
        if (str == null) {
            return Locale.getDefault();
        }
        final String[] segments = str.split("[_-]", -1);
        switch (segments.length) {
        case 1:
            return new Locale(str);
        case 2:
            return new Locale(segments[0], segments[1]);
        default:
            return new Locale(segments[0], segments[1], segments[2]);
        }
    }

    /**
     * Search {@code data -> company}
     */
    @Override
    public Optional<JsonNode> getCompanyData() {
        return Optional.ofNullable(extendedProfileData.get("data"))
            .map(data -> data.get("company"));
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyName(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("name"))
            .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyStreet(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("street"))
            .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyStreet2(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("street2"))
            .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyPlace(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("place"))
            .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyZip(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("zipCode"))
            .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyCountry(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("countryCode"))
            .map(JsonNode::asText);
    }

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    @Override
    public Optional<String> getCompanyPhone(final JsonNode companyNode) {
        return Optional.ofNullable(companyNode)
            .map(json -> json.get("phone"))
            .map(JsonNode::asText);
    }
}
