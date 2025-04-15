import { Component, EventEmitter, inject, OnInit, Output } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  @Output() toggleSidebar = new EventEmitter<void>();
  private authService = inject(AuthService);
  private router = inject(Router);

  public username: string | null = '';

  ngOnInit(): void {
    this.loadData();
  }

  public loadData() {
    this.username = this.authService.getUsername();
  }

  public logout() {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  public onToggleSidebar() {
    this.toggleSidebar.emit();
  }
}
