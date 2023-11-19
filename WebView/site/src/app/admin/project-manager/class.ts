import {Project} from "../commons/Project";

export class ProjectList{
    content : Project[];

    constructor(projects: Project[]) {
        this.content = projects;
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
