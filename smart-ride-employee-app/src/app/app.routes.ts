import { Routes } from '@angular/router';
import { LoginComponent } from './components/pages/login/login.component';
import { AuthGuard } from './guards/auth.guard';
import { Role } from './enum/role.enum';
import { AdminPageComponent } from './components/pages/admin-page/admin-page.component';
import { VehiclesManagementComponent } from './components/pages/vehicles-management/vehicles-management.component';
import { CarsTableComponent } from './components/table-components/cars-table/cars-table.component';
import { ManufacturersManagementComponent } from './components/pages/manufacturers-management/manufacturers-management.component';
import { UsersManagementComponent } from './components/pages/users-management/users-management.component';
import { EBikesTableComponent } from './components/table-components/e-bikes-table/e-bikes-table.component';
import { EScootersTableComponent } from './components/table-components/e-scooters-table/e-scooters-table.component';
import { AddCarComponent } from './components/add-object-components/add-car/add-car.component';
import { ViewDetailsCarComponent } from './components/view-details-components/view-details-car/view-details-car.component';
import { AddEBikeComponent } from './components/add-object-components/add-e-bike/add-e-bike.component';
import { AddEScooterComponent } from './components/add-object-components/add-e-scooter/add-e-scooter.component';
import { ViewDetailsEBikeComponent } from './components/view-details-components/view-details-e-bike/view-details-e-bike.component';
import { ViewDetailsEScooterComponent } from './components/view-details-components/view-details-e-scooter/view-details-e-scooter.component';
import { AddManufacturerComponent } from './components/add-object-components/add-manufacturer/add-manufacturer.component';
import { UpdateManufacturerComponent } from './components/update-object-components/update-manufacturer/update-manufacturer.component';
import { AddEmployeeComponent } from './components/add-object-components/add-employee/add-employee.component';
import { UpdateEmployeeComponent } from './components/update-object-components/update-employee/update-employee.component';
import { RentalsManagementComponent } from './components/pages/rentals-management/rentals-management.component';
import { ClientsManagementComponent } from './components/pages/clients-management/clients-management.component';
import { AddFailureComponent } from './components/add-object-components/add-failure/add-failure.component';
import { AddFailureByVehicleComponent } from './components/add-object-components/add-failure-by-vehicle/add-failure-by-vehicle.component';
import { VehicleMapComponent } from './components/pages/vehicle-map/vehicle-map.component';
import { RentalPriceConfigurationComponent } from './components/pages/rental-price-configuration/rental-price-configuration.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: 'admin',
    loadComponent: () =>
      import('./components/pages/admin-page/admin-page.component').then(
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
        path: 'manufacturers/add-manufacturer',
        component: AddManufacturerComponent,
      },
      {
        path: 'users/add-employee',
        component: AddEmployeeComponent,
      },
      {
        path: 'vehicles/cars/:id/add-failure',
        component: AddFailureByVehicleComponent,
      },
      {
        path: 'vehicles/cars/:id',
        component: ViewDetailsCarComponent,
      },
      {
        path: 'vehicles/e-bikes/:id',
        component: ViewDetailsEBikeComponent,
      },
      {
        path: 'vehicles/e-scooters/:id',
        component: ViewDetailsEScooterComponent,
      },
      {
        path: 'manufacturers/update-manufacturer/:id',
        component: UpdateManufacturerComponent,
      },
      {
        path: 'users/update-employee/:id',
        component: UpdateEmployeeComponent,
      },
      { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
    ],
  },
  {
    path: 'operator',
    loadComponent: () =>
      import('./components/pages/operator-page/operator-page.component').then(
        (m) => m.OperatorPageComponent
      ),
    canActivate: [AuthGuard],
    data: { roles: [Role.OPERATOR] },
    children: [
      {
        path: 'rentals',
        component: RentalsManagementComponent,
      },
      {
        path: 'clients',
        component: ClientsManagementComponent,
      },
      {
        path: 'add-failure',
        component: AddFailureComponent,
      },
      {
        path: 'map',
        component: VehicleMapComponent,
      },
      { path: '', redirectTo: 'rentals', pathMatch: 'full' },
    ],
  },
  {
    path: 'management',
    loadComponent: () =>
      import('./components/pages/manager-page/manager-page.component').then(
        (m) => m.ManagerPageComponent
      ),
    canActivate: [AuthGuard],
    data: { roles: [Role.MANAGEMENT] },
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
        path: 'rentals',
        component: RentalsManagementComponent,
      },
      {
        path: 'add-failure',
        component: AddFailureComponent,
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
        path: 'manufacturers/add-manufacturer',
        component: AddManufacturerComponent,
      },
      {
        path: 'users/add-employee',
        component: AddEmployeeComponent,
      },
      {
        path: 'vehicles/cars/:id/add-failure',
        component: AddFailureByVehicleComponent,
      },
      {
        path: 'vehicles/cars/:id',
        component: ViewDetailsCarComponent,
      },
      {
        path: 'vehicles/e-bikes/:id',
        component: ViewDetailsEBikeComponent,
      },
      {
        path: 'vehicles/e-scooters/:id',
        component: ViewDetailsEScooterComponent,
      },
      {
        path: 'manufacturers/update-manufacturer/:id',
        component: UpdateManufacturerComponent,
      },
      {
        path: 'users/update-employee/:id',
        component: UpdateEmployeeComponent,
      },
      {
        path: 'map',
        component: VehicleMapComponent,
      },
      {
        path: 'rental-price-config',
        component: RentalPriceConfigurationComponent,
      },
      { path: '', redirectTo: 'vehicles', pathMatch: 'full' },
    ],
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
];
