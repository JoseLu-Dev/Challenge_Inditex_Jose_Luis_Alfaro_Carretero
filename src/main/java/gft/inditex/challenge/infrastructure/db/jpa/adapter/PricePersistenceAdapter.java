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
import java.util.Optional;

/**
 * Adapter implementation that bridges the domain port with JPA persistence.
 */
@Component
@RequiredArgsConstructor
public class PricePersistenceAdapter implements PriceRepository {

    private final PriceJpaRepository priceJpaRepository;
    private final PriceJpaMapper priceJpaMapper;

    @Override
    public Price findApplicablePrice(Long brandId, Long productId, Instant onDate) {

        Optional<PriceJpaEntity> result = priceJpaRepository.findOne(
                PriceJpaSpecification.findApplicablePrice(brandId, productId, onDate)
        );

        return result.map(priceJpaEntity -> priceJpaMapper.toDomain(priceJpaEntity, onDate)).orElse(null);
    }
}
