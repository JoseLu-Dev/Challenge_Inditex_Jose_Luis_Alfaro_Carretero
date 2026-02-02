package gft.inditex.challenge.unit.infrastructure.rest.mapper;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.rest.dto.PriceResponse;
import gft.inditex.challenge.infrastructure.rest.mapper.PriceResponseMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PriceResponseMapperTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Long APPLIED_TARIFF_ID = 1L;
    private static final Instant ON_DATE = Instant.parse("2020-06-14T10:00:00Z");
    private static final Instant START_DATE = Instant.parse("2020-06-14T00:00:00Z");
    private static final Instant END_DATE = Instant.parse("2020-12-31T23:59:59Z");
    private static final BigDecimal FINAL_PRICE = new BigDecimal("35.50");

    private final PriceResponseMapper mapper = Mappers.getMapper(PriceResponseMapper.class);

    @Test
    @DisplayName("Should map Price domain model to PriceResponse DTO")
    void shouldMapPriceToPriceResponse() {
        // Given
        Price price = Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .appliedTariffId(APPLIED_TARIFF_ID)
                .onDate(ON_DATE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .finalPrice(FINAL_PRICE)
                .build();

        // When
        PriceResponse response = mapper.toResponse(price);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.brandId()).isEqualTo(BRAND_ID);
        assertThat(response.productId()).isEqualTo(PRODUCT_ID);
        assertThat(response.appliedTariffId()).isEqualTo(APPLIED_TARIFF_ID);
        assertThat(response.onDate()).isEqualTo(ON_DATE);
        assertThat(response.startDate()).isEqualTo(START_DATE);
        assertThat(response.endDate()).isEqualTo(END_DATE);
        assertThat(response.finalPrice()).isEqualTo(FINAL_PRICE);
    }

    @Test
    @DisplayName("Should return null when price is null")
    void shouldReturnNullWhenPriceIsNull() {
        // When
        PriceResponse response = mapper.toResponse(null);

        // Then
        assertThat(response).isNull();
    }
}
