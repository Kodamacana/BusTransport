package com.bustransport.service.impl.mapper;

import com.bustransport.dto.RouteStationDto;
import com.bustransport.entity.RouteStation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteStationMapper {
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="station", source="station"),
            @Mapping(target="route", source="route")
    })
    RouteStationDto Dto(RouteStation entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="station", source="station"),
            @Mapping(target="route", source="route")
    })
    RouteStation toEntity(RouteStationDto dto);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="station", source="station"),
            @Mapping(target="route", source="route")
    })
    List< RouteStationDto> Dto(List< RouteStation> entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="station", source="station"),
            @Mapping(target="route", source="route")
    })
    List< RouteStation> toEntity(List< RouteStationDto> dto);
}
