package com.bustransport.repository;

import com.bustransport.entity.RouteStation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface RouteStationRepository extends JpaRepository<RouteStation,Long> {

        List<RouteStation> findByRoute_Id(Long id);

        Optional<RouteStation> deleteAllByRoute_Id(Long id);

}
