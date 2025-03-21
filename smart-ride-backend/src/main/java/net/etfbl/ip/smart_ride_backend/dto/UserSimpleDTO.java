package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Role;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleDTO {
    protected Long id;
    protected String username;
    protected String firstName;
    protected String lastName;
    protected Role role;
}
