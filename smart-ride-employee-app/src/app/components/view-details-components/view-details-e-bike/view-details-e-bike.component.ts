import { Component, inject } from '@angular/core';
import { EBikeService } from '../../../services/e-bike.service';
import { ViewDetailsVehicleBaseComponent } from '../view-details-vehicle-base/view-details-vehicle-base.component';

@Component({
  selector: 'app-view-details-e-bike',
  imports: [ViewDetailsVehicleBaseComponent],
  templateUrl: './view-details-e-bike.component.html',
  styleUrl: './view-details-e-bike.component.css',
})
export class ViewDetailsEBikeComponent {
  public eBikeService: EBikeService = inject(EBikeService);
}
