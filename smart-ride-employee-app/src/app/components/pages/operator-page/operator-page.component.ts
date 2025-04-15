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
  selector: 'app-operator-page',
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
  templateUrl: './operator-page.component.html',
  styleUrl: './operator-page.component.css',
})
export class OperatorPageComponent {
  public isOpen = false;

  public toggle() {
    this.isOpen = !this.isOpen;
  }
}
