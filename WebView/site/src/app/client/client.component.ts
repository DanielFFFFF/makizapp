import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {Resource} from "../admin/commons/Resource";

@Component({
  selector: 'app-user',
  styleUrls: ['./client.component.css'],
  template:`
      <body style="margin : 0; overflow: hidden;">
      <!-- minimal loader shown until image descriptors are loaded. Loading may take a while according to the device computational power -->
      <div class="arjs-loader">
          <div>Loading, please wait...</div>
      </div>

      <!-- a-frame scene -->
      <a-scene
              vr-mode-ui="enabled: false;"
              renderer="logarithmicDepthBuffer: true;"
              embedded
              arjs="trackingMethod: best; sourceType: webcam;debugUIEnabled: false;"
      ></a-scene>


      </body>
  `
})
export class ClientComponent {
  private SERVER_PATH: string = "";
  resources: Resource[] = [];

  constructor(private route: ActivatedRoute, private http:HttpClient) {
     let name = route.snapshot.paramMap.get("projectName")
    if(name == null){
      throw new Error("No project name specified")
    }
    this.http.get<any>(`${this.SERVER_PATH}/public/projects/${name}/resources/`).pipe(map((value: Resource[]) => {
      return value
    })).subscribe((res: Resource[]) => {
      this.resources = res;

    });
  }
}
