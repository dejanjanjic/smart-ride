import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Vehicle } from '../../model/vehicle.model';

type VehicleType = 'car' | 'e-bike' | 'e-scooter';

@Component({
  selector: 'app-view-details-vehicle-base',
  imports: [CommonModule],
  templateUrl: './view-details-vehicle-base.component.html',
  styleUrl: './view-details-vehicle-base.component.css',
})
export class ViewDetailsVehicleBaseComponent {
  private activatedRoute = inject(ActivatedRoute);

  @Input() vehicleService: any;
  @Input() vehicleType!: VehicleType;

  public vehicle: any = null;

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    const id = this.activatedRoute.snapshot.params['id'];
    this.vehicleService.getById(id).subscribe({
      next: (vehicle: any) => {
        this.vehicle = vehicle;
        this.vehicleService.getImageById(id).subscribe({
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
