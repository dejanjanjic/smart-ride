package net.etfbl.ip.smart_ride_backend.repository;

import jakarta.transaction.Transactional;
import net.etfbl.ip.smart_ride_backend.model.Failure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FailureRepository extends JpaRepository<Failure, Long> {
    List<Failure> findAllByVehicle_Id(String vehicleId);
    @Query("SELECT f.vehicle.id, f.vehicle.manufacturer.name, f.vehicle.model, COUNT(f) " +
            "FROM Failure f GROUP BY f.vehicle.id, f.vehicle.manufacturer.name, f.vehicle.model")
    List<Object[]> countFailuresPerVehicle();
    @Transactional
    void deleteAllByVehicle_Id(String vehicleId);

}
