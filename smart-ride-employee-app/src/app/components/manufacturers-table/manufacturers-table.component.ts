import { Component, inject } from '@angular/core';
import { BaseTableComponent } from '../base-table/base-table.component';
import { ManufacturerService } from '../../services/manufacturer.service';

@Component({
  selector: 'app-manufacturers-table',
  imports: [BaseTableComponent],
  templateUrl: './manufacturers-table.component.html',
  styleUrl: './manufacturers-table.component.css',
})
export class ManufacturersTableComponent {
  manufacturerService: ManufacturerService = inject(ManufacturerService);
  headerMap = {
    id: 'Id',
    name: 'Name',
    address: 'Address',
    phone: 'Phone',
    fax: 'Fax',
    mail: 'Mail',
    entityName: 'manufacturer',
  };
}
