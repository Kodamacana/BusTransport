package com.bustransport.repository;

import com.bustransport.entity.Route;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Transactional
public interface RouteRepository extends JpaRepository<Route,Long> {

}
