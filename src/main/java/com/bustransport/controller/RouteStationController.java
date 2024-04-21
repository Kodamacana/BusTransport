package com.bustransport.controller;

import com.bustransport.LoggingController;
import com.bustransport.dto.RestResponseDto;
import com.bustransport.dto.RouteStationDto;
import com.bustransport.entity.RouteStation;
import com.bustransport.exception.ServiceException;
import com.bustransport.service.RouteStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard/RouteStation")
public class RouteStationController {
    LoggingController loggingController = new LoggingController();
    @Autowired
    private RouteStationService routeStationService;

    @PostMapping("/create")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<RestResponseDto<RouteStationDto>> createRouteStation(@RequestBody RouteStation routeStation){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(routeStationService.createRouteStation(routeStation),
                            "Successfully",
                            loggingController.createLog("İlişki kuruldu")),
                    HttpStatus.OK);
        }
        catch (ServiceException e){
            return new ResponseEntity<>(
                    new RestResponseDto(
                            null,
                            e.getMessageLanguageKey(),
                            loggingController.createLog(e.getMessage())),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
