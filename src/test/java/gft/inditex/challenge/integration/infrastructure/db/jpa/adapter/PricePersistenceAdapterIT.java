package gft.inditex.challenge.integration.infrastructure.db.jpa.adapter;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.db.jpa.adapter.PricePersistenceAdapter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:integration-test-db"
})
class PricePersistenceAdapterIT {

    private static final Long BRAND_ID_ZARA = 1L;
    private static final Long PRODUCT_ID = 35455L;

    @Autowired
    private PricePersistenceAdapter pricePersistenceAdapter;

    @Test
    @DisplayName("Should return applicable price when found")
    void shouldReturnApplicablePriceWhenFound() {
        Instant queryDate = Instant.parse("2020-06-14T10:00:00Z");

        Price result = pricePersistenceAdapter.findApplicablePrice(BRAND_ID_ZARA, PRODUCT_ID, queryDate);

        assertThat(result).isNotNull();
        assertThat(result.getBrandId()).isEqualTo(BRAND_ID_ZARA);
        assertThat(result.getProductId()).isEqualTo(PRODUCT_ID);
        assertThat(result.getAppliedTariffId()).isEqualTo(1L);
        assertThat(result.getFinalPrice()).isEqualByComparingTo(new BigDecimal("35.50"));
    }

    @Test
    @DisplayName("Should return null when not found")
    void shouldReturnNullWhenNotFound() {
        Instant queryDate = Instant.parse("2026-06-14T10:00:00Z");

        Price result = pricePersistenceAdapter.findApplicablePrice(BRAND_ID_ZARA, PRODUCT_ID, queryDate);

        assertThat(result).isNull();
    }

}
