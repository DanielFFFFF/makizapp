import {Component} from '@angular/core';
import {Project} from "../commons/Project";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {ProjectList} from "./class";
import {SERVER_PATH} from "../commons/config";

/**
 * @class ProjectManagerComponent
 * This class represents a component in an Angular application that manages projects.
 */
@Component({
  selector: 'project-manager',
  templateUrl: './project-manager.component.html',
  styleUrls: ['./project-manager.component.css']
})
export class ProjectManagerComponent {

  /**
   * @property projects: Project[]
   * An array of Project objects, representing the projects managed by this component.
   */
  projects: Project[] = [];

  /**
   * @property storage: number[]
   * An array of two numbers, representing some form of storage related to the projects.
   */
  storage: number[] = [1,15];

  /**
   * @property newProjectView: boolean
   * A boolean value that indicates whether the view for creating a new project is currently displayed.
   */
  newProjectView : boolean = false;

  constructor(private projectSelected: ProjectSelectorService, private updator: UpdatorService, private http: HttpClient) {
    this.updateApp();
  }

  /**
   * @method ngOnInit()
   * A lifecycle hook that is called after Angular has initialized all data-bound properties of a directive.
   * Here it subscribes to the `refreshNeeded$` observable of the `updator` service.
   * When an event is emitted from `refreshNeeded$`, the `updateApp()` method is called to update the application.
   */
  ngOnInit() {
    this.updator.refreshNeeded$
      .subscribe(() => {
        this.updateApp();
      });
  }

  /**
   * @method updateApp()
   * Updates the application by fetching the latest projects from the server and updating the `projects` property.
   */
  updateApp(){
    this.projects = [];

    this.http.get<any>(SERVER_PATH + "/public/projects/").pipe( map((value: ProjectList) => {return value})).subscribe((res: ProjectList) =>{
      console.log(res);
      this.projects = res.projects;
    });
    //TODO récupérer depuis le serveur les infos du stockage

  }

  /**
   * @method changeSelectedProject(project: Project)
   * Changes the currently selected project.
   * @param {Project} project - The project to be selected.
   */
  changeSelectedProject(project: Project){
    this.projectSelected.setProject(project);
  }

  /**
   * @method createNewProject(name : string)
   * Creates a new project.
   * @param {string} name - The name of the new project.
   */
  createNewProject(name : string){
    //Todo : créer un nouveau projet en demandant au serveur
    this.updator.refresh();
    this.hideNewProject();
  }

  /**
   * @method getPercentOfStorage(): number
   * Calculates and returns the percentage of storage used.
   * @returns {number} The percentage of storage used.
   */
  getPercentOfStorage(): number{
    return isNaN((this.storage)[0] / (this.storage)[1] * 100) ? 0: Math.trunc((this.storage)[0] / (this.storage)[1] * 100);
  }

  /**
   * @method showNewProject()
   * Displays the view for creating a new project.
   */
  showNewProject(){
    this.newProjectView = true;
  }

  /**
   * @method hideNewProject()
   * Hides the view for creating a new project.
   */
  hideNewProject(){
    this.newProjectView = false;
  }

}
