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
import { FailureService } from '../../../services/failure.service';
import { formatDate } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-failure-by-vehicle',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
  ],
  templateUrl: './add-failure-by-vehicle.component.html',
  styleUrl: './add-failure-by-vehicle.component.css',
})
export class AddFailureByVehicleComponent {
  private failureService = inject(FailureService);
  private router = inject(Router);
  private activatedRoute = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);

  public id = this.activatedRoute.snapshot.params['id'];
  public conflict = false;
  public failureForm: FormGroup = this.formBuilder.group({
    dateTime: [null, [Validators.required]],
    description: [null, [Validators.required]],
  });

  submitForm(): void {
    if (this.failureForm.invalid) {
      return;
    }
    const formValue = { ...this.failureForm.value, vehicleId: this.id };
    formValue.dateTime = this.formatDate(formValue.dateTime);

    this.failureService.add(formValue).subscribe({
      next: (failure: any) => {
        this.navigateBack();
        this.conflict = false;
      },
      error: (err: any) => this.handleError(err),
    });
  }

  private navigateBack(): void {
    this.router.navigate(['../'], { relativeTo: this.activatedRoute });
  }

  private formatDate(date: string): string {
    return `${formatDate(date, 'yyyy-MM-dd', 'en-US')}T00:00:00`;
  }
  private handleError(err: any): void {
    this.conflict = true;
    console.error('Error:', err.message);
  }
}
