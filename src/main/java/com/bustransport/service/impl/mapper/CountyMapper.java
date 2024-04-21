package com.bustransport.service.impl.mapper;

import com.bustransport.dto.CountyDto;
import com.bustransport.entity.County;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountyMapper {

    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="name", source="name")
    })
    CountyDto Dto(County entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="name", source="name")
    })
    County toEntity(CountyDto dto);

    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="name", source="name")
    })
    List< CountyDto> Dto(List< County> entity);
    @Mappings({
            @Mapping(target="id", source="id"),
            @Mapping(target="name", source="name")
    })
    List< County> toEntity(List< CountyDto> dto);
}
