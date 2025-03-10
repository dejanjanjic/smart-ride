import { Component, inject } from '@angular/core';
import { CarService } from '../../services/car.service';
import { AddVehicleBaseComponent } from '../add-vehicle-base/add-vehicle-base.component';

@Component({
  selector: 'app-add-car',
  imports: [AddVehicleBaseComponent],
  templateUrl: './add-car.component.html',
  styleUrl: './add-car.component.css',
})
export class AddCarComponent {
  public carService: CarService = inject(CarService);
}
