package com.bustransport.service.impl;

import com.bustransport.dto.StationDto;
import com.bustransport.entity.Station;
import com.bustransport.exception.ServiceException;
import com.bustransport.repository.StationRepository;
import com.bustransport.service.StationService;
import com.bustransport.service.impl.mapper.StationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;
    private final StationMapper stationMapper;

    @Override
    public StationDto createStation(Station station) {
        try {
            if (Objects.isNull(station.getName()) || station.getName().isEmpty()){
                throw new ServiceException("stationNameNotNull","Durak adı boş olamaz");
            }
            else if (Objects.isNull(station.getCounty())) {
                throw new ServiceException("stationCountyNotExceptable","Durak bölgesi boş olamaz");
            }
            else if (Objects.isNull(station.getCoordinate())){
                throw new ServiceException("stationCoordinateNotNull","Durak koordinatı boş olamaz");
            }
            else if (Objects.isNull(station.getStreet()) || station.getStreet().isEmpty()){
                throw new ServiceException("stationStreetNotNull","Durak sokağı boş olamaz");
            }
            Station stationData =  stationRepository.save(station);
            StationDto stationDto = stationMapper.Dto(stationData);
            return stationDto;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public StationDto getStation(Long id) {
        Optional<Station> station = stationRepository.findById(id);
        if (station.isPresent()){
            StationDto stationDto = stationMapper.Dto(station.get());
            return stationDto;
        }
        throw new ServiceException("stationNotNull","Bu id ile belirlenen durak bulunamadı");
    }

    @Override
    public List<StationDto> getAllStations() {
        List<Station> stations = stationRepository.findAll();

        if (Objects.isNull(stations)){
            throw new ServiceException("stationListNull","Durak listesi boş");
        }
        List<StationDto> stationDtos = stationMapper.Dto(stations);
        return stationDtos;
    }

    @Override
    public StationDto updateStation(Long id, Station station) {
        try{
            Optional<Station> resultStation = stationRepository.findById(id);
            if (resultStation.isPresent()){
                resultStation.get().setCoordinate(station.getCoordinate());
                resultStation.get().setName(station.getName());
                resultStation.get().setStreet(station.getStreet());
                resultStation.get().setCounty(station.getCounty());

                if (resultStation.get().getCoordinate() == 0 || resultStation.get().getCounty() == null || resultStation.get().getStreet() == null || resultStation.get().getName() == null){
                    throw new ServiceException("UpdatedStationValuesNotComplyRules","Değişmesi istenilen değerler kurallara uygun değil!");
                }
                Station stationData = stationRepository.save(resultStation.get());
                StationDto stationDto = stationMapper.Dto(stationData);
                return stationDto;
            }
            else throw new ServiceException("stationNotNull","Bu id ile belirlenen durak bulunamadı");
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public Boolean deleteStation(Long id) {
        Optional<Station> station = stationRepository.findById(id);
        if (station.isPresent()){
            stationRepository.deleteById(id);
            return true;
        }
        throw new ServiceException("stationNotNull","Bu id ile belirlenen durak bulunamadı");
    }
}
