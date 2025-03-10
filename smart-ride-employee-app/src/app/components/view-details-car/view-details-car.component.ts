import { Component, inject } from '@angular/core';
import { CarService } from '../../services/car.service';
import { CommonModule } from '@angular/common';
import { ViewDetailsVehicleBaseComponent } from '../view-details-vehicle-base/view-details-vehicle-base.component';

@Component({
  selector: 'app-view-details-car',
  imports: [CommonModule, ViewDetailsVehicleBaseComponent],
  templateUrl: './view-details-car.component.html',
  styleUrl: './view-details-car.component.css',
})
export class ViewDetailsCarComponent {
  public carService: CarService = inject(CarService);
}
