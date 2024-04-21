package com.bustransport.service.impl;

import com.bustransport.dto.CountyDto;
import com.bustransport.entity.County;
import com.bustransport.exception.ServiceException;
import com.bustransport.repository.CountyRepository;
import com.bustransport.service.CountyService;
import com.bustransport.service.impl.mapper.CountyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CountyServiceImpl implements CountyService {

    private final CountyRepository countyRepository;
    private final CountyMapper countyMapper;

    @Override
    public CountyDto createCounty(County county) {
        try {
            if (county.getName().isEmpty()){
                throw new ServiceException("CountyNameNotNull","İlçe adı boş olamaz");
            }
            County countyData = countyRepository.save(county);
            CountyDto countyDto = countyMapper.Dto(countyData);
            return countyDto;
        }
        catch (Exception e){
            throw e;
        }

    }

    @Override
    public List<CountyDto> getCounties() {
        try {
            List<County> counties = countyRepository.findAll();
            if (counties.isEmpty()){
                throw new ServiceException("CountyListNull","İlçe listesi boş");
            }
            List<CountyDto> countyDtos = countyMapper.Dto(counties);
            return countyDtos;
        }
        catch (Exception e){
            throw e;
        }
    }

    @Override
    public CountyDto getCountyById(Long id) {
        Optional<County> county = countyRepository.findById(id);
        if (county.isPresent()){
            CountyDto countyDto = countyMapper.Dto(county.get());
            return countyDto;
        }
        else throw new ServiceException("CountyNotFound","İlçe bulunamadı");
    }

    @Override
    public Boolean deleteCounty(Long id) {
        Optional<County> county = countyRepository.findById(id);
        if (county.isPresent()){
            countyRepository.deleteById(id);
            return true;
        }
        else throw new ServiceException("CountyNotFound","İlçe bulunamadı");
    }
}
