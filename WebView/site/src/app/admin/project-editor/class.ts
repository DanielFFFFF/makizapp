import {Resource} from "../commons/Resource";

export class ResourceList{
  resources : Resource[];

  constructor(projects: Resource[]) {
    this.resources = projects;
  }
}
