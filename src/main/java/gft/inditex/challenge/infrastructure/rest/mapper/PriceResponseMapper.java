package gft.inditex.challenge.infrastructure.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import gft.inditex.challenge.domain.model.Price;
import gft.inditex.challenge.infrastructure.rest.dto.PriceResponse;

/**
 * Mapper for converting between Price domain model and PriceResponse DTO.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PriceResponseMapper {

    PriceResponse toResponse(Price price);
}
