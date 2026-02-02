package gft.inditex.challenge.infrastructure.db.jpa.specification;

import gft.inditex.challenge.infrastructure.db.jpa.entity.PriceJpaEntity;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import java.time.Instant;

/**
 * JPA Specifications for dynamic price queries.
 */
public final class PriceJpaSpecification {

    private static final String BRAND_ID_FIELD = "brandId";
    private static final String PRODUCT_ID_FIELD = "productId";
    private static final String START_DATE_FIELD = "startDate";
    private static final String END_DATE_FIELD = "endDate";
    private static final String PRIORITY_FIELD = "priority";

    private PriceJpaSpecification() {
    }

    /**
     * Creates a specification to filter by brand ID.
     *
     * @param brandId the brand identifier
     * @return the specification
     */
    public static Specification<PriceJpaEntity> hasBrandId(Long brandId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(BRAND_ID_FIELD), brandId);
    }

    /**
     * Creates a specification to filter by product ID.
     *
     * @param productId the product identifier
     * @return the specification
     */
    public static Specification<PriceJpaEntity> hasProductId(Long productId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(PRODUCT_ID_FIELD), productId);
    }

    /**
     * Creates a specification to filter prices that are effective on a given date.
     * The date must be between the start date (inclusive) and end date (inclusive).
     *
     * @param onDate the date for which the price should be effective
     * @return the specification
     */
    public static Specification<PriceJpaEntity> isEffectiveOn(Instant onDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.and(
                        criteriaBuilder.lessThanOrEqualTo(root.get(START_DATE_FIELD), onDate),
                        criteriaBuilder.greaterThanOrEqualTo(root.get(END_DATE_FIELD), onDate)
                );
    }

    /**
     * Creates a specification to filter prices with maximum priority
     * among all applicable prices for the given criteria.
     * Uses a subquery to find the max priority value.
     *
     * @param brandId   the brand identifier
     * @param productId the product identifier
     * @param onDate    the date for which the price should be effective
     * @return the specification
     */
    public static Specification<PriceJpaEntity> hasMaxPriority(
            Long brandId, Long productId, Instant onDate) {
        return (root, query, cb) -> {
            Specification<PriceJpaEntity> filterSpec = hasBrandId(brandId)
                    .and(hasProductId(productId))
                    .and(isEffectiveOn(onDate));

            Subquery<Integer> maxPrioritySubquery = query.subquery(Integer.class);
            Root<PriceJpaEntity> subRoot = maxPrioritySubquery.from(PriceJpaEntity.class);

            maxPrioritySubquery.select(cb.max(subRoot.get(PRIORITY_FIELD)));
            maxPrioritySubquery.where(filterSpec.toPredicate(subRoot, query, cb));

            return cb.equal(root.get(PRIORITY_FIELD), maxPrioritySubquery);
        };
    }

    /**
     * Creates a combined specification to find an applicable price
     * for a given brand, product, and date.
     *
     * @param brandId   the brand identifier
     * @param productId the product identifier
     * @param onDate    the date for which the price should be effective
     * @return the combined specification
     */
    public static Specification<PriceJpaEntity> findApplicablePrice(
            Long brandId, Long productId, Instant onDate) {
        return hasBrandId(brandId)
                .and(hasProductId(productId))
                .and(isEffectiveOn(onDate))
                .and(hasMaxPriority(brandId, productId, onDate));
    }
}
