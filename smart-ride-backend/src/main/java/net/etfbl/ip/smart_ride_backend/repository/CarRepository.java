package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    @Query("SELECT c FROM Car c WHERE LOWER(c.manufacturer.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(c.model) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Car> searchByKeyword(@Param("keyword") String keyword);
}
