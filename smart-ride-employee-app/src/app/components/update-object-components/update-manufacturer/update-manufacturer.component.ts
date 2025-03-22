import { Component, inject, Input, OnInit } from '@angular/core';
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
import { Manufacturer } from '../../../model/manufacturer.model';

@Component({
  selector: 'app-update-manufacturer',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
  ],
  templateUrl: './update-manufacturer.component.html',
  styleUrl: './update-manufacturer.component.css',
})
export class UpdateManufacturerComponent implements OnInit {
  private manufacturerService = inject(ManufacturerService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);

  public initManufacturer!: Manufacturer;
  public conflict = false;

  public manufacturerForm: FormGroup = this.formBuilder.group({
    id: { value: '', disabled: true },
    name: ['', [Validators.required]],
    country: ['', [Validators.required]],
    address: ['', [Validators.required]],
    phone: [''],
    fax: [''],
    mail: [''],
  });

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    const id = this.route.snapshot.params['id'];
    this.manufacturerService.getById(id).subscribe({
      next: (manufacturer: Manufacturer) => {
        this.initManufacturer = manufacturer;

        const idControl = this.manufacturerForm.get('id');
        if (idControl) {
          idControl.enable({ emitEvent: false });
          this.manufacturerForm.patchValue(this.initManufacturer);
          idControl.disable({ emitEvent: false });
        }
      },
      error: (err) => console.error('Error loading data:', err),
    });
  }
  submitForm(): void {
    if (this.manufacturerForm.invalid) {
      return;
    }

    this.manufacturerService
      .update(this.manufacturerForm.getRawValue())
      .subscribe({
        next: (vehicle: any) => {
          this.navigateBack();
        },
        error: (err: any) => this.handleError(err),
      });
  }

  private navigateBack(): void {
    this.router.navigate(['../../'], { relativeTo: this.route });
  }

  private handleError(err: any): void {
    this.conflict = true;
    console.error('Error:', err.message);
  }
}
