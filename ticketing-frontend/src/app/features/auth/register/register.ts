
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';

import { Auth } from '../../../core/services/auth';
import { RegisterRequest } from '../../../core/models/register-request';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './register.html',
  styleUrl: './register.scss'
})
export class Register {

  name = '';
  email = '';
  password = '';
  confirmPassword = '';

  errorMessage = '';
  successMessage = '';
  loading = false;

  constructor(
    private auth: Auth,
    private router: Router
  ) {}

  onRegister(): void {

    this.errorMessage = '';
    this.successMessage = '';

    // Check password confirmation
    if (this.password !== this.confirmPassword) {
      this.errorMessage = 'Passwords do not match.';
      return;
    }

    // Prepare registration data
    const data: RegisterRequest = {
      name: this.name,
      email: this.email,
      password: this.password
    };

    this.loading = true;

    // Send registration request to backend
    this.auth.register(data).subscribe({

      next: () => {

        this.loading = false;

        // Remove automatically stored token
        this.auth.logout();

        // Show success message
        alert('Account created successfully! Please login.');

        // Navigate to login page
        this.router.navigate(['/login']);

      },

      error: (error) => {

        this.loading = false;

        this.errorMessage =
          error.error?.message ||
          'Registration failed. Please try again.';

      }

    });

  }

}
