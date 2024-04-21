package com.bustransport.service.impl;

import com.bustransport.dto.RouteStationDto;
import com.bustransport.entity.Route;
import com.bustransport.entity.RouteStation;
import com.bustransport.entity.Station;
import com.bustransport.exception.ServiceException;
import com.bustransport.repository.RouteRepository;
import com.bustransport.repository.RouteStationRepository;
import com.bustransport.repository.StationRepository;
import com.bustransport.service.RouteStationService;
import com.bustransport.service.impl.mapper.RouteStationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteStationServiceImpl implements RouteStationService {

    private final RouteStationRepository routeStationRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final RouteStationMapper routeStationMapper;

    @Override
    public RouteStationDto createRouteStation(RouteStation routeStation) {
        try {
            if (Objects.isNull(routeStation.getStation())){
                throw new ServiceException("stationValueNotNull","station değeri boş olamaz");
            } else if (Objects.isNull(routeStation.getRoute())) {
                throw new ServiceException("routeValueNotNull","route değeri boş olamaz");
            }
            RouteStation routeStationData =  routeStationRepository.save(routeStation);
            RouteStationDto routeStationDto = routeStationMapper.Dto(routeStationData);

            Optional<Route> route = routeRepository.findById(routeStation.getRoute().getId());
            Optional<Station> station = stationRepository.findById(routeStation.getStation().getId());

            routeStationDto.setStation(station.get());
            routeStationDto.setRoute(route.get());
            return routeStationDto;
        }
        catch (Exception e){
            throw e;
        }
    }

}
