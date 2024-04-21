package com.bustransport.controller;

import com.bustransport.LoggingController;
import com.bustransport.dto.BusDto;
import com.bustransport.dto.RestResponseDto;
import com.bustransport.entity.Bus;
import com.bustransport.exception.ServiceException;
import com.bustransport.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dashboard/bus")
public class BusController {
    LoggingController loggingController = new LoggingController();


    @Autowired
    private BusService busService;

    @PostMapping("/create")
    @PreAuthorize("@checkPermission.hasPermission(authentication, 'ADMIN_CREATE')")
    public ResponseEntity<RestResponseDto<BusDto>> createUser(@RequestBody Bus bus){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(busService.createBus(bus),
                            "CreateBusSuccessfully",
                            loggingController.createLog("Otobüs oluşturuldu")),
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
    public ResponseEntity<RestResponseDto<List<BusDto>>> getBuses(){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(busService.getBuses(),
                            "Successfully",
                            loggingController.createLog("Otobüsler Bulundu")),
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
    public ResponseEntity<RestResponseDto<BusDto>> getBus(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(busService.getBus(id),
                            "Successfully",
                            loggingController.createLog(id+ " Numaralı otobüs bulundu")),
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
    public ResponseEntity<RestResponseDto<BusDto>> updateBus(@PathVariable("id") Long id, @RequestBody Bus bus){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(busService.updateBus(id,bus),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı otobüs düzenlendi")),
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
    public ResponseEntity<RestResponseDto<Boolean>> deleteBus(@PathVariable("id") Long id){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(busService.deleteBus(id),
                            "Successfully",
                            loggingController.createLog(id+" Numaralı otobüs silindi")),
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
