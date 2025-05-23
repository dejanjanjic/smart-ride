package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {
    boolean existsByName(String manufacturer);
    Manufacturer findByName(String name);
    List<Manufacturer> findByNameContainingIgnoreCase(String name);
}
