import { Injectable } from '@angular/core';
import {Project} from "./Project";
import {Subject} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class DataService {
    private project = new Subject<Project>();
    public project$ = this.project.asObservable();
    constructor(){}

    setProject(project: Project) {
        this.project.next(project);
    }
}
