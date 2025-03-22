import { Component } from '@angular/core';
import { ClientsTableComponent } from '../../table-components/clients-table/clients-table.component';

@Component({
  selector: 'app-clients-management',
  imports: [ClientsTableComponent],
  templateUrl: './clients-management.component.html',
  styleUrl: './clients-management.component.css',
})
export class ClientsManagementComponent {}
