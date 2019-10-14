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
    private String externalid;
    private String origin;
    private OffsetDateTime entrydate;
    private String subject;
    private String paxlist;
    private String mime;
    private byte[] source;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * For file-based producers, this <strong>must</strong> be the filename. Note that
     * filenames only need to be unique within a booking.
     * 
     * @return
     */
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

    public byte[] getSource() {
        return source;
    }

    public void setSource(final byte[] source) {
        this.source = source;
    }
}
