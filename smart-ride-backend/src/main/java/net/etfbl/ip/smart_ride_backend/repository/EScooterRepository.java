package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.EScooter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EScooterRepository extends JpaRepository<EScooter, String> {
}
