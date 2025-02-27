import { Component, inject, OnInit } from '@angular/core';
import { MatTableModule } from '@angular/material/table';
import { CarService } from '../../services/car.service';
import { Car } from '../../model/car.model';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-cars-table',
  imports: [MatTableModule, CommonModule, MatIconModule, MatButtonModule],
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
    'actions',
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
  viewDetails(id: string) {
    // Logika za prikaz detalja
    console.log('Prikaz detalja za vozilo sa ID:', id);
  }

  deleteCar(id: string) {
    // Logika za brisanje
    console.log('Brisanje vozila sa ID:', id);
  }
}
