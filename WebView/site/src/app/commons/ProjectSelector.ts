import { Injectable } from '@angular/core';
import {Project} from "./Project";
import {Subject} from "rxjs";
import {ProjectService} from "./ProjectService";

@Injectable({
    providedIn: 'root'
})
export class ProjectSelector extends ProjectService{
  override project = new Subject<Project>();
  override project$ = this.project.asObservable();

  setProject(project: Project) {
      this.project.next(project);
  }
}
