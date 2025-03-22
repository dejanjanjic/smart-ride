import {
  Component,
  ElementRef,
  inject,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ActivatedRoute, Router } from '@angular/router';
import { formatDate } from '@angular/common';
import { ManufacturerService } from '../../../services/manufacturer.service';

type VehicleType = 'car' | 'e-bike' | 'e-scooter';

@Component({
  selector: 'app-add-vehicle-base',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
    MatIconModule,
  ],
  templateUrl: './add-vehicle-base.component.html',
  styleUrl: './add-vehicle-base.component.css',
})
export class AddVehicleBaseComponent implements OnInit {
  @ViewChild('fileInput') fileInput!: ElementRef;
  @Input() vehicleType!: VehicleType;
  @Input() vehicleService: any;

  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);
  private manufacturerService = inject(ManufacturerService);

  public manufacturers: string[] = [];
  public conflict = false;
  public selectedImage: File | null = null;
  public vehicleForm!: FormGroup;

  ngOnInit(): void {
    this.initForm();
    this.loadManufacturers();
  }

  private initForm(): void {
    const baseFields = {
      id: [null, [Validators.required]],
      manufacturer: [null, [Validators.required]],
      model: [null, [Validators.required]],
      purchasePrice: [null, [Validators.required, Validators.min(0.0)]],
    };

    const typeSpecificFields = {
      car: {
        purchaseDateTime: [null, [Validators.required]],
        description: [null, [Validators.required]],
      },
      'e-bike': {
        maxRange: [null, [Validators.required, Validators.min(1)]],
      },
      'e-scooter': {
        maxSpeed: [null, [Validators.required, Validators.min(1)]],
      },
    };

    this.vehicleForm = this.formBuilder.group({
      ...baseFields,
      ...typeSpecificFields[this.vehicleType],
    });
  }

  private loadManufacturers(): void {
    this.manufacturerService.getAllNames().subscribe({
      next: (result: any) => (this.manufacturers = result),
      error: (err: any) => {
        console.error('Failed to load manufacturers:', err);
      },
    });
  }

  openFileDialog(): void {
    this.fileInput.nativeElement.click();
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files?.[0]) {
      this.selectedImage = input.files[0];
    }
  }

  submitForm(): void {
    if (this.vehicleForm.invalid || !this.selectedImage) {
      return;
    }

    const formValue = this.prepareFormData();

    // Prvo šaljemo podatke o vozilu
    this.vehicleService.add(formValue).subscribe({
      next: (vehicle: any) => {
        // Nakon uspješnog dodavanja vozila, šaljemo sliku
        this.uploadImage(vehicle.id);
      },
      error: (err: any) => this.handleError(err),
    });
  }

  private prepareFormData(): any {
    const formValue = { ...this.vehicleForm.value };

    if (this.vehicleType === 'car') {
      formValue.purchaseDateTime = this.formatDate(formValue.purchaseDateTime);
    }
    return formValue;
  }

  private formatDate(date: string): string {
    return `${formatDate(date, 'yyyy-MM-dd', 'en-US')}T00:00:00`;
  }

  private uploadImage(vehicleId: string): void {
    if (!this.selectedImage) {
      console.error('No image selected');
      return;
    }

    // Slanje slike na server
    this.vehicleService.uploadImage(vehicleId, this.selectedImage).subscribe({
      next: () => {
        console.log('Image uploaded successfully');
        this.navigateBack();
      },
      error: (err: any) => {
        console.error('Image upload failed:', err);
      },
    });
  }

  private navigateBack(): void {
    this.router.navigate(['../'], { relativeTo: this.route });
  }

  private handleError(err: any): void {
    this.conflict = true;
    console.error('Error:', err.message);
  }
}
