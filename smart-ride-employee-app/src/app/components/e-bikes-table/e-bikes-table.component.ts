import { AfterViewInit, Component, inject, ViewChild } from '@angular/core';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { EBikeService } from '../../services/e-bike.service';
import { EBike } from '../../model/ebike.model';
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog } from '@angular/material/dialog';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-e-bikes-table',
  imports: [
    MatTableModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    RouterModule,
  ],
  templateUrl: './e-bikes-table.component.html',
  styleUrl: './e-bikes-table.component.css',
})
export class EBikesTableComponent {
  private dialog: MatDialog = inject(MatDialog);

  private eBikeService: EBikeService = inject(EBikeService);
  public eBikes: Array<EBike> = [];
  public displayedColumns: string[] = [
    'id',
    'manufacturer',
    'model',
    'maxRange',
    'purchasePrice',
    'actions',
  ];
  public resultsLength: number = 0;
  dataSource = new MatTableDataSource<EBike>(this.eBikes);

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.loadData();
  }
  public loadData() {
    this.eBikeService.getAll().subscribe({
      next: (result) => {
        this.eBikes = result as Array<EBike>;
        this.resultsLength = this.eBikes.length;
        this.dataSource.data = this.eBikes;
      },
      error: (err) => {
        console.error('Greška prilikom učitavanja podataka:', err);
      },
    });
  }

  deleteEBike(id: string) {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { name: `e-bike ${id}` },
    });

    dialogRef.afterClosed().subscribe((result) => {
      console.log(result);
      console.log('The dialog was closed');
      if (result) {
        this.eBikeService.deleteById(id).subscribe({
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
