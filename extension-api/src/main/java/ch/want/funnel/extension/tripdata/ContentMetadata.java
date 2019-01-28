/*
 * Created on 28 Jan 2019
 */
package ch.want.funnel.extension.tripdata;

import java.time.OffsetDateTime;

public class ContentMetadata {

    public final OffsetDateTime creationDate;
    public final String description;
    public final String participants;

    public ContentMetadata(final OffsetDateTime creationDate, final String description, final String participants) {
        super();
        this.creationDate = creationDate;
        this.description = description;
        this.participants = participants;
    }

    /**
     * Get the date this content was originally created. If unknown, OffsetDateTime.now() should be used.
     * 
     * @return
     */
    public OffsetDateTime getCreationDate() {
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
}
