/*
 * Created on 28 Jan 2019
 */
package ch.want.funnel.extension.tripdata;

import java.util.Date;

public class ContentMetadata {

    public final Date creationDate;
    public final String description;
    public final String participants;
    public final String mimeType;
    public final boolean discardAsIntermediate;

    /**
     * Create an instance for further processing
     *
     * @param creationDate
     * @param mimeType
     * @param description
     * @param participants
     */
    public ContentMetadata(final Date creationDate, final String mimeType, final String description, final String participants) {
        this.creationDate = creationDate;
        this.mimeType = mimeType;
        this.description = description;
        this.participants = participants;
        this.discardAsIntermediate = false;
    }

    /**
     * Create an instance with {@code discardAsIntermediate} set to true.
     */
    public ContentMetadata() {
        this.creationDate = null;
        this.mimeType = null;
        this.description = null;
        this.participants = null;
        this.discardAsIntermediate = true;
    }

    /**
     * Get the date this content was originally created. If unknown, new Date()() should be used.
     *
     * @return
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Returns a description of the content, such as an e-mail subject, a hotel name, or possible simply a PNR
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get a comma-separated list of participant names
     *
     * @return
     */
    public String getParticipants() {
        return participants;
    }

    /**
     * MIME types handled by funnel.travel:
     * - multipart/mixed (for MIME e-mail messages)
     * - text/plain
     * - text/html
     * - application/json
     * - application/xml
     *
     * @return
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * If true, funnel.travel will discard this content (assuming that later, another update will arrive)
     *
     * @return
     */
    public boolean isDiscardAsIntermediate() {
        return discardAsIntermediate;
    }
}
