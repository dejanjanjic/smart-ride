package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.model.EScooter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EScooterRepository extends JpaRepository<EScooter, String> {
    @Query("SELECT s FROM EScooter s WHERE LOWER(s.manufacturer.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EScooter> searchByKeyword(@Param("keyword") String keyword);
}
