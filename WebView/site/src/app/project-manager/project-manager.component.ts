import {Component, Output} from '@angular/core';
import {Project} from "../commons/Project";
import {ProjectSelector} from "../commons/ProjectSelector";
import {UpdatorService} from "../commons/UpdatorService";

@Component({
  selector: 'project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css']
})
export class ProjectManagerComponent {
  projects: Project[] = [];

  storage: number[] = [1,15];

  constructor(private projectSelected: ProjectSelector, private updator: UpdatorService) {
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
    for (let i = 0; i < 20; i++) {
      const newProject = new Project(i + 1, `Projig,ug,,gi,,i,,i,ii,,i,i,i,,et ${i + 1}`);
      this.projects.push(newProject);
    }

    //TODO récupérer depuis le serveur les infos du stockage

    console.log("Project up to date");
  }

  changeSelectedProject(project: Project){
    this.projectSelected.setProject(project);
  }

  createNewProject(){
    console.log('Le bouton de création de nouveau projet a été cliqué');
  }


  getPercentOfStorage(): number{
    return isNaN((this.storage)[0] / (this.storage)[1] * 100) ? 0: Math.trunc((this.storage)[0] / (this.storage)[1] * 100);
  }

  deleteProject(project : Project){
    console.log("Delete project : " + project.name);
    this.updateApp();
  }

  protected readonly isNaN = isNaN;
  protected readonly Math = Math;
}
