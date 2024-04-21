package com.bustransport.controller;

import com.bustransport.LoggingController;
import com.bustransport.dto.RestResponseDto;
import com.bustransport.dto.RouteDto;
import com.bustransport.entity.Route;
import com.bustransport.exception.ServiceException;
import com.bustransport.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/route")
public class RouteController {
    LoggingController loggingController = new LoggingController();
    @Autowired
    private RouteService routeService;

    @PostMapping("/create")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<RestResponseDto<RouteDto>> createRoute(@RequestBody Route route){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(routeService.creatRoute(route),
                            "Successfully",
                            loggingController.createLog("Rota oluşturuldu")),
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

    @GetMapping("/getall")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'USER_READ')")
    public ResponseEntity<RestResponseDto<List<RouteDto>>> getRoutes(){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(routeService.getAllRoutes(),
                            "Successfully",
                            loggingController.createLog("Rotalar Bulundu")),
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

    @GetMapping("/getbyid/{id}")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'USER_READ')")
    public ResponseEntity<RestResponseDto<RouteDto>> getRoute(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(routeService.getRoute(id),
                            "Successfully",
                            loggingController.createLog(id+ "Numaralı Rota bulundu")),
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


    @PutMapping("/edit/{id}")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_UPDATE')")
    public ResponseEntity<RestResponseDto<RouteDto>> updateRoute(@PathVariable("id") Long id, @RequestBody Route route){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(routeService.updateRoute(id,route),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı rota düzenlendi")),
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

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_DELETE')")
    public ResponseEntity<RestResponseDto<Boolean>> deleteRoute(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(routeService.deleteRoute(id),
                            "Successfully",
                            loggingController.createLog(id+ " Numaralı rota silindi")),
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
