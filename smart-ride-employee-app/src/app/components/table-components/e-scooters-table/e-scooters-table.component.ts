import { Component, inject, ViewChild } from '@angular/core';
import { EScooterService } from '../../../services/e-scooter.service';
import { BaseTableComponent } from '../base-table/base-table.component';
import { EScooter } from '../../../model/escooter.model';

@Component({
  selector: 'app-e-scooters-table',
  imports: [BaseTableComponent],
  templateUrl: './e-scooters-table.component.html',
  styleUrl: './e-scooters-table.component.css',
})
export class EScootersTableComponent {
  eScooterService: EScooterService = inject(EScooterService);
  headerMap = {
    id: 'Id',
    manufacturer: 'Manufacturer',
    model: 'Model',
    purchasePrice: 'Price',
    maxSpeed: 'Speed',
    entityName: 'e-scooter',
  };

  retrieveDataFunction = () => this.eScooterService.getAll();

  @ViewChild(BaseTableComponent)
  private baseTableRef?: BaseTableComponent<EScooter>;

  public loadData(): void {
    this.baseTableRef?.loadData();
  }
}
