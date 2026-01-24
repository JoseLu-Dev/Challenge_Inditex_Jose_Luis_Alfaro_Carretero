package gft.inditex.challenge.infrastructure.db.jpa.repository;

import gft.inditex.challenge.infrastructure.db.jpa.entity.PriceJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for price entities.
 */
@Repository
public interface PriceJpaRepository extends JpaRepository<PriceJpaEntity, Long>,
        JpaSpecificationExecutor<PriceJpaEntity> {
}
