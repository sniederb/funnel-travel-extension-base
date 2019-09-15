/*
 * Created on 5 Jan 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.util.UUID;

public class Traveler implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    private UUID organizationUuid;
    private String firstname;
    private String firstnameNonAps;
    private String lastname;
    private String email;
    private String username;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getOrganizationUuid() {
        return organizationUuid;
    }

    public void setOrganizationUuid(final UUID organizationUuid) {
        this.organizationUuid = organizationUuid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getFirstnameNonAps() {
        return firstnameNonAps;
    }

    public void setFirstnameNonAps(final String firstnameNonAps) {
        this.firstnameNonAps = firstnameNonAps;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
