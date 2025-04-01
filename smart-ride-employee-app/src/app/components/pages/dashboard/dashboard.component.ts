import { Component } from '@angular/core';
import { RevenueByDayComponent } from '../../charts/revenue-by-day/revenue-by-day.component';
import { FailuresByVehicleComponent } from '../../charts/failures-by-vehicle/failures-by-vehicle.component';
import { RevenueByVehicleTypeComponent } from '../../charts/revenue-by-vehicle-type/revenue-by-vehicle-type.component';

@Component({
  selector: 'app-stats',
  standalone: true,
  imports: [
    RevenueByDayComponent,
    FailuresByVehicleComponent,
    RevenueByVehicleTypeComponent,
  ],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css',
})
export class DashboardComponent {}
