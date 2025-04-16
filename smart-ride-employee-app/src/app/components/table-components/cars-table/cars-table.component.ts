import { Component, inject, ViewChild } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { CarService } from '../../../services/car.service';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';
import { BaseTableComponent } from '../base-table/base-table.component';
import { Car } from '../../../model/car.model';

@Component({
  selector: 'app-cars-table',
  imports: [
    MatTableModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    RouterModule,
    BaseTableComponent,
  ],
  templateUrl: './cars-table.component.html',
  styleUrl: './cars-table.component.css',
})
export class CarsTableComponent {
  carService = inject(CarService);
  headerMap = {
    id: 'Id',
    manufacturer: 'Manufacturer',
    model: 'Model',
    purchasePrice: 'Price',
    purchaseDateTime: 'Purchase date',
    entityName: 'car',
  };

  retrieveDataFunction = () => this.carService.getAll();

  @ViewChild(BaseTableComponent) private baseTableRef?: BaseTableComponent<Car>;

  public loadData(): void {
    this.baseTableRef?.loadData();
  }
}
