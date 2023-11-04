import {Component, Output} from '@angular/core';
import {Project} from "../commons/Project";
import {ProjectSelector} from "../commons/ProjectSelector";
import {UpdatorService} from "../commons/UpdatorService";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {ResponseProjects} from "./class";

@Component({
  selector: 'project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css']
})
export class ProjectManagerComponent {
  projects: Project[] = [];

  storage: number[] = [1,15];

  newProjectView : boolean = false;

  constructor(private projectSelected: ProjectSelector, private updator: UpdatorService, private http: HttpClient) {
    this.updateApp();
  }

  ngOnInit() {
    this.updator.refreshNeeded$
      .subscribe(() => {
        this.updateApp();
      });
  }

  updateApp(){
    //TODO recupérer depuis le serveur les noms des projets
    this.projects = [];

    this.http.get<any>(`http://test/public/projects`).pipe( map((value: ResponseProjects) => {return value})).subscribe((res: ResponseProjects) =>{
        this.projects = res.response.projects;
    });


  /*
    for (let i = 0; i < 20; i++) {
      const newProject = new Project(i + 1, `Projig,ug,,gi,,i,,i,ii,,i,i,i,,et ${i + 1}`,new Date().toString());
      this.projects.push(newProject);
    }

   */


    //TODO récupérer depuis le serveur les infos du stockage

    console.log("Project up to date");
  }

  changeSelectedProject(project: Project){
    this.projectSelected.setProject(project);
  }

  createNewProject(name : string){
    console.log('Le bouton de création de nouveau projet a été cliqué');
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

  protected readonly isNaN = isNaN;
  protected readonly Math = Math;
}
