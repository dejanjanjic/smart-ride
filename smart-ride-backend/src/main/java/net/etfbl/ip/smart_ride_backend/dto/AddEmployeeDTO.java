package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddEmployeeDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
}
