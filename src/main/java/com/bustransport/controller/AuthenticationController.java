package com.bustransport.controller;

import com.bustransport.LoggingController;
import com.bustransport.dto.RestResponseDto;
import com.bustransport.dto.UserDto;
import com.bustransport.dto.UserRequestDto;
import com.bustransport.dto.UserResponseDto;
import com.bustransport.exception.ServiceException;
import com.bustransport.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationServiceImpl authenticationServiceImpl;
    LoggingController loggingController = new LoggingController();

    @PostMapping("/save")
    public ResponseEntity<RestResponseDto<String>> save(@RequestBody UserDto userDto){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(authenticationServiceImpl.save(userDto),
                            "CreateUserSuccessfully",
                            loggingController.createLog("Kullanıcı oluşturuldu")),
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

    @GetMapping("/auth")
    public ResponseEntity<RestResponseDto<UserResponseDto>> auth(@RequestBody UserRequestDto userRequestDto){
        try {
            return new ResponseEntity<>(
                    new RestResponseDto(authenticationServiceImpl.auth(userRequestDto),
                            "GetUserSuccessfully",
                            loggingController.createLog("Kullanıcı bulundu")),
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
}

