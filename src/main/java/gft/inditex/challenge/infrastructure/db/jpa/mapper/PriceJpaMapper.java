package gft.inditex.challenge.infrastructure.db.jpa.mapper;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.db.jpa.entity.PriceJpaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.Instant;

/**
 * MapStruct mapper for converting between JPA entity and domain model.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceJpaMapper {

    /**
     * Converts a JPA entity to the domain model.
     *
     * @param entity the JPA entity
     * @param onDate the query date to set in the domain model
     * @return the domain model
     */
    @Mapping(source = "entity.priceListId", target = "appliedTariffId")
    @Mapping(source = "entity.price", target = "finalPrice")
    @Mapping(source = "onDate", target = "onDate")
    Price toDomain(PriceJpaEntity entity, Instant onDate);
}
