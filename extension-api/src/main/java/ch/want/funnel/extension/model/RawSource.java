/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.UUID;

public class RawSource implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID bookingUuid;
    private String externalid;
    private String origin;
    private OffsetDateTime entrydate;
    private String subject;
    private String paxlist;
    private String mime;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getBookingUuid() {
        return bookingUuid;
    }

    public void setBookingUuid(final UUID bookingUuid) {
        this.bookingUuid = bookingUuid;
    }

    public String getExternalid() {
        return externalid;
    }

    public void setExternalid(final String externalid) {
        this.externalid = externalid;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(final String origin) {
        this.origin = origin;
    }

    public OffsetDateTime getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(final OffsetDateTime entrydate) {
        this.entrydate = entrydate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getPaxlist() {
        return paxlist;
    }

    public void setPaxlist(final String paxlist) {
        this.paxlist = paxlist;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(final String mime) {
        this.mime = mime;
    }
}
