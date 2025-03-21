package net.etfbl.ip.smart_ride_backend.repository;

import net.etfbl.ip.smart_ride_backend.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
