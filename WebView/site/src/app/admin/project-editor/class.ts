import {Resource} from "../commons/Resource";

export class ResourceList{
  resources : Resource[];

  constructor(projects: Resource[]) {
    this.resources = projects;
  }
}

export class ResponseValue{
  value : string;

  constructor(value: string) {
    this.value = value;
  }
}
