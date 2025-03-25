import { Component, inject, OnInit } from '@angular/core';
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
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import { FailureService } from '../../../services/failure.service';
import { VehicleService } from '../../../services/vehicle.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-failure',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
    MatSnackBarModule,
  ],
  templateUrl: './add-failure.component.html',
  styleUrl: './add-failure.component.css',
})
export class AddFailureComponent implements OnInit {
  private failureService = inject(FailureService);
  private vehicleService = inject(VehicleService);

  private formBuilder = inject(FormBuilder);
  private snackBar = inject(MatSnackBar);

  public conflict = false;
  public vehicleIds: string[] = [];
  public failureForm: FormGroup = this.formBuilder.group({
    vehicleId: [null, [Validators.required]],
    dateTime: [null, [Validators.required]],
    description: [null, [Validators.required]],
  });

  ngOnInit(): void {
    this.loadVehicleIds();
  }

  private loadVehicleIds(): void {
    this.vehicleService.getVehicleIds().subscribe({
      next: (result: any) => (this.vehicleIds = result),
      error: (err: any) => {
        console.error('Failed to load vehicle ids:', err);
      },
    });
  }

  submitForm(): void {
    if (this.failureForm.invalid) {
      return;
    }
    const formValue = { ...this.failureForm.value };
    formValue.dateTime = this.formatDate(formValue.dateTime);

    this.failureService.add(formValue).subscribe({
      next: (failure: any) => {
        this.successfulMessage();
        this.conflict = false;
      },
      error: (err: any) => this.handleError(err),
    });
  }

  private successfulMessage(): void {
    this.snackBar.open('Failure added successfully!', 'Close', {
      duration: 3000,
      horizontalPosition: 'center',
      verticalPosition: 'bottom',
    });
  }

  private formatDate(date: string): string {
    return `${formatDate(date, 'yyyy-MM-dd', 'en-US')}T00:00:00`;
  }

  private handleError(err: any): void {
    this.conflict = true;
    console.error('Error:', err.message);
  }
}
