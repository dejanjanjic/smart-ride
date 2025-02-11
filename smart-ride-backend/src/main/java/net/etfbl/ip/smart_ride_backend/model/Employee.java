package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Employee extends User{
    public Employee() {
    }

    public Employee(Long id, String username, String password, String firstName, String lastName, Role role) {
        super(id, username, password, firstName, lastName, role);
        if (role == Role.CLIENT) {
            throw new IllegalArgumentException("Role cannot be CLIENT for an Employee.");
        }
    }
}
