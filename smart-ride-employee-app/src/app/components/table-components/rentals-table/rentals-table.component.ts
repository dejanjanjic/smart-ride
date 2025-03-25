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
  rentalService: RentalService = inject(RentalService);
  headerMap = {
    id: 'Id',
    dateTime: 'Date',
    durationInSeconds: 'Duration',
    price: 'Price',
    clientName: 'Client name',
    vehicleId: 'Vehicle',
    entityName: 'rental',
  };

  retrieveDataFunction = () => this.rentalService.getAll();
}
