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
    errorMessage: string = '';

    constructor(private auth: AuthService) { }

    onSubmit() {
      this.auth.register(this.name, this.password).subscribe({
          next: (response) => alert(response.message),
          error: (err) => {
              this.errorMessage = "An error occured while registering"
              console.error(err)
          }
      })
        this.name = ""
        this.password = ""
        this.confirmation = ""
    }

    formIsValid(): boolean {
        return this.password === this.confirmation && this.password.length > 0 && this.name.length > 0  && this.confirmation.length > 0;
    }

}
