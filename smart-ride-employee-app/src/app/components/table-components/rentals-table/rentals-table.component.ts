import { Component, inject, Input } from '@angular/core';
import { BaseTableComponent } from '../base-table/base-table.component';
import { RentalService } from '../../../services/rental.service';

@Component({
  selector: 'app-rentals-table',
  imports: [BaseTableComponent],
  templateUrl: './rentals-table.component.html',
  styleUrl: './rentals-table.component.css',
})
export class RentalsTableComponent {
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
