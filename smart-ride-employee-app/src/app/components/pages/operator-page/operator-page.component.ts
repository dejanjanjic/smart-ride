import { Component } from '@angular/core';
import { HeaderComponent } from '../../header/header.component';
import { RouterModule, RouterOutlet } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatSidenavModule } from '@angular/material/sidenav';

@Component({
  selector: 'app-operator-page',
  imports: [
    HeaderComponent,
    RouterOutlet,
    RouterModule,
    MatListModule,
    MatSidenavModule,
  ],
  templateUrl: './operator-page.component.html',
  styleUrl: './operator-page.component.css',
})
export class OperatorPageComponent {
  public isOpen = true;

  public toggle() {
    this.isOpen = !this.isOpen;
  }
}
