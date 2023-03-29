package com.ecommerce.projectd.repositories;

import com.ecommerce.projectd.entities.Irrigation;
import com.ecommerce.projectd.entities.projection.IrrigationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IIrrigationRepository extends JpaRepository<Irrigation, Long> {
    @Query(value = "SELECT * FROM risks WHERE irrigation_system_id=:systemId", nativeQuery = true)
    List<IrrigationProjection> findRisksBySystemId(Long systemId);
}
