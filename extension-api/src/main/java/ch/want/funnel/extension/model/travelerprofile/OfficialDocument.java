package ch.want.funnel.extension.model.travelerprofile;

import java.io.Serializable;
import java.time.LocalDate;

public class OfficialDocument implements Serializable {

    private static final long serialVersionUID = 1L;
    private OfficialDocumentType documentType;
    private String country;
    private String number;
    private VisaEntryType entryType;
    private LocalDate expiration;
    private LocalDate issueDate;
    private String issuePlace;

    public OfficialDocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(final OfficialDocumentType documentType) {
        this.documentType = documentType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    public VisaEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(final VisaEntryType entryType) {
        this.entryType = entryType;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(final LocalDate expiration) {
        this.expiration = expiration;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(final LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getIssuePlace() {
        return issuePlace;
    }

    public void setIssuePlace(final String issuePlace) {
        this.issuePlace = issuePlace;
    }
}
