package gft.inditex.challenge.infrastructure.db.jpa.adapter;

import gft.inditex.challenge.domain.exception.NotFoundException;
import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.domain.outbound.repository.PriceRepository;
import gft.inditex.challenge.infrastructure.db.jpa.entity.PriceJpaEntity;
import gft.inditex.challenge.infrastructure.db.jpa.mapper.PriceJpaMapper;
import gft.inditex.challenge.infrastructure.db.jpa.repository.PriceJpaRepository;
import gft.inditex.challenge.infrastructure.db.jpa.specification.PriceJpaSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

/**
 * Adapter implementation that bridges the domain port with JPA persistence.
 */
@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepository {

    private static final int FIRST_PAGE = 0;
    private static final int SINGLE_RESULT = 1;
    private static final int FIRST_RESULT = 0;
    private static final String PRIORITY_FIELD = "priority";

    private final PriceJpaRepository priceJpaRepository;
    private final PriceJpaMapper priceJpaMapper;

    @Override
    public Price findApplicablePrice(Long brandId, Long productId, Instant onDate) {

        List<PriceJpaEntity> results = priceJpaRepository.findAll(
                PriceJpaSpecification.findApplicablePrice(brandId, productId, onDate),
                PageRequest.of(FIRST_PAGE, SINGLE_RESULT, Sort.by(Sort.Direction.DESC, PRIORITY_FIELD))
        ).getContent();

        if (results.isEmpty()) {
            throw new NotFoundException(
                    String.format("No price found for brandId=%d, productId=%d on date %s",
                            brandId, productId, onDate));
        }

        return priceJpaMapper.toDomain(results.get(FIRST_RESULT), onDate);
    }
}
