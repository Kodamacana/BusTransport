package com.bustransport.service.impl;

import com.bustransport.dto.RouteDto;
import com.bustransport.dto.RouteStationDto;
import com.bustransport.dto.StationDto;
import com.bustransport.entity.*;
import com.bustransport.exception.ServiceException;
import com.bustransport.repository.RouteRepository;
import com.bustransport.repository.RouteStationRepository;
import com.bustransport.repository.StationRepository;
import com.bustransport.service.RouteService;
import com.bustransport.service.impl.mapper.RouteMapper;
import com.bustransport.service.impl.mapper.StationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;

    private final RouteStationRepository routeStationRepository;

    private final StationRepository stationRepository;

    private final RouteMapper routeMapper;

    private final StationMapper stationMapper;

    @Override
    public RouteDto creatRoute(Route route) {
        try {
            Route routeData = routeRepository.save(route);
            RouteDto routeDto = routeMapper.Dto(routeData);

            Optional<Station> startStation = stationRepository.findById(route.getStartState());
            Optional<Station> finalStation = stationRepository.findById(route.getFinalState());

            List<Station> stations = Arrays.asList(startStation.get(), finalStation.get());
            return stationSavetoDB(stations,routeData,routeDto);
        }
        catch (Exception e){
            throw e;
        }
    }

    private RouteDto stationSavetoDB(List<Station> stationList ,Route route, RouteDto routeDto){
        stationList.forEach(station -> {
            RouteStation routeStation = new RouteStation();
            routeStation.setStation(station);
            routeStation.setRoute(route);
            routeStationRepository.save(routeStation);
        });

        List<StationDto> stationDtoList = stationMapper.Dto(stationList);
        routeDto.setStations(stationDtoList);

        return routeDto;
    }

    @Override
    public List<RouteDto> getAllRoutes() {
        try {
            List<RouteDto> routeDtoList = new ArrayList<>();

            List<Route> routes = routeRepository.findAll();

            if (Objects.isNull(routes) || routes.isEmpty()){
                throw new ServiceException("RouteListEmpty","Rota listesi boş");
            }

            routes.stream().forEach(route -> {
                List<RouteStation> routeStations = routeStationRepository.findByRoute_Id(route.getId());

                if (Objects.isNull(routeStations) || routeStations.isEmpty()){

                    throw new ServiceException("RouteStationSubNotFound","Belirtilen Rota id tabloda bulunamadı");
                }
                List<Station> stations = new ArrayList<>();
                for (RouteStation routeStation : routeStations) {
                    stations.add(routeStation.getStation());
                }

                RouteDto routeDto = routeMapper.Dto(route);
                List<StationDto> stationDtos = stationMapper.Dto(stations);
                routeDto.setStations(stationDtos);
                routeDtoList.add(routeDto);
            });

            return routeDtoList;
        }
        catch (Exception e){
            throw e;
        }

    }

    @Override
    public RouteDto getRoute(Long id) {
        try {
            Optional<Route> route = routeRepository.findById(id);
            List<RouteStation> routeStations = routeStationRepository.findByRoute_Id(id);

            if (Objects.isNull(route)){
                throw new ServiceException("routeNotFound","Belirtilen id değerine sahip rota bulunamadı");
            } else if (Objects.isNull(routeStations)) {
                throw new ServiceException("routeStationSubNotFound","Belirtilen id değerine sahip rota-istasyon ilişkisi bulunamadı");
            }

            List<Station> stations = new ArrayList<>();
            for (RouteStation routeStation : routeStations) {
                stations.add(routeStation.getStation());
            }

            Route routeData = routeRepository.save(route.get());
            RouteDto routeDto = routeMapper.Dto(routeData);

            List<StationDto> stationDto = stationMapper.Dto(stations);
            routeDto.setStations(stationDto);

            return routeDto;
        }
        catch (Exception e){
            throw e;
        }

    }


    @Override
    public RouteDto updateRoute(Long id, Route route) {

        try {
            List<RouteStation> routeStations = routeStationRepository.findByRoute_Id(id);
            Optional<Route> routeData = routeRepository.findById(id);
            if (Objects.isNull(routeStations)) {
                throw new ServiceException("routeStationSubNotFound","Belirtilen id değerine sahip rota-istasyon ilişkisi bulunamadı");
            }
            else  if (Objects.isNull(route)){
                throw new ServiceException("routeNotFound","Belirtilen id değerine sahip rota bulunamadı");
            }

            List<Station> stations = new ArrayList<>();
            RouteDto routeDto = routeMapper.Dto(routeData.get());
            for (RouteStation routeStation : routeStations) {

                if (routeStation.getStation().getId() == route.getStartState()) {
                    routeDto.setStartState( routeStation.getStation().getName());
                }
                else if (routeStation.getStation().getId() == route.getFinalState()) {
                    routeDto.setFinalState( routeStation.getStation().getName());
                }
                routeDto.setAverageDuration(route.getAverageDuration());

                stations.add(routeStation.getStation());
            }
            List<StationDto> stationDto = stationMapper.Dto(stations);
            routeDto.setStations(stationDto);

            return routeDto;
        }
        catch (Exception e){
            throw e;
        }
    }


    @Override
    public Boolean deleteRoute(Long id) {
        Optional<Route> route = routeRepository.findById(id);

        if (route.isPresent()) {
            routeStationRepository.deleteAllByRoute_Id(id);
            routeRepository.deleteById(id);

            return true;
        }
        throw new ServiceException("routeNotFound","Belirtilen id değerine sahip rota bulunamadı");
    }

}
