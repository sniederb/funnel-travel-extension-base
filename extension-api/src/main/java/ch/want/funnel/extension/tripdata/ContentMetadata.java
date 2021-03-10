/*
 * Created on 28 Jan 2019
 */
package ch.want.funnel.extension.tripdata;

import java.time.LocalDateTime;

/**
 * This class provides information on the raw source content. As an example, with an email as source, this class
 * will hold the email subject, MIME type and received date.
 */
public class ContentMetadata {

    public final LocalDateTime creationDate;
    public final String description;
    public final String participants;
    public final String mimeType;
    /**
     * If true, the funnel.travel server is advised to discard the source. Set this to true if a data stream
     * produces multiple sources during a push or pull process and this is not the final source.
     */
    public final boolean discardAsIntermediate;

    /**
     * Create an instance for further processing
     *
     * @param creationDate
     * @param mimeType
     * @param description
     * @param participants
     */
    public ContentMetadata(final LocalDateTime creationDate, final String mimeType, final String description, final String participants) {
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
     * Get the date this content was originally created. If unknown, {@code LocalDateTime.now()} should be used.
     *
     * @return
     */
    public LocalDateTime getCreationDate() {
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
     * <ul>
     * <li>multipart/mixed (for MIME e-mail messages)
     * <li>text/plain
     * <li>text/html
     * <li>application/json
     * <li>application/xml
     * </ul>
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
