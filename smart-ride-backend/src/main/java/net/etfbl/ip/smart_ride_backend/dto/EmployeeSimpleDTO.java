package net.etfbl.ip.smart_ride_backend.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import net.etfbl.ip.smart_ride_backend.model.Employee;

@EqualsAndHashCode(callSuper = true)
@Data
public class EmployeeSimpleDTO extends UserSimpleDTO{
    public EmployeeSimpleDTO(Employee employee){
        this.id = employee.getId();
        this.firstName = employee.getFirstName();
        this.role = employee.getRole();
        this.lastName = employee.getLastName();
        this.username = employee.getUsername();
    }
}
