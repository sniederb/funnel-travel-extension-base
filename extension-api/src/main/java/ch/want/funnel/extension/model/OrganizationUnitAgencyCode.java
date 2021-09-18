package ch.want.funnel.extension.model;

public class OrganizationUnitAgencyCode {

    private final String sourceDomain;
    private final String agencyCode;

    public OrganizationUnitAgencyCode(final String sourceDomain, final String agencyCode) {
        this.sourceDomain = sourceDomain;
        this.agencyCode = agencyCode;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public String getSourceDomain() {
        return sourceDomain;
    }
}
