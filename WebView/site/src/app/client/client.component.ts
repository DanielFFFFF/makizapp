import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {ArResource} from "./ArResource";
import {AppConfigService} from "../config/app.config.service";

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

      <a-nft *ngFor="let resource of this.resources"
             type="nft"
             url="/MARKER/{{resource.id}}/"
             smooth="true"
             smoothCount="10"
             smoothTolerance=".01"
             smoothThreshold="5">
          <a-assets>
              <a-image *ng-if="resource.imageAsset != null" src="/IMAGE/{{resource.imageAsset}}"></a-image>
              <audio *ng-if="resource.soundAsset != null" src="/SOUND/{{resource.soundAsset}}"></audio>
              <video *ng-if="resource.videoAsset != null" src="{{resource.videoAsset}}"></video>
          </a-assets>
      </a-nft>
      </body>
  `
})


export class ClientComponent {
  private SERVER_PATH: string = "";
  resources: ArResource[] = [];

  ngOnInit() {
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];
    });
  }

  constructor(private route: ActivatedRoute, private http:HttpClient, private config: AppConfigService) {
     let projectId = route.snapshot.paramMap.get("projectID")
    if(projectId == null){
      alert("Project not found")
      throw new Error("Project is is invalid")
    }
    this.http.get<ArResource[]>( `${this.SERVER_PATH}/public/projects/${projectId}/resources`).pipe(map((value: ArResource[]) => {
      return value
    })).subscribe((res: ArResource[]) => {
      this.resources = res;
    });
  }

}
