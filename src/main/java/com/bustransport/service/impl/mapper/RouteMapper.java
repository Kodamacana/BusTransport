package com.bustransport.service.impl.mapper;

import com.bustransport.dto.RouteDto;
import com.bustransport.entity.Route;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="averageDuration", source="averageDuration"),
            @Mapping(target="startState", source="startState"),
            @Mapping(target="finalState", source="finalState")
    })
    RouteDto Dto(Route entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="averageDuration", source="averageDuration"),
            @Mapping(target="startState", source="startState"),
            @Mapping(target="finalState", source="finalState")
    })
    Route toEntity(RouteDto dto);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="averageDuration", source="averageDuration"),
            @Mapping(target="startState", source="startState"),
            @Mapping(target="finalState", source="finalState")
    })
    List< RouteDto> Dto(List< Route> entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="averageDuration", source="averageDuration"),
            @Mapping(target="startState", source="startState"),
            @Mapping(target="finalState", source="finalState")
    })
    List<  Route> toEntity(List< RouteDto> dto);
}
