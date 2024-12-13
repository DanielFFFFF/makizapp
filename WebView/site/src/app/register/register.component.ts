import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.css']
})
export class RegisterComponent {
    name: string = '';
    password: string = '';
    confirmation: string = '';

    constructor(private auth: AuthService) { }

    onSubmit() {
//       this.authService.register(this.name, this.password).subscribe({
//         next: (response) => {
//           console.log();
//           alert('Registration sent to the administrator for approval');
//         },
//         error: (err) => {
//           alert('Sorry! Registration Failed');
//           console.error('Registration failed:', err);
//         }
//       });
    }

    formIsValid(): boolean {
        return this.password === this.confirmation && this.password.length > 0 && this.name.length > 0  && this.confirmation.length > 0;
    }

}
