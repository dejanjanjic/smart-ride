package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.LoginDTO;
import net.etfbl.ip.smart_ride_backend.model.Employee;
import net.etfbl.ip.smart_ride_backend.model.Role;
import net.etfbl.ip.smart_ride_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public AuthService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public Employee login(LoginDTO loginDTO){
        Employee employee = employeeRepository
                .findByUsername(loginDTO.getUsername())
                .orElse(null);
        if(employee == null || employee.getRole().equals(Role.CLIENT)){
            return null;
        }
        if(!employee.getPassword().equals(loginDTO.getPassword())){
            return null;
        }
        return employee;

    }
}
