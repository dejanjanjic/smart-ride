import { Component, inject } from '@angular/core';
import { ClientService } from '../../../services/client.service';
import { BaseTableComponent } from '../base-table/base-table.component';

@Component({
  selector: 'app-clients-table',
  imports: [BaseTableComponent],
  templateUrl: './clients-table.component.html',
  styleUrl: './clients-table.component.css',
})
export class ClientsTableComponent {
  clientService = inject(ClientService);
  headerMap = {
    id: 'Id',
    username: 'Username',
    firstName: 'First name',
    lastName: 'Last name',
    email: 'Email',
    phoneNumber: 'Phone number',
    blocked: 'Blocked',
  };

  retrieveDataFunction = () => this.clientService.getAll();
}
