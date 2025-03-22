import { Component, inject, Input } from '@angular/core';
import { BaseTableComponent } from '../base-table/base-table.component';
import { RentalService } from '../../../services/rental.service';

@Component({
  selector: 'app-rentals-by-vehicle',
  imports: [BaseTableComponent],
  templateUrl: './rentals-by-vehicle.component.html',
  styleUrl: './rentals-by-vehicle.component.css',
})
export class RentalsByVehicleComponent {
  @Input() vehicleId: string = '';
  rentalService: RentalService = inject(RentalService);
  headerMap = {
    id: 'Id',
    rentalDate: 'Date',
    durationInSeconds: 'Duration',
    clientName: 'Client name',
    entityName: 'rental',
  };

  retrieveDataFunction = () =>
    this.rentalService.getAllByVehicleId(this.vehicleId);
}
