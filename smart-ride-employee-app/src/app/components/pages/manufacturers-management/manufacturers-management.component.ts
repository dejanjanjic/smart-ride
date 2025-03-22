import { Component } from '@angular/core';
import { ManufacturersTableComponent } from '../../table-components/manufacturers-table/manufacturers-table.component';

@Component({
  selector: 'app-manufacturers-management',
  imports: [ManufacturersTableComponent],
  templateUrl: './manufacturers-management.component.html',
  styleUrl: './manufacturers-management.component.css',
})
export class ManufacturersManagementComponent {}
