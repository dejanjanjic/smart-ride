import { Component } from '@angular/core';
import { RouterOutlet, RouterModule } from '@angular/router';

@Component({
  selector: 'app-vehicles-management',
  imports: [RouterOutlet, RouterModule],
  templateUrl: './vehicles-management.component.html',
  styleUrl: './vehicles-management.component.css',
})
export class VehiclesManagementComponent {}
