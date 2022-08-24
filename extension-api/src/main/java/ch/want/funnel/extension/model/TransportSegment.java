/*
 * Created on 26 Apr 2018
 */
package ch.want.funnel.extension.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "uuid")
public class TransportSegment implements Serializable, Comparable<TransportSegment> {

    private static final long serialVersionUID = 1L;
    private UUID uuid;
    @JsonBackReference("leg-segments")
    private SegmentedLeg segmentedLeg;
    private Short segNr;
    private LocalDateTime departuretime;
    private Location departingFromLocation;
    private LocalDateTime arrivaltime;
    private Location arrivingAtLocation;
    private Integer durationinminutes;
    private String travelclass;
    private String farebasis;
    private BookingStatus bookingstatus;
    private String providername;
    private String connectionnumber;
    private String reservationlocator;
    private String baggage;
    private Integer co2KiloPerPax;
    private String vehicleType;
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

    public LocalDateTime getArrivaltime() {
        return arrivaltime;
    }

    public void setArrivaltime(final LocalDateTime arrivaltime) {
        this.arrivaltime = arrivaltime;
    }

    public Integer getDurationinminutes() {
        return durationinminutes;
    }

    public void setDurationinminutes(final Integer durationinminutes) {
        this.durationinminutes = durationinminutes;
    }

    public BookingStatus getBookingstatus() {
        return bookingstatus;
    }

    public void setBookingstatus(final BookingStatus bookingstatus) {
        this.bookingstatus = bookingstatus;
    }

    public String getTravelclass() {
        return travelclass;
    }

    public void setTravelclass(final String travelclass) {
        this.travelclass = travelclass;
    }

    public String getFarebasis() {
        return farebasis;
    }

    public void setFarebasis(final String farebasis) {
        this.farebasis = farebasis;
    }

    /**
     * Get the operating provider
     */
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

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(final String baggage) {
        this.baggage = baggage;
    }

    public Integer getCo2KiloPerPax() {
        return co2KiloPerPax;
    }

    public void setCo2KiloPerPax(final Integer co2KiloPerPax) {
        this.co2KiloPerPax = co2KiloPerPax;
    }

    @Override
    public int compareTo(final TransportSegment other) {
        int result = 0;
        // we're comparing *local* timestamps here, which is functionally dubious. However, it's rare to depart
        // at a given time, and catch a connecting flight in a different time zone with an *earlier* local
        // departure time. These cases will normally be Pacific -> US flights
        if ((departuretime != null) && (other.departuretime != null)) {
            result = departuretime.compareTo(other.departuretime);
        }
        if ((result == 0) && (arrivaltime != null) && (other.arrivaltime != null)) {
            result = arrivaltime.compareTo(other.arrivaltime);
        }
        if ((result == 0) && (segNr != null) && (other.segNr != null)) {
            result = segNr - other.segNr;
        }
        return result;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((departingFromLocation == null) ? 0 : departingFromLocation.hashCode());
        result = prime * result + ((departuretime == null) ? 0 : departuretime.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }
        final TransportSegment other = (TransportSegment) obj;
        if (uuid != null && Objects.equals(uuid, other.uuid)) {
            return true;
        }
        return Objects.equals(departingFromLocation, other.departingFromLocation) &&
            Objects.equals(departuretime, other.departuretime) &&
            Objects.equals(connectionnumber, other.connectionnumber);
    }

    public Location getDepartingFromLocation() {
        if (departingFromLocation == null) {
            departingFromLocation = new Location();
        }
        return departingFromLocation;
    }

    public void setDepartingFromLocation(final Location departingFromLocation) {
        this.departingFromLocation = departingFromLocation;
    }

    public Location getArrivingAtLocation() {
        if (arrivingAtLocation == null) {
            arrivingAtLocation = new Location();
        }
        return arrivingAtLocation;
    }

    public void setArrivingAtLocation(final Location arrivingAtLocation) {
        this.arrivingAtLocation = arrivingAtLocation;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(final String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @JsonIgnore
    public String getAircraftType() {
        return getVehicleType();
    }

    @JsonIgnore
    public void setAircraftType(final String aircraftType) {
        setVehicleType(aircraftType);
    }
}
