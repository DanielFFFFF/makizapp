import { Injectable } from '@angular/core';
import {Project} from "./Project";
import {Subject} from "rxjs";

/**
 * @class ProjectSelectorService
 *
 * Service that provides a mechanism to select a project.
 */
@Injectable({
  providedIn: 'root'
})
export class ProjectSelectorService {

  /**
   * @property project
   * Subject that emits events when a project is selected.
   */
  private project = new Subject<Project>();

  /**
   * @property project$
   * Observable that can be subscribed to in order to react to project selection events.
   */
  public project$ = this.project.asObservable();

  /**
   * @method setProject
   * Sets the selected project and triggers a project selection event.
   */
  setProject(project: Project) {
    this.project.next(project);
  }
}
