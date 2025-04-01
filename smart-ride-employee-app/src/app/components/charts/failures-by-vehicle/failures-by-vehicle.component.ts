import { Component, inject, OnInit } from '@angular/core';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { FailureService } from '../../../services/failure.service';

@Component({
  selector: 'app-failures-by-vehicle',
  imports: [NgxChartsModule],
  templateUrl: './failures-by-vehicle.component.html',
  styleUrl: './failures-by-vehicle.component.css',
})
export class FailuresByVehicleComponent implements OnInit {
  private failureService: FailureService = inject(FailureService);
  view: [number, number] = [600, 250];
  public results = [];

  ngOnInit(): void {
    this.failureService.getFailuresByVehicle().subscribe({
      next: (result: any) => {
        this.results = result;
      },
      error: (err: any) => {
        console.error('Error: ', err);
      },
    });
  }
}
