package gft.inditex.challenge.unit.infrastructure.db.jpa.mapper;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.db.jpa.entity.PriceJpaEntity;
import gft.inditex.challenge.infrastructure.db.jpa.mapper.PriceJpaMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class PriceJpaMapperTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Long PRICE_LIST_ID = 1L;
    private static final Instant ON_DATE = Instant.parse("2020-06-14T10:00:00Z");
    private static final Instant START_DATE = Instant.parse("2020-06-14T00:00:00Z");
    private static final Instant END_DATE = Instant.parse("2020-12-31T23:59:59Z");
    private static final BigDecimal PRICE = new BigDecimal("35.50");
    private static final String CURRENCY = "EUR";

    private final PriceJpaMapper mapper = Mappers.getMapper(PriceJpaMapper.class);

    @Test
    @DisplayName("Should map PriceJpaEntity to Price domain model")
    void shouldMapPriceJpaEntityToPriceDomain() {
        // Given
        PriceJpaEntity entity = PriceJpaEntity.builder()
                .id(1L)
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceListId(PRICE_LIST_ID)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .priority(0)
                .price(PRICE)
                .currency(CURRENCY)
                .build();

        // When
        Price price = mapper.toDomain(entity, ON_DATE);

        // Then
        assertThat(price).isNotNull();
        assertThat(price.getBrandId()).isEqualTo(BRAND_ID);
        assertThat(price.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(price.getAppliedTariffId()).isEqualTo(PRICE_LIST_ID);
        assertThat(price.getOnDate()).isEqualTo(ON_DATE);
        assertThat(price.getStartDate()).isEqualTo(START_DATE);
        assertThat(price.getEndDate()).isEqualTo(END_DATE);
        assertThat(price.getFinalPrice()).isEqualTo(PRICE);
    }

    @Test
    @DisplayName("Should handle null entity by creating Price with only onDate populated")
    void shouldHandleNullEntity() {
        // When
        Price price = mapper.toDomain(null, ON_DATE);

        // Then
        assertThat(price).isNotNull();
        assertThat(price.getOnDate()).isEqualTo(ON_DATE);
        assertThat(price.getBrandId()).isNull();
        assertThat(price.getProductId()).isNull();
        assertThat(price.getAppliedTariffId()).isNull();
        assertThat(price.getFinalPrice()).isNull();
    }

    @Test
    @DisplayName("Should map priceListId to appliedTariffId")
    void shouldMapPriceListIdToAppliedTariffId() {
        // Given
        PriceJpaEntity entity = PriceJpaEntity.builder()
                .priceListId(5L)
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .price(PRICE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        // When
        Price price = mapper.toDomain(entity, ON_DATE);

        // Then
        assertThat(price.getAppliedTariffId()).isEqualTo(5L);
    }

    @Test
    @DisplayName("Should map price field to finalPrice")
    void shouldMapPriceFieldToFinalPrice() {
        // Given
        BigDecimal customPrice = new BigDecimal("99.99");
        PriceJpaEntity entity = PriceJpaEntity.builder()
                .priceListId(PRICE_LIST_ID)
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .price(customPrice)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .build();

        // When
        Price price = mapper.toDomain(entity, ON_DATE);

        // Then
        assertThat(price.getFinalPrice()).isEqualTo(customPrice);
    }
}
