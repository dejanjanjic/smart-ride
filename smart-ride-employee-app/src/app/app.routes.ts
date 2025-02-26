import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { Role } from './enum/role.enum';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { VehiclesManagementComponent } from './components/vehicles-management/vehicles-management.component';
import { CarsTableComponent } from './components/cars-table/cars-table.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    loadComponent: () =>
      import('./components/admin-page/admin-page.component').then(
        (m) => m.AdminPageComponent
      ),
    canActivate: [AuthGuard],
    data: { roles: [Role.ADMINISTRATOR] },
    children: [
      {
        path: 'vehicles',
        component: VehiclesManagementComponent,
        children: [
          { path: 'cars', component: CarsTableComponent },
          { path: '', redirectTo: 'cars', pathMatch: 'full' },
        ],
      },
      { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
