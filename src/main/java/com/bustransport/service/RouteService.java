package com.bustransport.service;


import com.bustransport.dto.RouteDto;
import com.bustransport.entity.Route;

import java.util.List;

public interface RouteService {

    RouteDto creatRoute(Route route);

    List<RouteDto> getAllRoutes();

    RouteDto getRoute(Long id);

    RouteDto updateRoute(Long id, Route route);

    Boolean deleteRoute(Long id);
}
