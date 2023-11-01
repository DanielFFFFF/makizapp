import {Component, Output} from '@angular/core';
import {Project} from "../commons/Project";
import {DataService} from "../commons/DataService";

@Component({
  selector: 'project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css']
})
export class ProjectManagerComponent {
  projects: Project[] = [];

  storage: number[] = [1,15];

  //private idProjectSelected = any;

  constructor(private projectService: DataService) {
    for (let i = 0; i < 20; i++) {
      const newProject = new Project(i + 1, `Projet ${i + 1}`);
      this.projects.push(newProject);
    }
  }

  changeSelectedProject(project: Project){
    this.projectService.setProject(project);
  }

  createNewProject(){
    console.log('Le bouton de création de nouveau projet a été cliqué');
  }

  getPercentOfStorage(): number{
    return isNaN((this.storage)[0] / (this.storage)[1] * 100) ? 0: Math.trunc((this.storage)[0] / (this.storage)[1] * 100);
  }

  protected readonly isNaN = isNaN;
  protected readonly Math = Math;
}
