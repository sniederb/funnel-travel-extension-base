/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class TransportSegment implements Serializable {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("leg-segments")
    private SegmentedLeg segmentedLeg;
    private Short segNr;
    private LocalDateTime departuretime;
    private String departingfromdestination;
    private String departingFromDestinationName;
    private LocalDateTime arrivaltime;
    private String arrivingatdestination;
    private String arrivingAtDestinationName;
    private Integer durationinminutes;
    private String travelclass;
    private String providername;
    private String connectionnumber;
    private String reservationlocator;
    private transient List<TransportDocument> auxiliaries = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(final UUID uuid) {
        this.uuid = uuid;
    }

    public SegmentedLeg getSegmentedLeg() {
        return segmentedLeg;
    }

    public void setSegmentedLeg(final SegmentedLeg segmentedLeg) {
        this.segmentedLeg = segmentedLeg;
    }

    public Short getSegNr() {
        return segNr;
    }

    public void setSegNr(final Short segNr) {
        this.segNr = segNr;
    }

    public LocalDateTime getDeparturetime() {
        return departuretime;
    }

    public void setDeparturetime(final LocalDateTime departuretime) {
        this.departuretime = departuretime;
    }

    public String getDepartingfromdestination() {
        return departingfromdestination;
    }

    public void setDepartingfromdestination(final String departingfromdestination) {
        this.departingfromdestination = departingfromdestination;
    }

    public LocalDateTime getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(final LocalDateTime arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public String getArrivingatdestination() {
        return arrivingatdestination;
    }

    public void setArrivingatdestination(final String arrivingatdestination) {
        this.arrivingatdestination = arrivingatdestination;
    }

    public Integer getDurationinminutes() {
        return durationinminutes;
    }

    public void setDurationinminutes(final Integer durationinminutes) {
        this.durationinminutes = durationinminutes;
    }

    public String getTravelclass() {
        return travelclass;
    }

    public void setTravelclass(final String travelclass) {
        this.travelclass = travelclass;
    }

    public String getProvidername() {
        return providername;
    }

    public void setProvidername(final String providername) {
        this.providername = providername;
    }

    public String getConnectionnumber() {
        return connectionnumber;
    }

    public void setConnectionnumber(final String connectionnumber) {
        this.connectionnumber = connectionnumber;
    }

    public String getReservationlocator() {
        return reservationlocator;
    }

    public void setReservationlocator(final String reservationlocator) {
        this.reservationlocator = reservationlocator;
    }

    public List<TransportDocument> getAuxiliaries() {
        return auxiliaries;
    }

    public void setAuxiliaries(final List<TransportDocument> auxiliaries) {
        this.auxiliaries = auxiliaries;
    }

    public String getDepartingFromDestinationName() {
        return departingFromDestinationName;
    }

    public void setDepartingFromDestinationName(final String departingFromDestinationName) {
        this.departingFromDestinationName = departingFromDestinationName;
    }

    public String getArrivingAtDestinationName() {
        return arrivingAtDestinationName;
    }

    public void setArrivingAtDestinationName(final String arrivingAtDestinationName) {
        this.arrivingAtDestinationName = arrivingAtDestinationName;
    }
}
