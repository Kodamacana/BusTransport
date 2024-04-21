package com.bustransport.service.impl;

import com.bustransport.dto.BusDto;
import com.bustransport.dto.RouteDto;
import com.bustransport.dto.StationDto;
import com.bustransport.entity.Bus;
import com.bustransport.entity.Route;
import com.bustransport.entity.RouteStation;
import com.bustransport.entity.Station;
import com.bustransport.exception.ServiceException;
import com.bustransport.repository.BusRepository;
import com.bustransport.repository.RouteRepository;
import com.bustransport.repository.RouteStationRepository;
import com.bustransport.repository.StationRepository;
import com.bustransport.service.BusService;
import com.bustransport.service.impl.mapper.BusMapper;
import com.bustransport.service.impl.mapper.RouteMapper;
import com.bustransport.service.impl.mapper.StationMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;
    private final RouteRepository routeRepository;
    private final StationRepository stationRepository;
    private final RouteStationRepository routeStationRepository;
    private final BusMapper busMapper;
    private final RouteMapper routeMapper;
    private final StationMapper stationMapper;

    public BusServiceImpl(BusRepository busRepository, RouteRepository routeRepository, StationRepository stationRepository, RouteStationRepository routeStationRepository, BusMapper busMapper, RouteMapper routeMapper, StationMapper stationMapper){

        this.busRepository = busRepository;
        this.routeRepository = routeRepository;
        this.stationRepository = stationRepository;
        this.routeStationRepository = routeStationRepository;
        this.busMapper = busMapper;
        this.routeMapper = routeMapper;
        this.stationMapper = stationMapper;
    }

    @Override
    public BusDto createBus(Bus bus) {
        try {
            if (Objects.isNull(bus.getPlate()) || bus.getPlate().isEmpty()){
                throw new ServiceException("busPlateNotNull","Otobüs plakası boş olamaz");
            } else if (bus.getCapacity() <= 0) {
                throw new ServiceException("busCapacityNotExceptable","Otobüs kapasitesi değeri sıfır ve/veya daha küçük olamaz");
            }
            else if (bus.getRouteId() <= 0) {
                throw new ServiceException("busRouteNotExceptable","Rota değeri sıfır ve/veya daha küçük olamaz");
            }
            Bus busData = busRepository.save(bus);
            BusDto busDto = busMapper.Dto(busData);
            return busDto;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<BusDto> getBuses() {

        List<BusDto> busDtos = new ArrayList<>();
        List<Bus> buses = busRepository.findAll();

        if (Objects.isNull(buses) || buses.isEmpty()){
            throw new ServiceException("BusInventoryNull","Otobüs envanteri boş");
        } else {
            buses.stream().forEach(bus -> {
                Optional<Route> routes  = routeRepository.findById(bus.getRouteId());

                if (routes.isPresent()) {
                    BusDto busDto = busMapper.Dto(bus);
                    RouteDto routeDto = routeMapper.Dto(routes.get());
                    busDto.setRouteDto(routeDto);

                    busDtos.add(busDto);
                }
            });
        }
        return busDtos;
    }

    @Override
    public BusDto getBus(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        if (bus.isPresent()) {
            Optional<Route> routes  = routeRepository.findById(bus.get().getRouteId());
            if (routes.isPresent()) {
                BusDto busDtos = busMapper.Dto(bus.get());
                RouteDto routeDto = routeMapper.Dto(routes.get());
                List<RouteStation> routeStations = routeStationRepository.findByRoute_Id(routes.get().getId());

                if (Objects.isNull(routeStations) || routeStations.isEmpty()){
                    throw new ServiceException("RouteStationSubNotFound","Belirtilen Rota id tabloda bulunamadı");
                }
                List<Station> stations = new ArrayList<>();
                for (RouteStation routeStation : routeStations) {
                    stations.add(routeStation.getStation());
                }

                List<StationDto> stationDtoList =  stationMapper.Dto(stations);
                routeDto.setStations(stationDtoList);
                busDtos.setRouteDto(routeDto);
                return busDtos;
            }
            return null;
        }
        else {
            throw new ServiceException("BusNotFound","Bu id ile tutulan Otobüs yok ");
        }
    }
    @Override
    public BusDto updateBus(Long id, Bus bus) {
        Optional<Bus> resultBus = busRepository.findById(id);
        if (resultBus.isPresent()){
            resultBus.get().setCapacity(bus.getCapacity());
            resultBus.get().setRouteId(bus.getRouteId());
            resultBus.get().setColor(bus.getColor());
            resultBus.get().setPlate(bus.getPlate());

            if (bus.getCapacity() != 0 || bus.getRouteId() != null || bus.getPlate() != null ){
                throw new ServiceException("UpdatedBusValuesNotComplyRules","Değişmesi istenilen değerler kurallara uygun değil!");
            }
            BusDto busDto =  busMapper.Dto(resultBus.get());
            Optional<Route> route = routeRepository.findById(resultBus.get().getRouteId());
            RouteDto routeDto =  routeMapper.Dto(route.get());
            busDto.setRouteDto(routeDto);

            Bus busData = busRepository.save(busMapper.toEntity(busDto));

            return busMapper.Dto(busData);
        }
        else throw new ServiceException("BusNotFound","Bu id ile tutulan otobüs bulunmamaktadır");
    }

    @Override
    public Boolean deleteBus(Long id) {
        Optional<Bus> bus = busRepository.findById(id);
        if (bus.isPresent()){
            busRepository.deleteById(id);
            return true;
        }
        throw new ServiceException("BusNotFound","Bu id ile tutulan otobüs bulunmamaktadır");
    }

}