package gft.inditex.challenge.application.inbound;

import java.time.Instant;

import gft.inditex.challenge.domain.model.Price;

/**
 * Input port for retrieving the applicable price for a product.
 */
public interface GetPriceUseCase {

    /**
     * Retrieves the applicable price for a product based on the brand ID, product ID, and effective date.
     *
     * @param brandId   the brand identifier
     * @param productId the product identifier
     * @param onDate    the date for which the price should be effective
     * @return the price result containing the applicable tariff and final price
     */
    Price getApplicablePrice(Long brandId, Long productId, Instant onDate);
}
