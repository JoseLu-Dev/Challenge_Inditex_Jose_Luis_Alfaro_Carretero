package gft.inditex.challenge.infrastructure.rest.dto;

import java.math.BigDecimal;
import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Response DTO for price queries.
 */
@Schema(description = "Response containing the applicable price information for a product")
public record PriceResponse(

        @Schema(description = "Unique identifier of the product", example = "35455")
        Long productId,

        @Schema(description = "Unique identifier of the brand", example = "1")
        Long brandId,

        @Schema(description = "Identifier of the applied tariff/price list", example = "1")
        Long appliedTariffId,

        @Schema(description = "The date for which the price was queried", example = "2020-06-14T10:00:00Z")
        Instant onDate,

        @Schema(description = "Start date of the price application period", example = "2020-06-14T00:00:00Z")
        Instant startDate,

        @Schema(description = "End date of the price application period", example = "2020-12-31T23:59:59Z")
        Instant endDate,

        @Schema(description = "The final price to apply for the product", example = "35.50")
        BigDecimal finalPrice
) {
}
