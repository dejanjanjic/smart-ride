package net.etfbl.ip.smart_ride_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.etfbl.ip.smart_ride_backend.model.Client;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientSimpleDTO extends UserSimpleDTO{
    private String email;
    private String phoneNumber;
    private Boolean blocked;

    public ClientSimpleDTO(Client client){
        this.id = client.getId();
        this.username = client.getUsername();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.role = client.getRole();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.blocked = client.getBlocked();
    }
}
