package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.RentalPriceConfig;
import net.etfbl.ip.smart_ride_backend.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalPriceConfigRepository extends JpaRepository<RentalPriceConfig, VehicleType> {
}
