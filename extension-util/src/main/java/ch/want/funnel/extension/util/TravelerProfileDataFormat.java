package ch.want.funnel.extension.util;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

public interface TravelerProfileDataFormat {

    Optional<JsonNode> getPassport();

    Optional<String> getPrimaryEmail();

    Optional<Date> getBirthdate();

    Optional<String> getCountry(JsonNode passportNode);

    Optional<String> getPassportNumber(JsonNode passportNode);

    Optional<String> getPassportPlaceOfIssue(JsonNode passportNode);

    Optional<Date> getPassportExpiration(JsonNode passportNode);

    Optional<JsonNode> getContactData();

    Optional<Locale> getContactLocale(JsonNode contactNode);

    Optional<String> getNationality(JsonNode contactNode);

    Optional<String> getContactPhone(JsonNode contactNode);

    Optional<JsonNode> getCompanyData();

    Optional<String> getCompanyName(JsonNode companyNode);

    Optional<String> getCompanyStreet(JsonNode companyNode);

    Optional<String> getCompanyStreet2(JsonNode companyNode);

    Optional<String> getCompanyPlace(JsonNode companyNode);

    Optional<String> getCompanyZip(JsonNode companyNode);

    Optional<String> getCompanyCountry(JsonNode companyNode);

    Optional<String> getCompanyPhone(JsonNode companyNode);
}