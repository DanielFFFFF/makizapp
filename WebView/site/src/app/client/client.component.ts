import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {Resource} from "../commons/Resource";
import {AppConfigService} from "../config/app.config.service";

@Component({
  selector: 'app-user',
  styleUrls: ['./client.component.css'],
  templateUrl: './client.component.html'
})


export class ClientComponent {
  SERVER_PATH: string = "";
  MARKERS : string = "";
  resources: Resource[] = [];
  projectId : string = "";

  ngOnInit() {
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];
      this.MARKERS = `${this.SERVER_PATH}/resources/MARKERS`;


      this.getResources();
    });

  }

  constructor(private route: ActivatedRoute, private http:HttpClient, private config: AppConfigService) {
    this.projectId = (route.snapshot.paramMap.get("projectID") as string);
    if(this.projectId == null){
      alert("Project not found")
      throw new Error("Project is is invalid")
    }
  }

  getResources(){
      this.http.get<any>( `${this.SERVER_PATH}/public/projects/${this.projectId}/resources`).pipe(map((value: Resource[]) => {
          return value
      })).subscribe((res: Resource[]) => {
        console.log(res);
          this.resources = res;

        this.resources.map(resource => {
          this.getContentOfResource(resource);
        });
      });
  }

  getContentOfResource(resource:Resource) {
    if (resource.videoAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/public/video/${resource.videoAssetId}`, {responseType: 'text'})
          .subscribe((res: any) => {
            resource.videoAsset = res;
          });
    }

    if (resource.soundAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/resources/SOUND/${resource.soundAssetId}`, {responseType: 'blob'})
          .subscribe((res) => {
            resource.soundAsset = URL.createObjectURL(res);
          });
    }

    if (resource.imageAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/resources/IMAGE/${resource.imageAssetId}`, {responseType: 'blob'})
          .subscribe((res) => {
            let reader = new FileReader();
            reader.addEventListener("loadend", () => {
              resource.imageAsset = (reader.result as string);
            });
            if (res) {
              reader.readAsDataURL(res);
            }
          });
    }
  }
}
