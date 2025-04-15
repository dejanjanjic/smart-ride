import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import {
  RouterOutlet,
  RouterModule,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-page',
  standalone: true,
  imports: [
    HeaderComponent,
    RouterOutlet,
    RouterModule,
    RouterLink,
    RouterLinkActive,
    MatListModule,
    MatSidenavModule,
    MatIconModule,
    CommonModule,
  ],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css',
})
export class AdminPageComponent {
  public isOpen = false;

  public toggle() {
    this.isOpen = !this.isOpen;
  }
}
