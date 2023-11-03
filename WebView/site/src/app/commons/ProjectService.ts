// abstract.service.ts
import { Injectable } from '@angular/core';
import {Project} from "./Project";
import {Observable, Subject} from "rxjs";

@Injectable()
export abstract class ProjectService {
  // @ts-ignore
  protected project: Subject<Project>;
  // @ts-ignore
  protected project$: Observable<Project>;

  abstract setProject(project: Project): void;
}
