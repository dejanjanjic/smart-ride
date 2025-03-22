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
import { EmployeeService } from '../../services/employee.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Role } from '../../enum/role.enum';
import { MatSelectModule } from '@angular/material/select';

@Component({
  selector: 'app-add-employee',
  imports: [
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
  ],
  templateUrl: './add-employee.component.html',
  styleUrl: './add-employee.component.css',
})
export class AddEmployeeComponent {
  private employeeService = inject(EmployeeService);

  private router = inject(Router);
  private route = inject(ActivatedRoute);
  private formBuilder = inject(FormBuilder);

  public conflict = false;
  public roles = [Role.ADMINISTRATOR, Role.MANAGEMENT, Role.OPERATOR];
  public employeeForm: FormGroup = this.formBuilder.group({
    username: [null, [Validators.required]],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    password: [null, [Validators.required]],
    role: [null, [Validators.required]],
  });

  submitForm(): void {
    if (this.employeeForm.invalid) {
      return;
    }

    this.employeeService.add(this.employeeForm.value).subscribe({
      next: (employee: any) => {
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
