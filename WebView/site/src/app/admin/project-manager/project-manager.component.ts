import {Component} from '@angular/core';
import {Project} from "../commons/Project";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs";
import {ProjectList, StorageInformations} from "./class";
import {Created_id} from "../commons/Created_id";
import {AppConfigService} from "../commons/app.config.service";

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

  private SERVER_PATH = "";

  /**
   * @property projects: Project[]
   * An array of Project objects, representing the projects managed by this component.
   */
  projects: Project[] = [];

  /**
   * @property storage: number[]
   * An array of two numbers, representing some form of storage related to the projects.
   */
  storage: number[] = [];

  /**
   * @property newProjectView: boolean
   * A boolean value that indicates whether the view for creating a new project is currently displayed.
   */
  newProjectView: boolean = false;

  constructor(private projectSelected: ProjectSelectorService, private updator: UpdatorService, private http: HttpClient, private config: AppConfigService) {
  }

  /**
   * @method ngOnInit()
   * A lifecycle hook that is called after Angular has initialized all data-bound properties of a directive.
   * Here it subscribes to the `refreshNeeded$` observable of the `updator` service.
   * When an event is emitted from `refreshNeeded$`, the `updateApp()` method is called to update the application.
   */
  ngOnInit() {
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];
      this.updateApp();
    });
    this.updator.refreshNeeded$
      .subscribe(() => {
        this.updateApp();
      });
  }

  /**
   * @method updateApp()
   * Updates the application by fetching the latest projects from the server and updating the `projects` property.
   */
  updateApp() {
    this.projects = [];

    /*
    for(let i = 0;i<20;i++){
      this.projects.push(new Project(i,"toto","to"));
    }
     */

    this.http.get<any>(this.SERVER_PATH + "/public/projects/").pipe(map((value: ProjectList) => {
      return value
    })).subscribe((res: ProjectList) => {
      console.log(res);
      this.projects = res.projects;
    });

    this.http.get<any>(this.SERVER_PATH + "/public/storage/").pipe(map((value: StorageInformations) => {
      return value
    })).subscribe((res: StorageInformations) => {
      this.storage[0] = res.used;
      this.storage[1] = res.total;
    });


  }

  /**
   * @method changeSelectedProject(project: Project)
   * Changes the currently selected project.
   * @param {Project} project - The project to be selected.
   */
  changeSelectedProject(project: Project) {
    this.projectSelected.setProject(project);
  }

  /**
   * @method createNewProject(name : string)
   * Creates a new project.
   * @param {string} name - The name of the new project.
   */
  createNewProject(name: string) {
    // @ts-ignore
    this.http.post(SERVER_PATH + "/admin/projects/create/project/", {'name': name}).subscribe((data: Created_id) => {
      //Refresh the list of project with the new project for get the new
      //this.updator.refresh();
      //Or create temporaly the new project
      this.projects.push(new Project(data.created_id, name, new Date().toString()));
      this.hideNewProject();
    })
  }

  /**
   * @method getPercentOfStorage(): number
   * Calculates and returns the percentage of storage used.
   * @returns {number} The percentage of storage used.
   */
  getPercentOfStorage(): number {
    return isNaN((this.storage)[0] / (this.storage)[1] * 100) ? 0 : Math.trunc((this.storage)[0] / (this.storage)[1] * 100);
  }

  /**
   * @method showNewProject()
   * Displays the view for creating a new project.
   */
  showNewProject() {
    this.newProjectView = true;
  }

  /**
   * @method hideNewProject()
   * Hides the view for creating a new project.
   */
  hideNewProject() {
    this.newProjectView = false;
  }

}
