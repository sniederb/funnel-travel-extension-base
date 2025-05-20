package ch.want.funnel.extension.util.sort;

import java.time.LocalDateTime;

import ch.want.funnel.extension.model.TravelServiceType;

public class TravelServiceSortKey {

    private final TravelServiceType serviceType;
    private final MiscServiceType miscServiceType;
    private final String lastResortSortProperty;
    private LocalDateTime departure;
    private boolean needsTime;

    public TravelServiceSortKey(final TravelServiceType serviceType, final MiscServiceType miscServiceType, final String lastResortSortProperty) {
        this.serviceType = serviceType;
        this.miscServiceType = miscServiceType;
        this.lastResortSortProperty = lastResortSortProperty;
    }

    public TravelServiceType getServiceType() {
        return serviceType;
    }

    public MiscServiceType getMiscServiceType() {
        return miscServiceType;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(final LocalDateTime departure) {
        this.departure = departure;
    }

    public boolean isNeedsTime() {
        return needsTime;
    }

    public void setNeedsTime(final boolean needsTime) {
        this.needsTime = needsTime;
    }

    public String getLastResortSortProperty() {
        return lastResortSortProperty;
    }
}
