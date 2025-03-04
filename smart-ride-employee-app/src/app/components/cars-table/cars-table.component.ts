import { AfterViewInit, Component, inject, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { CarService } from '../../services/car.service';
import { Car } from '../../model/car.model';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';

@Component({
  selector: 'app-cars-table',
  imports: [
    MatTableModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    RouterModule,
  ],
  templateUrl: './cars-table.component.html',
  styleUrl: './cars-table.component.css',
})
export class CarsTableComponent implements AfterViewInit {
  private dialog: MatDialog = inject(MatDialog);
  private router: Router = inject(Router);
  private route: ActivatedRoute = inject(ActivatedRoute);

  private carService: CarService = inject(CarService);
  public cars: Array<Car> = [];
  public displayedColumns: string[] = [
    'id',
    'manufacturer',
    'model',
    'purchasePrice',
    'purchaseDate',
    'actions',
  ];
  public resultsLength: number = 0;
  dataSource = new MatTableDataSource<Car>(this.cars);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.loadData();
  }
  public loadData() {
    this.carService.getAll().subscribe({
      next: (result) => {
        this.cars = result as Array<Car>;
        this.resultsLength = this.cars.length;
        this.dataSource.data = this.cars;
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
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { name: `car ${id}` },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(result);
      console.log('The dialog was closed');
      if (result) {
        this.carService.deleteById(id).subscribe({
          next: (result) => {
            console.log(result);
            this.loadData();
          },
          error: (err) => {
            console.error('Greška prilikom učitavanja podataka:', err);
          },
        });
      }
    });
  }
}
