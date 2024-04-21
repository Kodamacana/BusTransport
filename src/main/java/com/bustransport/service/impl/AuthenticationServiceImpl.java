package com.bustransport.service.impl;

import com.bustransport.dto.UserDto;
import com.bustransport.dto.UserRequestDto;
import com.bustransport.dto.UserResponseDto;
import com.bustransport.entity.User;
import com.bustransport.exception.ServiceException;
import com.bustransport.repository.UserRepository;
import com.bustransport.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final UserRepository userRepository;

    private final JwtService jwtService;


    public String save(UserDto userDto) {
        User user = User.builder()
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .nameSurname(userDto.getNameSurname())
                .role(userDto.getRole())
                .build();

        userRepository.save(user);

        var token = jwtService.generateToken(user, userDto.getRole().getPermissions());
        return token;
    }

    public UserResponseDto auth(UserRequestDto userRequestDto) {
        User user = userRepository.findByUsername(userRequestDto.getUsername()).orElseThrow();
        if (userRequestDto.getPassword().equals(user.getPassword())) {
            String token = jwtService.generateToken(user, user.getRole().getPermissions());
            return UserResponseDto.builder().token(token).build();
        }
        throw new ServiceException("UserNotFound","User bulunamadı");
    }
}
