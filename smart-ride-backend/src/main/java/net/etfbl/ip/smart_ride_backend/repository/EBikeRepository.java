package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.EBike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EBikeRepository extends JpaRepository<EBike, String> {
}
