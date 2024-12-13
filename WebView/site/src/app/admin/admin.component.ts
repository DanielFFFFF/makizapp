import {Component, ElementRef, ViewEncapsulation} from '@angular/core';
import { HttpClient } from '@angular/common/http'; // Ajout de cet import


@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AdminComponent {

  constructor( private elementRef: ElementRef) {}

  ngAfterViewInit() {
    this.elementRef.nativeElement.ownerDocument.body.style.backgroundColor = '#F6F7FA';
    this.elementRef.nativeElement.ownerDocument.body.style.overflowY = 'scroll';
  }

}
