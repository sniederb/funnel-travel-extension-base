package ch.want.funnel.extension.util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import ch.want.funnel.extension.model.PriceItem;
import ch.want.funnel.extension.model.PriceItemType;
import ch.want.funnel.extension.model.TravelService;

public final class PriceItemUtils {

    public static final String PROMOTION_PREFIX = "Promotion";
    public static final String DISCOUNT_PREFIX = "Discount";

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

    /**
     * Create totals of {@link PriceItem#getAmount()} grouped by {@link PriceItem#getCurrency()}.
     */
    public static final Map<String, BigDecimal> getAmountPerCurrency(final Collection<PriceItem> priceitems) {
        return priceitems.stream()
            .collect(Collectors.groupingBy(PriceItem::getCurrency))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(PriceItem::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    /**
     * Create totals of {@link PriceItem#getPurchasePrice()} grouped by {@link PriceItem#getPurchasePriceCurrency()}.
     */
    public static final Map<String, BigDecimal> getPurchasePricePerCurrency(final Collection<PriceItem> priceitems) {
        return priceitems.stream()
            .filter(p -> p.getPurchasePrice() != null)
            .filter(p -> p.getPurchasePriceCurrency() != null)
            .collect(Collectors.groupingBy(PriceItem::getPurchasePriceCurrency))
            .entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream().map(PriceItem::getPurchasePrice).reduce(BigDecimal.ZERO, BigDecimal::add)));
    }

    /**
     * <dt>Service charge</dt>
     * <dd>A fee added by the agency. This amount must not affect revenue/commission calculations</dd>
     */
    public static boolean isServiceCharge(final PriceItem priceitem) {
        return (priceitem.getPriceItemType() == PriceItemType.SURCHARGE_PER_BOOKING) ||
            (priceitem.getPriceItemType() == PriceItemType.SURCHARGE_PER_PAX) ||
            (priceitem.getPriceItemType() == PriceItemType.AUX_PER_BOOKING &&
                // legacy approach to surcharges
                "Service charge".equalsIgnoreCase(priceitem.getDescription()));
    }

    /**
     * <dt>Commission</dt>
     * <dd>Commission granted by the tour operator to the agency. In an agency-collection scenario, this effectively reduces the total
     * purchase price. In a direct-collection scenario, this amount must be passed on to the agency.</dd>
     */
    public static boolean isCommission(final PriceItem priceitem) {
        return (priceitem.getPriceItemType() == PriceItemType.SUPPLIER_ONLY) &&
            (priceitem.getPurchasePrice() != null) &&
            (priceitem.getPurchasePrice().signum() == -1);
    }

    /**
     * <dt>Promotion</dt>
     * <dd>A retail price reduction which takes the form of a pseudo-payment. Using a gift coupon would be similar to a promotion code.
     * Typically, a promo code does not influence the revenue/commission calculation.</dd>
     */
    public static boolean isPromotion(final PriceItem priceitem) {
        return priceitem.getAmount().signum() == -1 &&
            priceitem.getPriceItemType() == PriceItemType.AUX_PER_BOOKING &&
            priceitem.getTravelService() == null &&
            StringUtils.isNotBlank(priceitem.getDescription()) &&
            priceitem.getDescription().startsWith(PROMOTION_PREFIX);
    }

    /**
     * <dt>Discount</dt>
     * <dd>A retail price reduction. Typically, a discount influences the revenue/commission calculation.</dd>
     */
    public static boolean isDiscount(final PriceItem priceitem) {
        return priceitem.getAmount().signum() == -1 &&
            priceitem.getPriceItemType() == PriceItemType.AUX_PER_BOOKING &&
            priceitem.getTravelService() == null &&
            StringUtils.isNotBlank(priceitem.getDescription()) &&
            priceitem.getDescription().startsWith(DISCOUNT_PREFIX);
    }
}
