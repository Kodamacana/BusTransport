package com.bustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusDto {

    private Long id;

    private String plate;

    private String color;

    private int capacity;

    private RouteDto routeDto;
}
