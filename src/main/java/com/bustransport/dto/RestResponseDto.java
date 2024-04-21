package com.bustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RestResponseDto<T> {
    T data;
    String title;
    String message;
}
