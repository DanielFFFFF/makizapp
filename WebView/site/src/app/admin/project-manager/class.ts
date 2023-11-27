import {Project} from "../commons/Project";

/**
 * @class ProjectList
 * Class used to encapsulate the response to the API for the list of projects.
 */
export class ProjectList{
    content : Project[];

    constructor(projects: Project[]) {
        this.content = projects;
    }
}

/**
 * @class StorageInformations
 * Class used to encapsulate the response to the API for storage.
 */
export class StorageInformations{
  used: number;
  total: number;

  constructor(used: number, total: number) {
    this.used = used;
    this.total = total;
  }
}
