package ch.want.funnel.extension.util;

import java.util.HashSet;
import java.util.Set;

import ch.want.funnel.extension.model.PriceItem;
import ch.want.funnel.extension.model.TravelService;

public final class PriceItemUtils {

    private PriceItemUtils() {
    }

    /**
     * Beware that the core TripReader produces clones of the {@link PriceItem} linked
     * to service / document. Furthermore, segment auxiliary {@link PriceItem} are
     * not linked to the service.
     *
     * This method returns all {@link PriceItem} within a {@link TravelService}, regardless
     * of nesting.
     */
    public static Set<PriceItem> getAllPriceitems(final TravelService service) {
        final Set<PriceItem> allpriceitems = service.getPriceitems().stream()
            .filter(priceitem -> priceitem.getTransportDocument() == null)
            .collect(HashSet::new, HashSet::add, HashSet::addAll);
        service.getTransportDocuments().stream()
            .flatMap(srv -> srv.getPriceitems().stream())
            .forEach(allpriceitems::add);
        return allpriceitems;
    }
}
