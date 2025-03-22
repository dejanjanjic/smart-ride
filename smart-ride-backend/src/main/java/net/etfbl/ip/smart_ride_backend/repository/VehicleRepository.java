package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {
}
