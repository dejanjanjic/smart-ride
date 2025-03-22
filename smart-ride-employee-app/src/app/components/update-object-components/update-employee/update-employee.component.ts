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
import { EmployeeService } from '../../../services/employee.service';
import { ActivatedRoute, Router } from '@angular/router';
import { EmployeeSimple } from '../../../model/employee.model';

@Component({
  selector: 'app-update-employee',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
  ],
  templateUrl: './update-employee.component.html',
  styleUrl: './update-employee.component.css',
})
export class UpdateEmployeeComponent {
  private employeeService = inject(EmployeeService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);

  public initEmployee!: EmployeeSimple;
  public conflict = false;

  public employeeForm: FormGroup = this.formBuilder.group({
    id: { value: '', disabled: true },
    firstName: ['', [Validators.required]],
    lastName: ['', [Validators.required]],
    username: ['', [Validators.required]],
  });

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    const id = this.route.snapshot.params['id'];
    this.employeeService.getById(id).subscribe({
      next: (employee: EmployeeSimple) => {
        this.initEmployee = employee;

        const idControl = this.employeeForm.get('id');
        if (idControl) {
          idControl.enable({ emitEvent: false });
          this.employeeForm.patchValue(this.initEmployee);
          idControl.disable({ emitEvent: false });
        }
      },
      error: (err) => console.error('Error loading data:', err),
    });
  }
  submitForm(): void {
    if (this.employeeForm.invalid) {
      return;
    }

    this.employeeService.update(this.employeeForm.getRawValue()).subscribe({
      next: (employee: any) => {
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
