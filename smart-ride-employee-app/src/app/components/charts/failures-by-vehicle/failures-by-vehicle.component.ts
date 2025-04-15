import { Component, inject, OnInit, ChangeDetectorRef } from '@angular/core';
import { NgxChartsModule, LegendPosition } from '@swimlane/ngx-charts';
import { FailureService } from '../../../services/failure.service';

@Component({
  selector: 'app-failures-by-vehicle',
  standalone: true,
  imports: [NgxChartsModule],
  templateUrl: './failures-by-vehicle.component.html',
  styleUrl: './failures-by-vehicle.component.css',
})
export class FailuresByVehicleComponent implements OnInit {
  private failureService: FailureService = inject(FailureService);
  private cdr: ChangeDetectorRef = inject(ChangeDetectorRef);

  view: [number, number] = [450, 300];
  colorScheme: string = 'vivid';
  legendPosition = LegendPosition.Below;

  public results: any[] = [];

  ngOnInit(): void {
    this.failureService.getFailuresByVehicle().subscribe({
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
