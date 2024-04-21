package com.bustransport.service.impl.mapper;

import com.bustransport.dto.StationDto;
import com.bustransport.entity.Station;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StationMapper {
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="countyDto", source="county"),
            @Mapping(target="name", source="name"),
            @Mapping(target="street", source="street"),
            @Mapping(target="coordinate", source="coordinate")
    })
    StationDto Dto(Station entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="county", source="countyDto"),
            @Mapping(target="name", source="name"),
            @Mapping(target="street", source="street"),
            @Mapping(target="coordinate", source="coordinate")
    })
    Station toEntity(StationDto dto);

    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="county", source="county"),
            @Mapping(target="name", source="name"),
            @Mapping(target="street", source="street"),
            @Mapping(target="coordinate", source="coordinate")
    })
    List<StationDto> Dto(List<Station> entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="county", source="county"),
            @Mapping(target="name", source="name"),
            @Mapping(target="street", source="street"),
            @Mapping(target="coordinate", source="coordinate")
    })
    List< Station> toEntity( List<StationDto> dto);
}
