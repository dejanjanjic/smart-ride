import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import { RouterOutlet, RouterModule } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-admin-page',
  imports: [
    HeaderComponent,
    RouterOutlet,
    RouterModule,
    MatListModule,
    MatSidenavModule,
  ],
  templateUrl: './admin-page.component.html',
  styleUrl: './admin-page.component.css',
})
export class AdminPageComponent {
  public isOpen = true;

  public toggle() {
    this.isOpen = !this.isOpen;
  }
}
