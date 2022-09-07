package ch.want.funnel.extension.model;

public enum FareType {
    /**
     * aka "published" fare (P), incl. full fares, discount fares.
     */
    PUBLIC,
    /**
     * Negotiated fare (N), from an agreement between airlines, consolidators and travel agencies
     */
    NEGO,
    /**
     * Nego corporate fares (C), usually a private fare with corporate code
     */
    NEGO_CORP,
    /**
     * Unifares such as Dynamic Discounted Fares (U), ATPCO Private Fares (V) and ATPCO CAT35 Negotiated Fares (A)
     */
    UNI,
    /**
     * Corporate unifare
     */
    UNI_CORP,
    /**
     * Fare type offered on internet sites
     */
    WEB
}
