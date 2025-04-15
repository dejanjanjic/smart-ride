import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import {
  RouterModule,
  RouterOutlet,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-manager-page',
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
  templateUrl: './manager-page.component.html',
  styleUrl: './manager-page.component.css',
})
export class ManagerPageComponent {
  public isOpen = false;

  public toggle() {
    this.isOpen = !this.isOpen;
  }
}
