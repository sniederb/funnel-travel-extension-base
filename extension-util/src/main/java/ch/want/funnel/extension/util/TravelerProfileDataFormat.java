package ch.want.funnel.extension.util;

import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

public interface TravelerProfileDataFormat {

    /**
     * Search {@code data -> papers -> passports [primary == true]}
     */
    Optional<JsonNode> getPassport();

    Optional<String> getCountry(JsonNode passportNode);

    /**
     * Search {@code data -> generalData}
     */
    Optional<JsonNode> getContactData();

    /**
     * {@code contactNode} is obtained using {@link #getContactData()}
     */
    Optional<String> getContactPhone(JsonNode contactNode);

    /**
     * Search {@code data -> company}
     */
    Optional<JsonNode> getCompanyData();

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyName(JsonNode companyNode);

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyStreet(JsonNode companyNode);

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyStreet2(JsonNode companyNode);

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyPlace(JsonNode companyNode);

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyZip(JsonNode companyNode);

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyCountry(JsonNode companyNode);

    /**
     * {@code companyNode} is obtained using {@link #getCompanyData()}
     */
    Optional<String> getCompanyPhone(JsonNode companyNode);
}