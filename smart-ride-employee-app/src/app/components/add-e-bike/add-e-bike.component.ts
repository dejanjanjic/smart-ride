import { Component, inject } from '@angular/core';
import { AddVehicleBaseComponent } from '../add-vehicle-base/add-vehicle-base.component';
import { EBikeService } from '../../services/e-bike.service';

@Component({
  selector: 'app-add-e-bike',
  imports: [AddVehicleBaseComponent],
  templateUrl: './add-e-bike.component.html',
  styleUrl: './add-e-bike.component.css',
})
export class AddEBikeComponent {
  public eBikeService: EBikeService = inject(EBikeService);
}
