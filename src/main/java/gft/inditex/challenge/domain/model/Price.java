package gft.inditex.challenge.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Domain model representing a price.
 */
@Data
@Builder
public class Price {

    private Long productId;

    private Long brandId;

    private Long appliedTariffId;

    private Instant onDate;

    private BigDecimal finalPrice;
}
