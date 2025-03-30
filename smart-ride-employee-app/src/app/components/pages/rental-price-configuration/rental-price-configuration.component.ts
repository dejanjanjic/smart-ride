import { Component, inject } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { RentalPriceConfigService } from '../../../services/rental-price-config.service';
import { RentalConfig } from '../../../model/rental-config.model';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-rental-price-configuration',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSnackBarModule,
  ],
  templateUrl: './rental-price-configuration.component.html',
  styleUrl: './rental-price-configuration.component.css',
})
export class RentalPriceConfigurationComponent {
  private rentalPriceConfigService = inject(RentalPriceConfigService);
  private formBuilder = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);

  public initRentalPrice!: RentalConfig;
  public conflict = false;

  public rentalPriceConfigForm: FormGroup = this.formBuilder.group({
    carPrice: ['', [Validators.required, Validators.min(0)]],
    scooterPrice: ['', [Validators.required, Validators.min(0)]],
    bikePrice: ['', [Validators.required, Validators.min(0)]],
  });

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.rentalPriceConfigService.getAll().subscribe({
      next: (rentalConfig: RentalConfig) => {
        this.initRentalPrice = rentalConfig;
        this.rentalPriceConfigForm.patchValue(this.initRentalPrice);
      },
      error: (err) => console.error('Error loading data:', err),
    });
  }
  submitForm(): void {
    if (this.rentalPriceConfigForm.invalid) {
      return;
    }

    this.rentalPriceConfigService
      .update(this.rentalPriceConfigForm.value)
      .subscribe({
        next: (rentalConfig: any) => {
          this.conflict = false;
          this.successfulMessage();
        },
        error: (err: any) => this.handleError(err),
      });
  }

  private successfulMessage(): void {
    this.snackBar.open('Prices updated successfully!', 'Close', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
    });
  }

  private handleError(err: any): void {
    this.conflict = true;
    console.error('Error:', err.message);
  }
}
