package com.bustransport.dto;

import com.bustransport.entity.Route;
import com.bustransport.entity.Station;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RouteStationDto {

    private long id;

    private Station station;

    private Route route;
}
