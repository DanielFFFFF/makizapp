import {Component, ElementRef, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Resource} from "../commons/Resource";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";
import {DEFAULT_IMAGE, SERVER_PATH} from "../commons/config";
import {map} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {ResourceList, ResponseValue} from "./class";
import {Created_id} from "../commons/Created_id";

@Component({
    selector: 'project-editor',
    templateUrl: './project-editor.component.html',
    styleUrls: ['./project-editor.component.css']
})
/**
 * @class ProjectEditorComponent
 *
 * This class is an Angular component that allows you to edit a project.
 * This component takes care of displaying the list of resources and providing menus for editing/creating/deleting resources.
 */
export class ProjectEditorComponent {

    /**
     * @property {ElementRef | null} filterResource - Reference to the HTML element for filtering resources.
     */
    @ViewChild('filterResource') filterResource: ElementRef | null = null;

    /**
     * @property {Resource} defaultProject - The default project when no project has been selected.
     */
    defaultProject = new Project(-1, "Selectionnez un projet ...", "JJ/MM/AA");

    /**
     @property {Project} project - The currently edited project (initialized to a project (id = -1) to display the default text: "Select a project...").
     */
    project: Project = this.defaultProject;

    /**
     @property {Resource[]} resources - List of all resources in the project. This list is complete and unfiltered.
     */
    resources: Resource[] = [];

    /**
     * @property {Resource[]} resourcesFiltered - List of filtered resources. This list is filtered according to the search bar.
     */
    resourcesFiltered: Resource[] = [];

    /**
     * @property {Resource | null} resourceSelected - The resource clicked by the player for use in the resource edit display.
     */
    resourceSelected: Resource | null = null;

    /**
     * @property {boolean} videoMod - Check if we should display the video or image menu in an resource.
     */
    videoMod: boolean = true;

    /**
     * @property {boolean} renameView - Show or not the menu to rename an resource.
     */
    renameView: boolean = false;

    /**
     * @property {boolean} renameView - Show or not the menu to edit an resource.
     */
    editResourceView: boolean = false;

    /**
     * @property {boolean} newResourceView - Show or not the menu to create an resource.
     */
    newResourceView: boolean = false;


    constructor(private projectSelected: ProjectSelectorService, private updator: UpdatorService, private http: HttpClient) {
    }

    /**
     * @method ngOnInit()
     * Component initialization method.
     * It is used to communicate with the ProjectSelectorService to retrieve
     * the selected project from the list of projects.
     */
    ngOnInit() {
        this.projectSelected.project$.subscribe(project => {
            this.project = project;
            this.updateProjectSelected()
        });
    }

    /**
     * @method updateProjectSelected()
     * Updates the selected project.
     * Update the list of resources in the project.
     */
    updateProjectSelected() {
        this.resources = [];

        this.http.get<any>(SERVER_PATH + `/public/projects/${this.project.id}/resources/`).pipe(map((value: ResourceList) => {
            return value
        })).subscribe((res: ResourceList) => {
            this.resources = res.resources;
            this.resourcesFiltered = this.resources;

            for (let i = 0; i < this.resourcesFiltered.length; i++) {
                this.getThumbnailById(this.resources[i]);
            }
        });
        /*
        for (let i = 0; i < this.project.id; i++) {
          const resource = new Resource(1, "toto", `Entité ${i}`, "https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg", Math.trunc(Math.random() * 100), "https://player.vimeo.com/video/879891554?h=fba301cac0", Math.trunc(Math.random() * 100), null, Math.trunc(Math.random() * 100), "https://lasonotheque.org/UPLOAD/mp3/0001.mp3", Math.trunc(Math.random() * 100), 0, Math.trunc(Math.random() * 100), this.formatDate(new Date()));
          this.resources.push(resource);
        }
         */
    }

    /**
     * @method filterList()
     * Filters the list of resources based on the value entered in the search bar.
     * If nothing is entered, the complete list is displayed.
     */
    filterList() {
        if (this.filterResource != null) {
            let filter = this.filterResource.nativeElement.value;
            this.resourcesFiltered = this.resources.filter(resource => resource._name.includes(filter));
        } else {
            this.resourcesFiltered = this.resources;
        }

    }

    /**
     * @method formatDate(date: Date): string
     * @param {Date} date - The date to format
     * Formats a date into a string in this way: DD/MM/YY.
     */
    formatDate(date: Date): string {
        const day = date.getDate().toString().padStart(2, '0');
        const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Les mois commencent à 0, donc on ajoute 1
        const year = date.getFullYear().toString();
        return `${day}/${month}/${year}`;
    }

    /**
     * @method createNewResource(name: String)
     * Create a new resource and push to the server.
     *
     * @param {string} name - The name of the new resource.
     * @param {HTMLInputElement} thumbnail - The thumbnail associated with the new resource.
     * @param {HTMLInputElement} video - The video element associated with the new resource.
     * @param {HTMLInputElement} sound - The sound element associated with the new resource.
     * @param {HTMLInputElement} image - The image element associated with the new resource.
     */
    createNewResource(name: string, thumbnail: HTMLInputElement, video: HTMLInputElement, sound: HTMLInputElement, image: HTMLInputElement) {
        //Create the request body
        let body = {
            "name": name,
            "Marker1": "",
            "Marker2": "",
            "Marker3": "",
            "thumbnail": "",
            "imageAsset": "",
            "videoAsset": video.value,
            "soundAsset": ""
        }

        //Add to the body the thumbnail if exist (sets by the user)
        if (thumbnail.files != null) {
            const reader = new FileReader();
            reader.readAsDataURL(thumbnail.files[0]);
            reader.onload = () => {
                body.thumbnail = reader.result as string;
            };
        } else {
            //todo popup impossible de charger l'image tracking
        }

        //Add to the body the image if exist (sets by the user)
        if (image.files != null && image.files.length != 0) {
            const reader = new FileReader();
            reader.readAsDataURL(image.files[0]);
            reader.onload = () => {
                body.imageAsset = reader.result as string;
            };
        } else {
            //todo popup impossible de charger l'image
        }

        //Add to the body the sound if exist (sets by the user)
        if (sound.files != null) {
            const reader = new FileReader();
            reader.readAsDataURL(sound.files[0]);
            reader.onload = () => {
                body.soundAsset = reader.result as string;
            };
        } else {
            //todo popup impossible de charger le son
        }

        //Todo générer les markers


        // Execute and send the request POST to create the resource
        // @ts-ignore
        this.http.post(SERVER_PATH + `/admin/projects/${this.project.id}/create/resource/`, body).subscribe((data: Created_id) => {
            this.hideNewResource();
        });


    }

    /**
     * @method uploadNewThumbnail()
     * Uploads a new thumbnail for the selected resource.
     * @param {HTMLInputElement} thumbnail - The thumbnail element associated with the new resource.
     */
    uploadNewThumbnail(thumbnail: HTMLInputElement) {
        //TODO changer
        let imgBase64 = "https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg"
        this.http.post(SERVER_PATH + `/admin/projects/ressources/${this.resourceSelected?._id}/thumbnail/`, {'name': `thumbnail_res_${this.resourceSelected?._id}`, 'image': imgBase64}).subscribe(() => {
            if (this.resourceSelected) {
                this.resourceSelected._thumbnail = imgBase64;
            }
        });


    }

    /**
     * @method uploadNewImage()
     * Uploads a new image for the selected resource.
     * @param {HTMLInputElement} image - The image file associated with the new resource.
     */
    uploadNewImage(image: HTMLInputElement) {
        //TODO Upload a image for this selected resource
        //TODO send request to server --> on sucess refresh --> on echec show popup error
        console.log("Upload new Image");
    }

    /**
     * @method uploadNewAudio()
     * Uploads a new audio file for the selected resource.
     */
    uploadNewAudio() {
        //TODO Upload a audio for this selected resource
        //TODO send request to server --> on sucess refresh --> on echec show popup error
        console.log("Upload new Audio");
    }

    /**
     * @method reloadMarker()
     * Reloads the marker.
     */
    reloadMarker() {
        //TODO send request to server --> on sucess refresh --> on echec show popup error
        console.log("Marker reloaded");
    }

    /**
     * @method deleteProject()
     * Delete the project.
     */
    deleteProject() {
        console.log("Delete project");
        //TODO send request to server --> on sucess refresh --> on echec show popup error
        this.updator.refresh();
        //Set the default project to indicate (select a project)
        this.project = this.defaultProject;
    }

    /**
     * @method renameProject()
     * @param {string} newName - The new name of the project.
     * Rename the project.
     */
    renameProject(newName: string) {
        console.log("Rename project");
        //TODO send request to server --> on sucess refresh --> on echec show popup error
        this.updator.refresh();
        //To avoid reloading project recovery
        this.project.name = newName;
        this.hideRenameProject();
    }

    selectResource(resource: Resource) {
        this.resourceSelected = resource;
        if (this.resourceSelected._image != null) {
            this.videoMod = false;
        }
        this.showEditResource()
    }

    /**
     * @method exitEdition()
     * Exits edit mode.
     */
    exitEdition() {
        this.resourceSelected = null;
        this.hideEditResource();
    }

    /**
     * @method showEditResource()
     * Displays the resource edit mode.
     */
    showEditResource() {
        this.editResourceView = true;
    }

    /**
     * @method hideEditResource()
     * Hides the resource edit mode.
     */
    hideEditResource() {
        this.editResourceView = false;
    }

    /**
     * @method showRenameProject()
     * Displays the project rename mode.
     */
    showRenameProject() {
        this.renameView = true;
    }

    /**
     * @method hideRenameProject()
     * Hides the project rename mode.
     */
    hideRenameProject() {
        this.renameView = false;
    }

    /**
     * @method showNewResource()
     * Displays the new resource mode.
     */
    showNewResource() {
        this.newResourceView = true;
    }

    /**
     * @method hideNewResource()
     * Hides the new resource mode.
     */
    hideNewResource() {
        this.newResourceView = false;
    }


    getThumbnailById(resource: Resource) {
        console.log(resource);
        if (resource._thumbnail_id !== undefined) {
            this.http.get<any>(SERVER_PATH + `/public/projects/ressources/images/${resource._thumbnail_id}/`)
                .pipe(
                    map((value: ResponseValue) => {
                        return value
                    })
                )
                .subscribe((res: ResponseValue) => {
                    resource._thumbnail = res.value;
                });
        } else {
            resource._thumbnail = DEFAULT_IMAGE;
        }
    }

    deleteResource() {

        this.exitEdition();
    }

    saveEdition() {

        this.exitEdition();
    }
}
