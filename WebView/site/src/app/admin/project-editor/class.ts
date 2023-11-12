import {Resource} from "../commons/Resource";

export class ResourceList{
  resources : Resource[];

  constructor(resources: Resource[]) {
    this.resources = resources;
  }
}

export class ResponseValue{
  value : string;

  constructor(value: string) {
    this.value = value;
  }
}
