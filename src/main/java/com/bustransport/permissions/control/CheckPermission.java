package com.bustransport.permissions.control;

import com.bustransport.permissions.enums.Permission;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component("checkPermission")
public class CheckPermission {
    public boolean hasPermission(Authentication authentication, Permission permission) {
        if (Objects.isNull(authentication)) {
            return false;
        }
        if (Objects.isNull(permission)) {
            throw new NullPointerException("hasPermission'ye gelen yetki boş olmamalı!");
        }

        boolean controlResult = authentication.getAuthorities().stream().map(object ->
                        Objects.toString(object, null))
                        .collect(Collectors.toList()).contains(permission.name());
        return controlResult;
    }
}

