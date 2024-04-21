package com.bustransport;

import com.bustransport.dto.*;
import com.bustransport.entity.*;
import com.bustransport.permissions.enums.Role;
import com.bustransport.service.JwtService;
import com.bustransport.service.impl.mapper.CountyMapper;
import com.bustransport.service.impl.mapper.RouteMapper;
import com.bustransport.service.impl.mapper.StationMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtService jwtService ;

    @Autowired
    private RouteMapper routeMapper ;

    @Autowired
    private CountyMapper countyMapper;

    @Autowired
    private StationMapper stationMapper;

    public String obtainToken(User user) {
        return "Bearer " + jwtService.generateToken(user, user.getRole().getPermissions());
    }

    @Test
    public void createBusIntegrationTest() throws Exception{

        User user = new User(11l,"Adminadmin","ADMIN","ADMIN", Role.ADMIN);
        County county = new County();
        county.setId(99l);
        county.setName("County");

        CountyDto countyDto = countyMapper.Dto(county);

        Station firstStation = new Station();
        firstStation.setId(98l);
        firstStation.setCounty(county);
        firstStation.setStreet("StreetName");
        firstStation.setName("StationName");
        firstStation.setCoordinate(1l);

        Station lastStation = new Station();
        lastStation.setId(99l);
        lastStation.setCounty(county);
        lastStation.setStreet("StreetName");
        lastStation.setName("StationName");
        lastStation.setCoordinate(2l);

        StationDto firstStationDto = stationMapper.Dto(firstStation);
        StationDto lastStationDto = stationMapper.Dto(lastStation);

        firstStationDto.setCountyDto(countyDto);
        lastStationDto.setCountyDto(countyDto);
        List<StationDto> stationDtoList = new ArrayList<>();
        stationDtoList.add(firstStationDto);
        stationDtoList.add(lastStationDto);

        Route route = new Route();
        route.setId(1l);
        route.setStartState(98);
        route.setFinalState(99);
        route.setAverageDuration(20);

        RouteDto routeDto = routeMapper.Dto(route);
        routeDto.setStations(stationDtoList);

        RouteStation firstrouteStation = new RouteStation();
        firstrouteStation.setId(1l);
        firstrouteStation.setRoute(routeMapper.toEntity(routeDto));
        firstrouteStation.setStation(stationMapper.toEntity(firstStationDto));

        RouteStation lastrouteStation = new RouteStation();
        lastrouteStation.setId(2l);
        lastrouteStation.setRoute(routeMapper.toEntity(routeDto));
        lastrouteStation.setStation(stationMapper.toEntity(lastStationDto));

        Bus bus = new Bus();
        bus.setId(99l);
        bus.setPlate("TestPlate4");
        bus.setCapacity(30);
        bus.setColor("TestColor");
        bus.setRouteId(1l);


        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(bus);

        String token = obtainToken(user);

        mockMvc.perform(MockMvcRequestBuilders.post("/dashboard/bus/create")
                        .header(HttpHeaders.AUTHORIZATION, token)
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
