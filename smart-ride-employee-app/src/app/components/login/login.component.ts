import { Component, inject } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import {
  ReactiveFormsModule,
  FormBuilder,
  FormGroup,
  Validators,
} from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EmployeeService } from '../../services/employee.service';

@Component({
  selector: 'app-login',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatCardModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    RouterModule,
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  private formBuilder = inject(FormBuilder);
  private employeeService = inject(EmployeeService);

  public invalidCredentials: boolean = false;

  public loginForm: FormGroup = this.formBuilder.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
  });

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    this.employeeService.login(this.loginForm.value).subscribe({
      next: (employee) => {
        console.log('Login successful:', employee);
        this.invalidCredentials = false;
        // Preusmeri korisnika na sledeću stranicu
      },
      error: (err) => {
        console.error('Error during login:', err.message);
        this.invalidCredentials = true;
      },
    });
  }
}
