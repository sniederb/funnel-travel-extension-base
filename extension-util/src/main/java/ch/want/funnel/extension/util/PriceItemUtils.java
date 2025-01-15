package ch.want.funnel.extension.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import ch.want.funnel.extension.model.PriceItem;
import ch.want.funnel.extension.model.PriceItemType;
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
        return getAllPriceitems(service, false);
    }

    public static Set<PriceItem> getAllPriceitems(final TravelService service, final boolean includeOnSite) {
        final Predicate<PriceItem> additionalFilter = includeOnSite ? priceitem -> true : priceitem -> priceitem.getPriceItemType() != PriceItemType.ONSITE;
        final Set<PriceItem> allpriceitems = service.getPriceitems().stream()
            .filter(additionalFilter)
            .filter(priceitem -> priceitem.getTransportDocument() == null)
            .collect(HashSet::new, HashSet::add, HashSet::addAll);
        service.getTransportDocuments().stream()
            .flatMap(srv -> srv.getPriceitems().stream())
            .filter(additionalFilter)
            .forEach(allpriceitems::add);
        return allpriceitems;
    }

    public static final Map<String, BigDecimal> getAmountPerCurrency(final Collection<PriceItem> priceitems) {
        return priceitems.stream()
            .collect(Collectors.groupingBy(PriceItem::getCurrency))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(PriceItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    public static final Map<String, BigDecimal> getPurchasePricePerCurrency(final Collection<PriceItem> priceitems) {
        return priceitems.stream()
            .collect(Collectors.groupingBy(PriceItem::getCurrency))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(PriceItem::getPurchasePrice).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }
}
