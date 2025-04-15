import { CommonModule, DatePipe } from '@angular/common';
import { AfterViewInit, Component, Input, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { RouterModule } from '@angular/router';
import { DeleteDialogComponent } from '../../delete-dialog/delete-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-base-table',
  standalone: true,
  imports: [
    MatTableModule,
    CommonModule,
    MatIconModule,
    MatButtonModule,
    MatPaginatorModule,
    MatSlideToggleModule,
    FormsModule,
    MatInputModule,
    RouterModule,
    DatePipe,
    MatTooltipModule,
  ],
  templateUrl: './base-table.component.html',
  styleUrl: './base-table.component.css',
})
export class BaseTableComponent<T extends { id: string | number }>
  implements AfterViewInit
{
  @Input() displayedColumns!: string[];
  @Input() addRoute!: string;
  @Input() updateRoute!: string;
  @Input() service: any;
  @Input() headerMap: { [key: string]: string } = {};
  @Input() dateColumns: string[] = [];
  @Input() toggleColumns: string[] = [];
  @Input() actionButtons: string[] = [];
  @Input() dateFormat: string = 'dd.MM.yyyy.';
  @Input() retrieveDataFunction: any;

  private _paginator!: MatPaginator;
  @ViewChild(MatPaginator) set paginator(paginator: MatPaginator) {
    this._paginator = paginator;
    if (this.dataSource) {
      this.dataSource.paginator = this._paginator;
    }
  }

  dataSource = new MatTableDataSource<T>();
  resultsLength = 0;

  searchTerm = '';
  isLoading = false;

  constructor(private dialog: MatDialog) {}

  ngAfterViewInit(): void {
    this.loadData();
  }

  onSearch(): void {
    if (this.searchTerm.trim()) {
      this.filter(this.searchTerm.trim());
    } else {
      this.loadData();
    }
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  filter(keyword: string) {
    this.isLoading = true;
    this.service.filter(keyword).subscribe({
      next: (result: T[]) => {
        this.dataSource.data = result;
        this.resultsLength = result.length;
        this.isLoading = false;
      },
      error: (err: any) => {
        console.error('Error loading data:', err);
        this.isLoading = false;
      },
    });
  }

  refreshTable(): void {
    this.searchTerm = '';
    this.loadData();
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  loadData(): void {
    this.isLoading = true;
    this.retrieveDataFunction().subscribe({
      next: (result: T[]) => {
        this.dataSource.data = result;
        this.resultsLength = result.length;
        this.isLoading = false;
        if (this._paginator) {
          this.dataSource.paginator = this._paginator;
        }
      },
      error: (err: any) => {
        console.error('Error loading data:', err);
        this.isLoading = false;
      },
    });
  }

  deleteItem(id: string): void {
    const dialogRef = this.dialog.open(DeleteDialogComponent, {
      data: { name: `${this.headerMap['entityName']} ${id}` },
      width: '400px',
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result) {
        this.service.deleteById(id).subscribe({
          next: () => this.loadData(),
          error: (err: any) => console.error('Error deleting item:', err),
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

  onToggleChange(item: T, column: string, value: boolean): void {
    const updatedItem = { id: item.id, [column]: value };
    this.service.update(updatedItem).subscribe({
      next: () => {
        const index = this.dataSource.data.findIndex((d) => d.id === item.id);
        if (index > -1) {
          const newData = [...this.dataSource.data];
          newData[index] = { ...newData[index], [column]: value };
          this.dataSource.data = newData;
        } else {
          this.loadData();
        }
      },
      error: (err: any) => {
        console.error('Error updating item:', err);
      },
    });
  }
}
