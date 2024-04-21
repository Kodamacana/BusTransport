package com.bustransport.service;

import com.bustransport.dto.StationDto;
import com.bustransport.entity.Station;

import java.util.List;

public interface StationService {

    StationDto createStation(Station station);

    List<StationDto> getAllStations();

    StationDto getStation(Long id);

    StationDto updateStation(Long id, Station station);

    Boolean deleteStation(Long id);
}
