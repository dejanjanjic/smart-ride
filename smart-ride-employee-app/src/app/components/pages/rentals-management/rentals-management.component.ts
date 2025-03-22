import { Component } from '@angular/core';
import { RentalsTableComponent } from '../../table-components/rentals-table/rentals-table.component';

@Component({
  selector: 'app-rentals-management',
  imports: [RentalsTableComponent],
  templateUrl: './rentals-management.component.html',
  styleUrl: './rentals-management.component.css',
})
export class RentalsManagementComponent {}
