package com.bustransport.service;

import com.bustransport.dto.BusDto;
import com.bustransport.entity.Bus;

import java.util.List;

public interface BusService {

    BusDto createBus(Bus bus);
    List<BusDto> getBuses();

    BusDto getBus(Long id);

    BusDto updateBus(Long id, Bus bus);

    Boolean deleteBus(Long id);
}
