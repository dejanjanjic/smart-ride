package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.AddEmployeeDTO;
import net.etfbl.ip.smart_ride_backend.dto.EmployeeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Employee;
import net.etfbl.ip.smart_ride_backend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    public List<EmployeeSimpleDTO> findAll() {
        return employeeRepository
                .findAll()
                .stream()
                .map(EmployeeSimpleDTO::new)
                .toList();
    }

    public Employee addEmployee(AddEmployeeDTO addEmployeeDTO) {
        if(employeeRepository.existsByUsername(addEmployeeDTO.getUsername())){
            return null;
        }
        return employeeRepository.save(
                new Employee(
                        addEmployeeDTO.getUsername(),
                        addEmployeeDTO.getPassword(),
                        addEmployeeDTO.getFirstName(),
                        addEmployeeDTO.getLastName(),
                        addEmployeeDTO.getRole()
                )
        );

    }


}
