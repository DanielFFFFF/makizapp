import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {ArResource} from "./ArResource";
import {AppConfigService} from "../config/app.config.service";

@Component({
  selector: 'app-user',
  styleUrls: ['./client.component.css'],
  templateUrl: './client.component.html'
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
