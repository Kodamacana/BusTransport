package com.bustransport.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StationDto {

    private long id;

    private CountyDto countyDto;

    private String name;

    private String street;

    private long coordinate;
}
