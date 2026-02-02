package gft.inditex.challenge.application.service;

import java.time.Instant;

import gft.inditex.challenge.domain.exception.NotFoundException;
import gft.inditex.challenge.domain.inbound.GetPriceUseCase;
import org.springframework.stereotype.Service;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.domain.outbound.repository.PriceRepository;
import lombok.RequiredArgsConstructor;

/**
 * Service implementing the price use case.
 */
@Service
@RequiredArgsConstructor
public class PriceService implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    @Override
    public Price getApplicablePrice(Long brandId, Long productId, Instant onDate) {
        Price price = priceRepository.findApplicablePrice(brandId, productId, onDate);

        if (price == null) {
            throw new NotFoundException(
                    String.format("No price found for brandId=%d, productId=%d on date %s", brandId, productId, onDate)
            );
        }

        return price;
    }
}
