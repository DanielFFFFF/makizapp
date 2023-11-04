import {Project} from "../commons/Project";

class ProjectList{
    projects : Project[];

    constructor(projects: Project[]) {
        this.projects = projects;
    }
}
export class ResponseProjects{
    response : ProjectList;
    constructor(response : ProjectList){
        this.response = response;
    }
}
