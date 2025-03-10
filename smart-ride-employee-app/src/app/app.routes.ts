import { Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { Role } from './enum/role.enum';
import { AdminPageComponent } from './components/admin-page/admin-page.component';
import { VehiclesManagementComponent } from './components/vehicles-management/vehicles-management.component';
import { CarsTableComponent } from './components/cars-table/cars-table.component';
import { ManufacturersManagementComponent } from './components/manufacturers-management/manufacturers-management.component';
import { UsersManagementComponent } from './components/users-management/users-management.component';
import { EBikesTableComponent } from './components/e-bikes-table/e-bikes-table.component';
import { EScootersTableComponent } from './components/e-scooters-table/e-scooters-table.component';
import { AddCarComponent } from './components/add-car/add-car.component';
import { ViewDetailsCarComponent } from './components/view-details-car/view-details-car.component';
import { AddEBikeComponent } from './components/add-e-bike/add-e-bike.component';
import { AddEScooterComponent } from './components/add-e-scooter/add-e-scooter.component';

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
      },
      {
        path: 'manufacturers',
        component: ManufacturersManagementComponent,
      },
      {
        path: 'users',
        component: UsersManagementComponent,
      },
      {
        path: 'vehicles/add-car',
        component: AddCarComponent,
      },
      {
        path: 'vehicles/add-e-bike',
        component: AddEBikeComponent,
      },
      {
        path: 'vehicles/add-e-scooter',
        component: AddEScooterComponent,
      },
      {
        path: 'vehicles/:id',
        component: ViewDetailsCarComponent,
      },
      { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
