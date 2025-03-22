import { CommonModule } from '@angular/common';
import { Component, inject, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RentalsTableComponent } from '../../table-components/rentals-table/rentals-table.component';
import { FailuresTableComponent } from '../../table-components/failures-table/failures-table.component';

type VehicleType = 'car' | 'e-bike' | 'e-scooter';

@Component({
  selector: 'app-view-details-vehicle-base',
  imports: [CommonModule, RentalsTableComponent, FailuresTableComponent],
  templateUrl: './view-details-vehicle-base.component.html',
  styleUrl: './view-details-vehicle-base.component.css',
})
export class ViewDetailsVehicleBaseComponent implements OnInit {
  private activatedRoute = inject(ActivatedRoute);

  @Input() vehicleService: any;
  @Input() vehicleType!: VehicleType;

  public vehicle: any = null;
  public id: string = '';

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.vehicleService.getById(this.id).subscribe({
      next: (vehicle: any) => {
        this.vehicle = vehicle;
        this.vehicleService.getImageById(this.id).subscribe({
          next: (image: any) => {
            const imageUrl = URL.createObjectURL(image);
            this.vehicle!.picture = imageUrl;
          },
          error: (err: any) => {
            console.error('Image not found:', err);
          },
        });
      },
      error: (err: any) => {
        console.error('Car not found: ', err);
      },
    });
  }
}
