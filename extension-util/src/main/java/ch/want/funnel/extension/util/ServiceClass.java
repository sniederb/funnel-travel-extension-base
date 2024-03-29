package ch.want.funnel.extension.util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * <dt>Class of service</dt>
 * <dd>The class of service refers to the different cabin/service classes. Typically
 * these are Y = Economy, C = Business, F = First</dd>
 * <dt>Class of booking</dt>
 * <dd>The class of booking refers to which fare you booked. This implies the
 * class of service, but also factors such as refundable, miles collection etc.</dd>
 * Note that the assigned service classes are "common", but not a standard.
 * Individual airlines might choose to use service class letters differently.
 * See eg. <a href="https://www.cheapflights.com/news/what-are-airline-classes">What are the differences between airline classes</a>
 */
public enum ServiceClass {
    ECONOMY("Economy", "B", "E", "G", "H", "I", "K", "L", "M", "N", "O", "Q", "S", "T", "U", "V", "W", "X", "Y"), //
    SPECIAL_FARE("Special fare"), //
    /**
     * Premium economy is something which certain airlines define, e.g. LH for E, G and N
     */
    PREMIUM_ECONOMY("Premium economy"), //
    BUSINESS("Business", "C", "D", "J", "P", "Z"), //
    FIRST("First", "A", "F", "R");

    private final String description;
    private final Set<String> fareClasses;

    ServiceClass(final String description, final String... fareClasses) {
        this.description = description;
        this.fareClasses = new HashSet<>();
        Collections.addAll(this.fareClasses, fareClasses);
    }

    public static ServiceClass fromFareClass(final String fareClass) {
        for (final ServiceClass candidate : ServiceClass.values()) {
            if (candidate.fareClasses.contains(fareClass)) {
                return candidate;
            }
        }
        return ECONOMY;
    }

    public String getDescription() {
        return description;
    }
}
