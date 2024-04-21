package com.bustransport.repository;

import com.bustransport.entity.Bus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BusRepository extends JpaRepository<Bus,Long> {

}
