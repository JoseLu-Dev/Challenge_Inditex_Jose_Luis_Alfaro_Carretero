package gft.inditex.challenge.domain.outbound.repository;

import gft.inditex.challenge.domain.model.Price;

import java.time.Instant;
import java.util.Optional;

/**
 * Output port for price persistence operations.
 */
public interface PriceRepository {

    /**
     * Finds the applicable price for a product based on the brand ID, product ID, and effective date.
     *
     * @param brandId   the brand identifier
     * @param productId the product identifier
     * @param onDate    the date for which the price should be effective
     * @return an Optional containing the applicable price, or empty if no price is found
     */
    Price findApplicablePrice(Long brandId, Long productId, Instant onDate);
}
