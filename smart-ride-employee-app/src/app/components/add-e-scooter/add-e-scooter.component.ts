import { Component, inject } from '@angular/core';
import { AddVehicleBaseComponent } from '../add-vehicle-base/add-vehicle-base.component';
import { EScooterService } from '../../services/e-scooter.service';

@Component({
  selector: 'app-add-e-scooter',
  imports: [AddVehicleBaseComponent],
  templateUrl: './add-e-scooter.component.html',
  styleUrl: './add-e-scooter.component.css',
})
export class AddEScooterComponent {
  public eScooterService: EScooterService = inject(EScooterService);
}
