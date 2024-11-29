import { Directive, ElementRef, Renderer2 } from '@angular/core';

@Directive({
  standalone: true,
  selector: '[aframe]'
})

export class AFrameDirective {
  constructor(el: ElementRef, renderer: Renderer2) {
    // Optionally manipulate the element
  }
}
