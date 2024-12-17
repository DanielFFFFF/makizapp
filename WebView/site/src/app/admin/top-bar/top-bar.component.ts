import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.css']
})
export class TopBarComponent {
    constructor(private router: Router, private authService: AuthService) {
    }

    logout() {
        this.authService.logout();
        this.router.navigate(['/login']);
    }
}
