package gft.inditex.challenge.unit.infrastructure.db.jpa.adapter;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.db.jpa.adapter.PricePersistenceAdapter;
import gft.inditex.challenge.infrastructure.db.jpa.entity.PriceJpaEntity;
import gft.inditex.challenge.infrastructure.db.jpa.mapper.PriceJpaMapper;
import gft.inditex.challenge.infrastructure.db.jpa.repository.PriceJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PricePersistenceAdapterTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Long PRICE_LIST_ID = 1L;
    private static final Instant ON_DATE = Instant.parse("2020-06-14T10:00:00Z");
    private static final BigDecimal PRICE = new BigDecimal("35.50");

    @Mock
    private PriceJpaRepository priceJpaRepository;

    @Mock
    private PriceJpaMapper priceJpaMapper;

    @InjectMocks
    private PricePersistenceAdapter pricePersistenceAdapter;

    @Test
    @DisplayName("Should return price when applicable price is found")
    void shouldReturnPriceWhenFound() {
        PriceJpaEntity entity = createPriceJpaEntity();
        Price expectedPrice = createPrice();

        when(priceJpaRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.of(entity));
        when(priceJpaMapper.toDomain(eq(entity), eq(ON_DATE)))
                .thenReturn(expectedPrice);

        Price result = pricePersistenceAdapter.findApplicablePrice(BRAND_ID, PRODUCT_ID, ON_DATE);

        assertThat(result).isEqualTo(expectedPrice);
        assertThat(result.getBrandId()).isEqualTo(BRAND_ID);
        assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getFinalPrice()).isEqualTo(PRICE);

        verify(priceJpaRepository).findOne(any(Specification.class));
        verify(priceJpaMapper).toDomain(entity, ON_DATE);
    }

    @Test
    @DisplayName("Should return null when no price is found")
    void shouldReturnNullWhenNoPriceFound() {
        when(priceJpaRepository.findOne(any(Specification.class)))
                .thenReturn(Optional.empty());

        Price result = pricePersistenceAdapter.findApplicablePrice(BRAND_ID, PRODUCT_ID, ON_DATE);

        assertThat(result).isNull();
        verify(priceJpaRepository).findOne(any(Specification.class));
    }

    private PriceJpaEntity createPriceJpaEntity() {
        return PriceJpaEntity.builder()
                .id(1L)
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .priceListId(PRICE_LIST_ID)
                .startDate(Instant.parse("2020-06-14T00:00:00Z"))
                .endDate(Instant.parse("2020-12-31T23:59:59Z"))
                .priority(0)
                .price(PRICE)
                .currency("EUR")
                .build();
    }

    private Price createPrice() {
        return Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .appliedTariffId(PRICE_LIST_ID)
                .onDate(ON_DATE)
                .finalPrice(PRICE)
                .build();
    }
}
