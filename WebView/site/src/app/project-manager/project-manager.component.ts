import {Component} from '@angular/core';
import {Project} from "../commons/Project";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {ProjectList} from "./class";
import {SERVER_PATH} from "../commons/config";

@Component({
  selector: 'project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css']
})
export class ProjectManagerComponent {
  projects: Project[] = [];

  storage: number[] = [1,15];

  newProjectView : boolean = false;

  private serverPath : string = "";

  constructor(private projectSelected: ProjectSelectorService, private updator: UpdatorService, private http: HttpClient) {
    this.updateApp();
  }

  ngOnInit() {
    console.log(this.serverPath);

    this.updator.refreshNeeded$
      .subscribe(() => {
        this.updateApp();
      });
  }

  updateApp(){
    this.projects = [];

    this.http.get<any>(SERVER_PATH + "/public/projects/").pipe( map((value: ProjectList) => {return value})).subscribe((res: ProjectList) =>{
      console.log(res);
      this.projects = res.projects;
    });
    //TODO récupérer depuis le serveur les infos du stockage

    console.log("Project up to date");
  }

  changeSelectedProject(project: Project){
    this.projectSelected.setProject(project);
  }

  createNewProject(name : string){
    this.hideNewProject();
  }


  getPercentOfStorage(): number{
    return isNaN((this.storage)[0] / (this.storage)[1] * 100) ? 0: Math.trunc((this.storage)[0] / (this.storage)[1] * 100);
  }

  showNewProject(){
    this.newProjectView = true;
  }

  hideNewProject(){
    this.newProjectView = false;
  }

}
