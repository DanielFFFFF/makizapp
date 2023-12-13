import {Component} from '@angular/core';
import {ArResource} from "./ArResource";

@Component({
  selector: 'app-nft',
  template: `
      <a-nft
              type="nft"
              smooth="true"
              smoothCount="10"
              smoothTolerance=".01"
              smoothThreshold="5"
      >
          <a-entity
                  scale="5 5 5"
                  position="50 150 0"
          >
          </a-entity>
      </a-nft>
  `,
  standalone: true
})
export class Resource {

  resource:ArResource

  constructor(resources: ArResource) {
    this.resource = resources;
  }
}
