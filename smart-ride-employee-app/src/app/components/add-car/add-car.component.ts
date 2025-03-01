import { Component, inject, OnInit } from '@angular/core';
import {
  FormBuilder,
  ReactiveFormsModule,
  FormGroup,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';
import { MatInputModule } from '@angular/material/input';
import { Router } from '@angular/router';
import { CarService } from '../../services/car.service';
import { Car } from '../../model/car.model';
import { MatCardModule } from '@angular/material/card';
import { ManufacturerService } from '../../manufacturer.service';
import { formatDate } from '@angular/common';

@Component({
  selector: 'app-add-car',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatCardModule,
  ],
  templateUrl: './add-car.component.html',
  styleUrl: './add-car.component.css',
})
export class AddCarComponent implements OnInit {
  private router: Router = inject(Router);
  private formBuilder = inject(FormBuilder);
  private carService: CarService = inject(CarService);
  private manufacturerService: ManufacturerService =
    inject(ManufacturerService);

  public manufacturers: Array<string> = [];
  public conflict: boolean = false;

  public addCarForm: FormGroup = this.formBuilder.group({
    id: [null, [Validators.required]],
    manufacturer: [null, [Validators.required]],
    model: [null, [Validators.required]],
    purchasePrice: [null, [Validators.required, Validators.min(0.0)]],
    purchaseDateTime: [null, [Validators.required]],
    description: [null, [Validators.required]],
  });

  public ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.manufacturerService.getAllNames().subscribe({
      next: (result: any) => {
        this.manufacturers = result;
      },
    });
  }
  public addCar() {
    const formValue = { ...this.addCarForm.value };

    const formattedDate = formatDate(
      formValue.purchaseDateTime,
      'yyyy-MM-dd',
      'en-US'
    );

    formValue.purchaseDateTime = `${formattedDate}T00:00:00`;

    this.carService.add(formValue).subscribe({
      next: (car: Car) => {
        console.log('Successful:', car);
        this.conflict = false;
      },
      error: (err) => {
        console.error('Conflict:', err.message);
        this.conflict = true;
      },
    });
  }
}
