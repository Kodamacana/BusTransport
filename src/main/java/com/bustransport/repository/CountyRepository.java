package com.bustransport.repository;

import com.bustransport.entity.County;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CountyRepository extends JpaRepository<County,Long> {

}
