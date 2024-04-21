package com.bustransport.controller;

import com.bustransport.LoggingController;
import com.bustransport.dto.RestResponseDto;
import com.bustransport.dto.StationDto;
import com.bustransport.entity.Station;
import com.bustransport.exception.ServiceException;
import com.bustransport.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/station")
public class StationController {
    LoggingController loggingController = new LoggingController();
    @Autowired
    private StationService stationService;

    @PostMapping("/create")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<RestResponseDto<StationDto>> createStation (@RequestBody Station station){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(stationService.createStation(station),
                            "Successfully",
                            loggingController.createLog("İstasyon oluşturuldu")),
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
    public ResponseEntity<RestResponseDto<List<StationDto>>> getStations(){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(stationService.getAllStations(),
                            "Successfully",
                            loggingController.createLog("İstasyonlar Bulundu")),
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
    public ResponseEntity<RestResponseDto<StationDto>> getStation(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(stationService.getStation(id),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı istasyon bulundu")),
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
    public ResponseEntity<RestResponseDto<StationDto>> updateStation(@PathVariable("id") Long id, @RequestBody Station station) {
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(stationService.updateStation(id, station),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı istasyon düzenlendi")),
                    HttpStatus.OK);
        } catch (ServiceException e) {
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
    public ResponseEntity<RestResponseDto<Boolean>> deleteStation(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(stationService.deleteStation(id),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı istasyon silindi")),
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
