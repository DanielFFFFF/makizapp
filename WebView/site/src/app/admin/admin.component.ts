import {Component, ElementRef, ViewEncapsulation} from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Ajout de cet import
import { Router } from '@angular/router'; // Ajout de cet import
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AdminComponent {

  constructor(private http: HttpClient, private router: Router, private elementRef: ElementRef, private authService: AuthService) {}

  ngAfterViewInit() {
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = '#F6F7FA';
    this.elementRef.nativeElement.ownerDocument.body.style.overflowY = 'scroll';
  }


  logout() {
    console.log("Logout button clicked");
    this.authService.logout();
    this.router.navigate(['/login']);
  }


}
