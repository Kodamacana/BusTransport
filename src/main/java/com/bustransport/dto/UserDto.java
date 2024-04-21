package com.bustransport.dto;

import com.bustransport.permissions.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String nameSurname;
    private String username;
    private String password;
    private Role role;
}
