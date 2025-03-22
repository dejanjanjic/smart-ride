import { Component, inject } from '@angular/core';
import { EScooterService } from '../../../services/e-scooter.service';
import { ViewDetailsVehicleBaseComponent } from '../view-details-vehicle-base/view-details-vehicle-base.component';

@Component({
  selector: 'app-view-details-e-scooter',
  imports: [ViewDetailsVehicleBaseComponent],
  templateUrl: './view-details-e-scooter.component.html',
  styleUrl: './view-details-e-scooter.component.css',
})
export class ViewDetailsEScooterComponent {
  public eScooterService: EScooterService = inject(EScooterService);
}
