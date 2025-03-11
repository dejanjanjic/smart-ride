import { CommonModule, DatePipe } from '@angular/common';
import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { DeleteDialogComponent } from '../delete-dialog/delete-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';

@Component({
  selector: 'app-base-table',
  imports: [
    MatTableModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    FormsModule,
    MatInputModule,
    RouterModule,
    DatePipe,
  ],
  templateUrl: './base-table.component.html',
  styleUrl: './base-table.component.css',
})
export class BaseTableComponent<T extends { id: string | number }>
  implements AfterViewInit
{
  @Input() displayedColumns!: string[];
  @Input() addRoute!: string;
  @Input() service: any;
  @Input() headerMap: { [key: string]: string } = {};
  @Input() dateColumns: string[] = [];
  @Input() dateFormat: string = 'dd.MM.yyyy.';

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  dataSource = new MatTableDataSource<T>();
  resultsLength = 0;

  searchTerm = '';

  constructor(private dialog: MatDialog) {}

  ngAfterViewInit(): void {
    this.dataSource.paginator = this.paginator;
    this.loadData();
  }

  onSearch(): void {
    if (this.searchTerm.trim()) {
      this.filter(this.searchTerm.trim());
    } else {
      this.loadData();
    }
    this.dataSource.paginator?.firstPage();
  }
  filter(keyword: string) {
    this.service.filter(keyword).subscribe({
      next: (result: T[]) => {
        this.dataSource.data = result;
        this.resultsLength = result.length;
      },
      error: (err: any) =>
        console.error('Greška prilikom učitavanja podataka:', err),
    });
  }

  refreshTable(): void {
    this.searchTerm = '';
    this.loadData();
    this.dataSource.paginator?.firstPage();
  }

  loadData(): void {
    this.service.getAll().subscribe({
      next: (result: T[]) => {
        this.dataSource.data = result;
        this.resultsLength = result.length;
      },
      error: (err: any) =>
        console.error('Greška prilikom učitavanja podataka:', err),
    });
  }

  deleteItem(id: string): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { name: `${this.headerMap['entityName']} ${id}` }, // entityName iz headerMap (npr. 'user')
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.service.deleteById(id).subscribe({
          next: () => this.loadData(),
          error: (err: any) => console.error('Greška prilikom brisanja:', err),
        });
      }
    });
  }

  getHeader(col: string): string {
    return this.headerMap[col] || col;
  }

  isDateColumn(col: string): boolean {
    return this.dateColumns.includes(col);
  }
}
