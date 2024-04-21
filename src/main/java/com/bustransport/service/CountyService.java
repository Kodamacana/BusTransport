package com.bustransport.service;

import com.bustransport.dto.CountyDto;
import com.bustransport.entity.County;

import java.util.List;

public interface CountyService {

    CountyDto createCounty(County county);

    List<CountyDto> getCounties();

    CountyDto getCountyById(Long id);

    Boolean deleteCounty(Long id);
}
