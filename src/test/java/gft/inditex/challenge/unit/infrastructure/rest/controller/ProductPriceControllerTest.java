package gft.inditex.challenge.unit.infrastructure.rest.controller;

import gft.inditex.challenge.domain.inbound.GetPriceUseCase;
import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.rest.controller.ProductPriceController;
import gft.inditex.challenge.infrastructure.rest.dto.PriceResponse;
import gft.inditex.challenge.infrastructure.rest.mapper.PriceResponseMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPriceControllerTest {

    private static final Long BRAND_ID = 1L;
    private static final Long PRODUCT_ID = 35455L;
    private static final Long APPLIED_TARIFF_ID = 1L;
    private static final Instant ON_DATE = Instant.parse("2020-06-14T10:00:00Z");
    private static final Instant START_DATE = Instant.parse("2020-06-14T00:00:00Z");
    private static final Instant END_DATE = Instant.parse("2020-12-31T23:59:59Z");
    private static final BigDecimal FINAL_PRICE = new BigDecimal("35.50");

    @Mock
    private GetPriceUseCase getPriceUseCase;

    @Mock
    private PriceResponseMapper priceResponseMapper;

    @InjectMocks
    private ProductPriceController productPriceController;

    @Test
    @DisplayName("Should return price response when price is found")
    void shouldReturnPriceResponseWhenPriceFound() {
        // Given
        Price price = createPrice();
        PriceResponse expectedResponse = createPriceResponse();

        when(getPriceUseCase.getApplicablePrice(BRAND_ID, PRODUCT_ID, ON_DATE))
                .thenReturn(price);
        when(priceResponseMapper.toResponse(price))
                .thenReturn(expectedResponse);

        // When
        ResponseEntity<PriceResponse> response = productPriceController.getApplicablePrice(
                BRAND_ID, PRODUCT_ID, ON_DATE
        );

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedResponse);
        assertThat(response.getBody().brandId()).isEqualTo(BRAND_ID);
        assertThat(response.getBody().productId()).isEqualTo(PRODUCT_ID);
        assertThat(response.getBody().finalPrice()).isEqualTo(FINAL_PRICE);

        verify(getPriceUseCase).getApplicablePrice(BRAND_ID, PRODUCT_ID, ON_DATE);
        verify(priceResponseMapper).toResponse(price);
    }

    private Price createPrice() {
        return Price.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .appliedTariffId(APPLIED_TARIFF_ID)
                .onDate(ON_DATE)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .finalPrice(FINAL_PRICE)
                .build();
    }

    private PriceResponse createPriceResponse() {
        return new PriceResponse(
                PRODUCT_ID,
                BRAND_ID,
                APPLIED_TARIFF_ID,
                ON_DATE,
                START_DATE,
                END_DATE,
                FINAL_PRICE
        );
    }
}
