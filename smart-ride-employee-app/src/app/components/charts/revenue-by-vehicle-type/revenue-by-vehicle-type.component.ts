import { Component, inject } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { RentalService } from '../../../services/rental.service';

@Component({
  selector: 'app-revenue-by-vehicle-type',
  imports: [NgxChartsModule],
  templateUrl: './revenue-by-vehicle-type.component.html',
  styleUrl: './revenue-by-vehicle-type.component.css',
})
export class RevenueByVehicleTypeComponent {
  private rentalService: RentalService = inject(RentalService);
  view: [number, number] = [600, 250];
  public results = [];

  ngOnInit(): void {
    this.rentalService.getRevenueByVehicleType().subscribe({
      next: (result: any) => {
        this.results = result;
      },
      error: (err: any) => {
        console.error('Error: ', err);
      },
    });
  }
}
