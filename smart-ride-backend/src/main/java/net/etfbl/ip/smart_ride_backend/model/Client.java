package net.etfbl.ip.smart_ride_backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
public class Client extends User{
    private String idNumber; //id card number or passport number
    private String email;
    private String phoneNumber;
    private String image;
    private String driverLicenseNumber;
    private Boolean domesticate;

    public Client() {
    }

    public Client(Long id, String username, String password, String firstName, String lastName, String idNumber, String email, String phoneNumber, String driverLicenseNumber, Boolean domesticate) {
        super(id, username, password, firstName, lastName, Role.CLIENT);
        this.idNumber = idNumber;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.driverLicenseNumber = driverLicenseNumber;
        this.domesticate = domesticate;
    }
}
