package net.etfbl.ip.smart_ride_backend.controller;

import net.etfbl.ip.smart_ride_backend.dto.AddEmployeeDTO;
import net.etfbl.ip.smart_ride_backend.dto.EmployeeSimpleDTO;
import net.etfbl.ip.smart_ride_backend.model.Employee;
import net.etfbl.ip.smart_ride_backend.model.Role;
import net.etfbl.ip.smart_ride_backend.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeSimpleDTO>> getAll(){
        return ResponseEntity.ok(this.employeeService.findAll());
    }
    @PostMapping
    public ResponseEntity<Employee> addEmployee(@RequestBody AddEmployeeDTO addEmployeeDTO){
        if(addEmployeeDTO.getRole().equals(Role.CLIENT)){
            return ResponseEntity.status(403).build();
        }
        Employee employee = employeeService.addEmployee(addEmployeeDTO);
        if(employee == null){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.ok(employee);
    }
}
