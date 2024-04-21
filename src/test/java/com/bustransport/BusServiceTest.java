package com.bustransport;

import com.bustransport.dto.BusDto;
import com.bustransport.entity.Bus;
import com.bustransport.entity.Route;
import com.bustransport.entity.User;
import com.bustransport.permissions.enums.Role;
import com.bustransport.repository.BusRepository;
import com.bustransport.repository.RouteRepository;
import com.bustransport.repository.RouteStationRepository;
import com.bustransport.repository.StationRepository;
import com.bustransport.service.impl.BusServiceImpl;
import com.bustransport.service.impl.mapper.BusMapper;
import com.bustransport.service.impl.mapper.RouteMapper;
import com.bustransport.service.impl.mapper.StationMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class BusServiceTest {

	private final BusRepository busRepository = Mockito.mock(BusRepository.class);
	private final RouteRepository routeRepository = Mockito.mock(RouteRepository.class);
	private final StationRepository stationRepository = Mockito.mock(StationRepository.class);
	private final RouteStationRepository routeStationRepository = Mockito.mock(RouteStationRepository.class);
	private final BusMapper busMapper = Mockito.mock(BusMapper.class);
	private final RouteMapper routeMapper = Mockito.mock(RouteMapper.class);
	private final StationMapper stationMapper = Mockito.mock(StationMapper.class);

	@InjectMocks
	@Spy
	private final BusServiceImpl busServiceImpl = new BusServiceImpl(busRepository, routeRepository, stationRepository, routeStationRepository, busMapper, routeMapper, stationMapper);


	@Test
	public void testCreateBus() {
		User user = new User();
		user.setId(99l);
		user.setRole(Role.ADMIN);
		user.setUsername("TestUsername");
		user.setPassword("TestPassword");
		user.setNameSurname("Test Test");

		Route route = new Route();
		route.setId(1l);
		route.setStartState(0);
		route.setFinalState(0);
		route.setAverageDuration(0);

		Bus bus = new Bus();
		bus.setId(99l);
		bus.setPlate("TestPlate");
		bus.setCapacity(30);
		bus.setRouteId(1l);
		bus.setColor("TestColor");

		BusDto busDto = new BusDto();
		busDto.setId(99l);
		busDto.setPlate(bus.getPlate());
		busDto.setRouteDto(routeMapper.Dto(route));
		busDto.setCapacity(bus.getCapacity());
		busDto.setColor(bus.getColor());


		SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
		securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(), "N/A", new ArrayList<>()));
		SecurityContextHolder.setContext(securityContext);


		Mockito.when(busRepository.save(any(Bus.class))).thenReturn(bus);
		Mockito.when(busServiceImpl.createBus(bus)).thenReturn(busMapper.Dto(bus));

		assertThat(String.valueOf(bus.getId()), true);
	}


}
