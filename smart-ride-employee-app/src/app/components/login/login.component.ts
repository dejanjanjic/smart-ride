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
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Employee } from '../../model/employee.model';
import { Role } from '../../enum/role.enum';

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
  private router: Router = inject(Router);
  private formBuilder = inject(FormBuilder);
  private authService = inject(AuthService);

  public invalidCredentials: boolean = false;

  public loginForm: FormGroup = this.formBuilder.group({
    username: [null, [Validators.required]],
    password: [null, [Validators.required]],
  });

  login() {
    if (this.loginForm.invalid) {
      return;
    }

    this.authService.login(this.loginForm.value).subscribe({
      next: (employee: Employee) => {
        console.log('Login successful:', employee);
        this.invalidCredentials = false;
        this.authService.store(employee);
        switch (employee.role) {
          case Role.ADMINISTRATOR:
            this.router.navigate(['/admin']);
            break;
          case Role.OPERATOR:
            this.router.navigate(['/operator']);
            break;
          case Role.MANAGEMENT:
            this.router.navigate(['/management']);
            break;
        }
      },
      error: (err) => {
        console.error('Error during login:', err.message);
        this.invalidCredentials = true;
      },
    });
  }
}
