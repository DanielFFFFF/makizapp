import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

/**
 * @class SafePipe
 *
 * Pipe that sanitizes and bypasses security for resource URLs.
 */
@Pipe({
  name: "safe"
})
export class SafePipe implements PipeTransform {

  /**
   * @constructor
   * Creates a new instance of SafePipe.
   */
  constructor(private sanitizer: DomSanitizer) { }

  /**
   * @method transform
   * Transforms the input URL into a safe resource URL.
   */
  transform(url:any) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
