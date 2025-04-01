package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    List<Rental> findAllByVehicle_Id(String vehicleId);
    List<Rental> findAllByDateTimeBetween(LocalDateTime dateTimeAfter, LocalDateTime dateTimeBefore);
    @Query("SELECT TYPE(r.vehicle), SUM(r.price) FROM Rental r GROUP BY TYPE(r.vehicle)")
    List<Object[]> getTotalRevenueByVehicleType();
}
