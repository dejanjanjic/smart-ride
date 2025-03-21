import { Component, inject } from '@angular/core';
import { EmployeeService } from '../../services/employee.service';
import { BaseTableComponent } from '../base-table/base-table.component';

@Component({
  selector: 'app-employees-table',
  imports: [BaseTableComponent],
  templateUrl: './employees-table.component.html',
  styleUrl: './employees-table.component.css',
})
export class EmployeesTableComponent {
  employeeService = inject(EmployeeService);
  headerMap = {
    id: 'Id',
    username: 'Username',
    firstName: 'First name',
    lastName: 'Last name',
    role: 'Account type',
    entityName: 'employee',
  };

  retrieveDataFunction = () => this.employeeService.getAll();
}
