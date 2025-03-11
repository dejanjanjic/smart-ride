package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Car;
import net.etfbl.ip.smart_ride_backend.model.EBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EBikeRepository extends JpaRepository<EBike, String> {
    @Query("SELECT b FROM EBike b WHERE LOWER(b.manufacturer.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(b.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EBike> searchByKeyword(@Param("keyword") String keyword);
}
