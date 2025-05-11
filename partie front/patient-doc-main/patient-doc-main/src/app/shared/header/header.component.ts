import { Component } from '@angular/core';
import { AuthService } from '../../components/core/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  imports: [],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent {
  isLoggedIn = false;
  username: string | null = null;

  constructor(private authService: AuthService, private router: Router) {
    this.authService.isLoggedIn$.subscribe((loggedIn: boolean) => {
      this.isLoggedIn = loggedIn;
      this.username = this.authService.getUsername(); // Assuming AuthService has a getUsername method
    });
  }

  logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
