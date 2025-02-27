import { Component } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { CarsTableComponent } from '../cars-table/cars-table.component';
import { EBikesTableComponent } from '../e-bikes-table/e-bikes-table.component';
import { EScootersTableComponent } from '../e-scooters-table/e-scooters-table.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-vehicles-management',
  imports: [
    MatTabsModule,
    MatIconModule,
    CarsTableComponent,
    EBikesTableComponent,
    EScootersTableComponent,
  ],
  templateUrl: './vehicles-management.component.html',
  styleUrl: './vehicles-management.component.css',
})
export class VehiclesManagementComponent {}
