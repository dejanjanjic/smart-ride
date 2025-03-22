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
import { ManufacturerService } from '../../../services/manufacturer.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-manufacturer',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
  ],
  templateUrl: './add-manufacturer.component.html',
  styleUrl: './add-manufacturer.component.css',
})
export class AddManufacturerComponent {
  private manufacturerService = inject(ManufacturerService);

  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);

  public conflict = false;
  public manufacturerForm: FormGroup = this.formBuilder.group({
    name: [null, [Validators.required]],
    country: [null, [Validators.required]],
    address: [null, [Validators.required]],
    phone: [null],
    fax: [null],
    mail: [null],
  });

  submitForm(): void {
    if (this.manufacturerForm.invalid) {
      return;
    }

    this.manufacturerService.add(this.manufacturerForm.value).subscribe({
      next: (vehicle: any) => {
        this.navigateBack();
      },
      error: (err: any) => this.handleError(err),
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
