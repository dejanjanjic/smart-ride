import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { RouterModule, RouterOutlet } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-manager-page',
  imports: [
    HeaderComponent,
    RouterOutlet,
    RouterModule,
    MatListModule,
    MatSidenavModule,
  ],
  templateUrl: './manager-page.component.html',
  styleUrl: './manager-page.component.css',
})
export class ManagerPageComponent {
  public isOpen = true;

  public toggle() {
    this.isOpen = !this.isOpen;
  }
}
