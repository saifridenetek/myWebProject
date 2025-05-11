import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of, tap } from 'rxjs';
import { User } from '../../models/user.model';
import { Router } from '@angular/router';

export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
  password: string;
  name: string;
  role: 'DOCTOR' | 'PATIENT';
}

export interface AuthResponse {
  token: string;
  role: 'DOCTOR' | 'PATIENT';
  name: string;
}

export interface RegisterResponse {
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  getUsername(): string | null {
    throw new Error('Method not implemented.');
  }
  private user: User | null = null;
  private apiUrl = 'http://localhost:8901/api'; // 👈 Point to your mock server
  isLoggedIn$: any;

  constructor(private http: HttpClient, private router: Router) { }

  login(loginRequest: LoginRequest): Observable<User> {
    return this.http.post<AuthResponse>(`api/auth/login`, loginRequest).pipe(
      tap(response => {
        const user: User = {
          name: response.name,
          role: response.role,
          token: response.token
        };
        this.user = user;
        localStorage.setItem('token', user.token || '');
        localStorage.setItem('role', user.role);
      }),
      // Convert to User observable
      tap(() => console.log('User logged in successfully')),
    );
  }

  register(registerRequest: RegisterRequest): Observable<RegisterResponse> {
    return this.http.post<RegisterResponse>(`api/auth/register`, registerRequest);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem('token');
  }

  getRole(): string | null {
    return localStorage.getItem('role');
  }

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
