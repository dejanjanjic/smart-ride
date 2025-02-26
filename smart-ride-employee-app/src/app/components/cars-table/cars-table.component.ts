import { Component, inject, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { CarService } from '../../services/car.service';
import { Car } from '../../model/car.model';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-cars-table',
  imports: [MatTableModule, CommonModule],
  templateUrl: './cars-table.component.html',
  styleUrl: './cars-table.component.css',
})
export class CarsTableComponent implements OnInit {
  private carService: CarService = inject(CarService);
  public cars: Array<Car> = [];
  public displayedColumns: string[] = [
    'id',
    'model',
    'manufacturer',
    'purchasePrice',
    'purchaseDate',
  ];
  ngOnInit(): void {
    this.loadData();
  }
  public loadData() {
    this.carService.getAll().subscribe({
      next: (result) => {
        this.cars = result as Array<Car>;
      },
      error: (err) => {
        console.error('Greška prilikom učitavanja podataka:', err);
      },
    });
  }
}
