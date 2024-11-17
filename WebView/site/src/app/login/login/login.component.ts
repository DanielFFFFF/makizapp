import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';  // Le service que tu vas créer
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    this.authService.login(this.username, this.password).subscribe({
      next: (response) => {
        localStorage.setItem('token', response.token);  // Stocke le token
        this.router.navigate(['/admin']);  // Redirige vers l'interface admin
      },
      error: (err) => {
        this.errorMessage = 'Invalid credentials';
      }
    });
  }
}
