import {Project} from "../commons/Project";

export class ProjectList{
    projects : Project[];

    constructor(projects: Project[]) {
        this.projects = projects;
    }
}

export class StorageInformations{
  used: number;
  total: number;

  constructor(used: number, total: number) {
    this.used = used;
    this.total = total;
  }
}
