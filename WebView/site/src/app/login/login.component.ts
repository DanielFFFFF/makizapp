import {Component, inject} from '@angular/core';
import {AuthService} from "../Auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(private auth : AuthService, private router: Router) {}

  login(){
    //Todo : Check with the server
    //Todo : add google button
    this.auth.login();
    this.router.navigateByUrl('/admin');

  }
}
