package com.bustransport.service.impl.mapper;

import com.bustransport.dto.BusDto;
import com.bustransport.entity.Bus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface BusMapper{

    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="plate", source="plate"),
            @Mapping(target="color", source="color"),
            @Mapping(target="capacity", source="capacity")
    })
    BusDto Dto(Bus entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="plate", source="plate"),
            @Mapping(target="color", source="color"),
            @Mapping(target="capacity", source="capacity")
    })
    Bus toEntity(BusDto dto);

}
