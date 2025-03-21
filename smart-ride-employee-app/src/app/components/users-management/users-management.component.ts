import { Component, inject } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { ClientService } from '../../services/client.service';
import { EmployeeService } from '../../services/employee.service';
import { ClientsTableComponent } from '../clients-table/clients-table.component';
import { EmployeesTableComponent } from '../employees-table/employees-table.component';

@Component({
  selector: 'app-users-management',
  imports: [MatTabsModule, ClientsTableComponent, EmployeesTableComponent],
  templateUrl: './users-management.component.html',
  styleUrl: './users-management.component.css',
})
export class UsersManagementComponent {}
