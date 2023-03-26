package com.ecommerce.projectd.repositories;

import com.ecommerce.projectd.entities.IrrigationSystem;
import com.ecommerce.projectd.entities.projection.IrrigationSystemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IIrrigationSystemRepository extends JpaRepository<IrrigationSystem, Long> {

    @Query(value = "SELECT * FROM irrigation_systems WHERE user_id=:userId", nativeQuery = true)
    List<IrrigationSystemProjection> findRisksByUserId(Long userId);
}
