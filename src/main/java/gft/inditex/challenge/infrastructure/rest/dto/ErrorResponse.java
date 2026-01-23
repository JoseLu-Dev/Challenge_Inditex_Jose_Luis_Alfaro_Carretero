package gft.inditex.challenge.infrastructure.rest.dto;

import java.time.Instant;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Standard error response DTO.
 */
@Schema(description = "Standard error response containing details about the error")
public record ErrorResponse(

        @Schema(description = "HTTP status code", example = "404")
        int status,

        @Schema(description = "Error type or title", example = "Not Found")
        String error,

        @Schema(description = "Detailed error message", example = "No price found for product 35455 and brand 1")
        String message,

        @Schema(description = "Timestamp when the error occurred", example = "2020-06-14T10:00:00Z")
        Instant timestamp,

        @Schema(description = "Request path that caused the error", example = "/api/v1/brands/1/products/35455/prices")
        String path
) {
}
