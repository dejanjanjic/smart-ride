import { CommonModule } from '@angular/common';
import {
  Component,
  ElementRef,
  inject,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RentalsByVehicleComponent } from '../../table-components/rentals-by-vehicle/rentals-by-vehicle.component';
import { FailuresTableComponent } from '../../table-components/failures-table/failures-table.component';

type VehicleType = 'car' | 'e-bike' | 'e-scooter';

@Component({
  selector: 'app-view-details-vehicle-base',
  imports: [CommonModule, RentalsByVehicleComponent, FailuresTableComponent],
  templateUrl: './view-details-vehicle-base.component.html',
  styleUrl: './view-details-vehicle-base.component.css',
})
export class ViewDetailsVehicleBaseComponent implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;

  private activatedRoute = inject(ActivatedRoute);

  @Input() vehicleService: any;
  @Input() vehicleType!: VehicleType;

  public vehicle: any = null;
  public id: string = '';
  public selectedImage: File | null = null;

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

  openFileDialog(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedImage = input.files[0];

      if (!this.selectedImage.type.startsWith('image/')) {
        alert('Please select an image file');
        this.selectedImage = null;
        return;
      }

      const reader = new FileReader();
      reader.onload = () => {
        this.vehicle!.picture = reader.result as string;
      };
      reader.readAsDataURL(this.selectedImage);

      this.uploadImage();
    }
  }

  uploadImage(): void {
    if (!this.selectedImage) {
      console.error('No image selected');
      return;
    }

    this.vehicleService.uploadImage(this.id, this.selectedImage).subscribe({
      next: () => {
        console.log('Image uploaded successfully');
      },
      error: (err: any) => {
        console.error('Image upload failed:', err);
        alert('Failed to upload image. Please try again.');
      },
    });
  }
}
