import { CommonModule } from '@angular/common';
import { Component, inject, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { EScooter } from '../../model/escooter.model';
import { MatDialog } from '@angular/material/dialog';
import { EScooterService } from '../../services/e-scooter.service';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';

@Component({
  selector: 'app-e-scooters-table',
  imports: [
    MatTableModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    RouterModule,
  ],
  templateUrl: './e-scooters-table.component.html',
  styleUrl: './e-scooters-table.component.css',
})
export class EScootersTableComponent {
  private dialog: MatDialog = inject(MatDialog);

  private eScooterService: EScooterService = inject(EScooterService);
  public eScooter: Array<EScooter> = [];
  public displayedColumns: string[] = [
    'id',
    'manufacturer',
    'model',
    'maxSpeed',
    'purchasePrice',
    'actions',
  ];
  public resultsLength: number = 0;
  dataSource = new MatTableDataSource<EScooter>(this.eScooter);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.loadData();
  }
  public loadData() {
    this.eScooterService.getAll().subscribe({
      next: (result) => {
        this.eScooter = result as Array<EScooter>;
        this.resultsLength = this.eScooter.length;
        this.dataSource.data = this.eScooter;
      },
      error: (err) => {
        console.error('Greška prilikom učitavanja podataka:', err);
      },
    });
  }

  deleteEScooter(id: string) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { name: `e-scooter ${id}` },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(result);
      console.log('The dialog was closed');
      if (result) {
        this.eScooterService.deleteById(id).subscribe({
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
