import { Injectable } from '@angular/core';
import {Project} from "./Project";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class ProjectSelector{
  private project = new Subject<Project>();
  public project$ = this.project.asObservable();

  setProject(project: Project) {
      this.project.next(project);
  }
}
