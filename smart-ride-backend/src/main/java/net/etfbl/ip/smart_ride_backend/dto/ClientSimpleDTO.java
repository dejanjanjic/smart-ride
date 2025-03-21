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
    private String idNumber; //id card number or passport number
    private String email;
    private String phoneNumber;
    private String driverLicenseNumber;
    private Boolean domesticate;
    private Boolean blocked;

    public ClientSimpleDTO(Client client){
        this.id = client.getId();
        this.username = client.getUsername();
        this.firstName = client.getFirstName();
        this.lastName = client.getLastName();
        this.role = client.getRole();
        this.idNumber = client.getIdNumber();
        this.email = client.getEmail();
        this.phoneNumber = client.getPhoneNumber();
        this.driverLicenseNumber = client.getDriverLicenseNumber();
        this.domesticate = client.getDomesticate();
        this.blocked = client.getBlocked();
    }
}
