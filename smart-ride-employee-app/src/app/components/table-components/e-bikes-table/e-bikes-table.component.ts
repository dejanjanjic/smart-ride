import { Component, inject, ViewChild } from '@angular/core';
import { EBikeService } from '../../../services/e-bike.service';
import { BaseTableComponent } from '../base-table/base-table.component';
import { EBike } from '../../../model/ebike.model';
@Component({
  selector: 'app-e-bikes-table',
  imports: [BaseTableComponent],
  templateUrl: './e-bikes-table.component.html',
  styleUrl: './e-bikes-table.component.css',
})
export class EBikesTableComponent {
  public eBikeService: EBikeService = inject(EBikeService);
  public headerMap = {
    id: 'Id',
    manufacturer: 'Manufacturer',
    model: 'Model',
    purchasePrice: 'Price',
    maxRange: 'Range',
    entityName: 'e-bike',
  };

  retrieveDataFunction = () => this.eBikeService.getAll();

  @ViewChild(BaseTableComponent)
  private baseTableRef?: BaseTableComponent<EBike>;

  public loadData(): void {
    this.baseTableRef?.loadData();
  }
}
