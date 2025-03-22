import { Component, inject, Input } from '@angular/core';
import { FailureService } from '../../../services/failure.service';
import { BaseTableComponent } from '../base-table/base-table.component';

@Component({
  selector: 'app-failures-table',
  imports: [BaseTableComponent],
  templateUrl: './failures-table.component.html',
  styleUrl: './failures-table.component.css',
})
export class FailuresTableComponent {
  @Input() vehicleId: string = '';
  failureService: FailureService = inject(FailureService);
  headerMap = {
    id: 'Id',
    dateTime: 'Date',
    description: 'Description',
    entityName: 'failure',
  };

  retrieveDataFunction = () =>
    this.failureService.getAllByVehicleId(this.vehicleId);
}
