import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { Auth } from '../../../core/services/auth';
import { LoginRequest } from '../../../core/models/login-request';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.html',
  styleUrl: './login.scss'
})
export class Login {

  email = '';
  password = '';
  errorMessage = '';
  loading = false;

  constructor(
    private auth: Auth,
    private router: Router
  ) {}

  onLogin(): void {
    this.errorMessage = '';
    this.loading = true;

    const data: LoginRequest = {
      email: this.email,
      password: this.password
    };

    this.auth.login(data).subscribe({
      next: () => {
        this.loading = false;
        this.router.navigate(['/dashboard']);
      },
      error: (error) => {
        this.loading = false;
        this.errorMessage =
          error.error?.message || 'Invalid email or password';
      }
    });
  }
}
