package gft.inditex.challenge.infrastructure.rest.controller;

import java.time.Instant;

import gft.inditex.challenge.application.inbound.GetPriceUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gft.inditex.challenge.infrastructure.rest.dto.ErrorResponse;
import gft.inditex.challenge.infrastructure.rest.dto.PriceResponse;
import gft.inditex.challenge.infrastructure.rest.mapper.PriceResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

/**
 * REST controller for product price-related operations.
 */
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Prices", description = "Operations related to product pricing")
public class ProductPriceController {

    private final GetPriceUseCase getPriceUseCase;
    private final PriceResponseMapper priceResponseMapper;

    @GetMapping("/brands/{brandId}/products/{productId}/prices")
    @Operation(
            summary = "Get applicable price for a product",
            description = "Retrieves the applicable price for a specific product and brand at a given effective date. "
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Price found successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = PriceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = """
                            Invalid request parameters. This can occur when required parameters are missing
                            or have invalid format (e.g., invalid date format, non-numeric IDs)
                            """,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No price found for the specified product, brand, and effective date combination",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error. An unexpected error occurred while processing the request",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<PriceResponse> getApplicablePrice(
            @Parameter(
                    description = "Unique identifier of the brand",
                    required = true,
                    example = "1"
            )
            @PathVariable Long brandId,

            @Parameter(
                    description = "Unique identifier of the product",
                    required = true,
                    example = "35455"
            )
            @PathVariable Long productId,

            @Parameter(
                    description = "The date for which the price is requested (ISO 8601 format)",
                    required = true,
                    example = "2020-06-14T10:00:00Z"
            )
            @RequestParam Instant onDate
    ) {
        var priceResult = getPriceUseCase.getApplicablePrice(brandId, productId, onDate);
        return ResponseEntity.ok(priceResponseMapper.toResponse(priceResult));
    }
}
