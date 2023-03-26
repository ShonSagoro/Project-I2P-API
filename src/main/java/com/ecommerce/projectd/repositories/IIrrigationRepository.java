package com.ecommerce.projectd.repositories;

import com.ecommerce.projectd.entities.Irrigation;
import com.ecommerce.projectd.entities.projection.IrrigationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IIrrigationRepository extends JpaRepository<Irrigation, Long> {
    @Query(value = "SELECT * FROM risks WHERE user_id=:userId", nativeQuery = true)
    List<IrrigationProjection> findRisksByUserId(Long userId);
}
