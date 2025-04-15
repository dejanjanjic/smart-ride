import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgxChartsModule, LegendPosition } from '@swimlane/ngx-charts';
import { RentalService } from '../../../services/rental.service';

@Component({
  selector: 'app-revenue-by-vehicle-type',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './revenue-by-vehicle-type.component.html',
  styleUrl: './revenue-by-vehicle-type.component.css',
})
export class RevenueByVehicleTypeComponent implements OnInit {
  private rentalService: RentalService = inject(RentalService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  view: [number, number] = [450, 300];
  colorScheme: string = 'vivid';
  legendPosition = LegendPosition.Below;

  public results: any[] = [];

  ngOnInit(): void {
    this.rentalService.getRevenueByVehicleType().subscribe({
      next: (result: any) => {
        this.results = result;
        this.cdr.markForCheck();
      },
      error: (err: any) => {
        console.error('Error: ', err);
      },
    });
  }
}
