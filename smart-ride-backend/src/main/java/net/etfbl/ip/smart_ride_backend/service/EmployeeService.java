package net.etfbl.ip.smart_ride_backend.service;

import net.etfbl.ip.smart_ride_backend.dto.AddEmployeeDTO;
import net.etfbl.ip.smart_ride_backend.dto.EmployeeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Employee;
import net.etfbl.ip.smart_ride_backend.model.Manufacturer;
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
    public EmployeeSimpleDTO getById(Long id) {
        Employee temp = this.employeeRepository.findById(id).orElse(null);
        return temp == null ? null : new EmployeeSimpleDTO(temp);
    }
    public List<EmployeeSimpleDTO> findByKeyword(String keyword) {
        return employeeRepository
                .findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(keyword, keyword, keyword)
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


    public boolean deleteById(Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public EmployeeSimpleDTO update(EmployeeSimpleDTO employee) {
        Employee temp = employeeRepository.findById(employee.getId()).orElse(null);
        if(temp == null){
            return null;
        }
        if(employee.getFirstName() != null){
            temp.setFirstName(employee.getFirstName());
        }
        if(employee.getLastName() != null){
            temp.setLastName(employee.getLastName());
        }
        if(employee.getUsername() != null){
            temp.setUsername(employee.getUsername());
        }
        return new EmployeeSimpleDTO(employeeRepository.save(temp));
    }


}
