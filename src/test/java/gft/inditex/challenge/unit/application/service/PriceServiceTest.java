package gft.inditex.challenge.unit.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;

import gft.inditex.challenge.application.service.PriceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.domain.outbound.repository.PriceRepository;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceService priceService;

    @Test
    void getApplicablePrice_shouldReturnPrice_whenPriceExists() {
        // Given
        Long brandId = 1L;
        Long productId = 35455L;
        Instant onDate = Instant.parse("2020-06-14T10:00:00Z");

        Price expectedPrice = Price.builder()
                .brandId(brandId)
                .productId(productId)
                .appliedTariffId(1L)
                .onDate(onDate)
                .finalPrice(new BigDecimal("35.50"))
                .build();

        when(priceRepository.findApplicablePrice(brandId, productId, onDate))
                .thenReturn(expectedPrice);

        // When
        Price result = priceService.getApplicablePrice(brandId, productId, onDate);

        // Then
        assertThat(result).isEqualTo(expectedPrice);
        verify(priceRepository).findApplicablePrice(brandId, productId, onDate);
    }
}
