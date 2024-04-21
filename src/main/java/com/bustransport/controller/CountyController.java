package com.bustransport.controller;

import com.bustransport.LoggingController;
import com.bustransport.dto.CountyDto;
import com.bustransport.dto.RestResponseDto;
import com.bustransport.entity.County;
import com.bustransport.exception.ServiceException;
import com.bustransport.service.CountyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/county")
public class CountyController {

    LoggingController loggingController = new LoggingController();
    @Autowired
    private CountyService countyService;

    @PostMapping("/create")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<RestResponseDto<CountyDto>> createCounty(@RequestBody County county){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(countyService.createCounty(county),
                            "Successfully",
                            loggingController.createLog("Bölge oluşturuldu")),
                    HttpStatus.OK);
        }
        catch (ServiceException e){
            return new ResponseEntity<>(
                    new RestResponseDto(
                            null,
                            e.getMessageLanguageKey(),
                            e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE);
        }

    }
    @GetMapping("/getall")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'USER_READ')")
    public ResponseEntity<RestResponseDto<List<CountyDto>>> getCounties(){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(countyService.getCounties(),
                            "Successfully",
                            loggingController.createLog("Bölgeler Bulundu")),
                    HttpStatus.OK);
        }
        catch (ServiceException e){
            return new ResponseEntity<>(
                    new RestResponseDto(
                            null,
                            e.getMessageLanguageKey(),
                            e.getMessage()),
                    HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getbyid/{id}")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'USER_READ')")
    public ResponseEntity<RestResponseDto<CountyDto>> getCounty(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(countyService.getCountyById(id),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı bölge bulundu")),
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
    public ResponseEntity<RestResponseDto<Boolean>> deleteCounty(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(countyService.deleteCounty(id),
                            "Successfully",
                            loggingController.createLog(id+ " Numaralı bölge silindi")),
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
