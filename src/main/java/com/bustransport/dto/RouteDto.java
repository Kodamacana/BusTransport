package com.bustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteDto {

    private long id;

    private int averageDuration;

    private String startState;

    private String finalState;

    private List<StationDto> stations;

}
