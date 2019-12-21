package ch.want.funnel.extension.util;

import java.util.Date;
import java.util.Locale;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

class NoopProfileDataAccessor implements TravelerProfileDataFormat {

    @Override
    public Optional<JsonNode> getPassport() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getPrimaryEmail() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCountry(final JsonNode passportNode) {
        return Optional.empty();
    }

    @Override
    public Optional<JsonNode> getContactData() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getContactPhone(final JsonNode contactNode) {
        return Optional.empty();
    }

    @Override
    public Optional<Locale> getContactLocale(final JsonNode contactNode) {
        return Optional.empty();
    }

    @Override
    public Optional<JsonNode> getCompanyData() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyName(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyStreet(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyStreet2(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyPlace(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyZip(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyCountry(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getCompanyPhone(final JsonNode companyNode) {
        return Optional.empty();
    }

    @Override
    public Optional<Date> getBirthdate() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getPassportNumber(final JsonNode passportNode) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getPassportPlaceOfIssue(final JsonNode passportNode) {
        return Optional.empty();
    }

    @Override
    public Optional<Date> getPassportExpiration(final JsonNode passportNode) {
        return Optional.empty();
    }
}
