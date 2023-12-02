import {Component, ElementRef, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Resource} from "../commons/Resource";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";
import {map} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Created_id} from "../commons/Created_id";
import {AppConfigService} from "../../config/app.config.service";


/**
 * @enum Onglet
 * Enumeration representing the list of tabs for modifying a resource.
 */
enum Onglet {
  Base, Thumbnail, Video, Sound, Image,
}
declare function runAPI(jpg: string): void;
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
   * @property {string} SERVER_PATH - Constant containing the server path.
   */
  SERVER_PATH: string = ""

  /**
   * @property {ElementRef | null} filterResource - Reference to the HTML element for filtering resources.
   */
  @ViewChild('filterResource') filterResource: ElementRef | null = null;

  /**
   * @property {Resource} defaultProject - The default project when no project has been selected.
   */
  defaultProject = new Project("", "-1", "Selectionnez un projet ...", []);

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

  /**
   * @property {boolean} showResponses
   * @private
   * Is used for show in console the response of all requests to server.
   */
  private showResponses: boolean = true;

  /**
   * @property {Onglet} ongletSelected - The selected tab.
   */
  ongletSelected: Onglet = Onglet.Base;

  /**
   * @property {Onglet} Onglet - Variable containing the enumeration. This allows it to be used within the class.
   */
  Onglet = Onglet;

  /**
   * @property {string} newName - When editing a resource, this variable allows you to modify the name later.
   * @private
   */
  newName: string = "";


  constructor(private projectSelected: ProjectSelectorService, private updator: UpdatorService, private http: HttpClient, private config: AppConfigService) {
  }

  /**
   * @method ngOnInit()
   * Component initialization method.
   * It is used to communicate with the ProjectSelectorService to retrieve
   * the selected project from the list of projects.
   */
  ngOnInit() {
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];
    });
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


    this.http.get<any>(this.SERVER_PATH + `/public/projects/${this.project.getId()}/resources/`).pipe(map((value: Resource[]) => {
      return value
    })).subscribe((res: Resource[]) => {
      if (this.showResponses) console.log("/public/projects/resources/${id}/");
      if (this.showResponses) console.log(res);
      this.resources = res;
      this.filterList();

      this.resources.map(resource => {
        // For each resource, we send a request to the server to get the thumbnail
        this.http.get<any>(this.SERVER_PATH + `/path/to/server/static/to/get/thumbnail/`).pipe(map((value: any) => {
          return value
        })).subscribe((res) => {
          if (this.showResponses) console.log("/path/to/server/static/to/get/thumbnail/");
          if (this.showResponses) console.log(res);
          //Update the list
          //TODO : Replace filterList call with update resource if it is in filterList
          this.filterList();
        });
      });
    });
    /*
      this.project.getArResource().map(id =>{
          // For each id, we send a request to the server
          this.http.get<any>(this.SERVER_PATH + `/public/projects/resources/${id}/`).pipe(map((value: any) => {
              return value
          })).subscribe((res: StorageInformations) => {
              if (this.showResponses) console.log("/public/projects/resources/${id}/");
              if (this.showResponses) console.log(res);
          });
      });

     */


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
      this.resourcesFiltered = this.resources.filter(resource => resource.name.includes(filter));
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
  createNewResource(name: HTMLInputElement, thumbnail: HTMLInputElement, video: HTMLInputElement, sound: HTMLInputElement, image: HTMLInputElement) {
    //Create the request body
    const body: { [key: string]: any } = {};
    body["name"] = name.value;

    if (name.value == "") {
      alert("Il manque le nom de la ressource !");
      return;
    }


    if (video.value != "") {
      body["videoAsset"] = video.value;
    } else if (!image.files || image.files.length == 0 || !sound.files || sound.files.length == 0) {
      alert("Il manque soit la vidéo soit (audio et image) !");
      return;
    }

    // Array containing promises to execute the post query only when all data has been retrieved
    let promises = [];

    //Add to the body the thumbnail if exist (sets by the user)
    if (thumbnail.files != null && thumbnail.files.length != 0) {
      const reader = new FileReader();
      let p = new Promise((resolve) => {
        reader.onload = () => {
          body["thumbnail"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');

          //TODO a retirer !!!! et faire la création de marker
          //body["marker1"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
          //body["marker2"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
          //body["marker3"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');



          //run NFT-Marker-Creator with path of the image in parameter
          runAPI(image.name);

          //The 3 local output files are located in the "output" folder of the NFT-Marker-Creator folder
          var srcOutput = "../../../assets/NFT-Marker-Creator/output/";
          var imgIset = srcOutput+image.name+".iset";
          var imgFset = srcOutput+image.name+".fset";
          var imgFset3 = srcOutput+image.name+".fset3";

          body["marker1"] = imgIset;
          body["marker2"] = imgFset;
          body["marker3"] = imgFset3;

          //Delete the 3 local output files
          var fs = require('fs');
          fs.unlinkSync(imgIset);
          fs.unlinkSync(imgFset);
          fs.unlinkSync(imgFset3);

          resolve(true);
        };
      });
      reader.readAsDataURL(thumbnail.files[0]);
      promises.push(p);
    } else {
      alert("Il manque l'image à capturer !");
      return;
    }

    //Add to the body the image if exist (sets by the user)
    if (image.files != null && image.files.length != 0) {
      const reader = new FileReader();
      let p = new Promise((resolve) => {
        reader.onload = () => {
          body["imageAsset"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
          resolve(true);
        };
      });
      reader.readAsDataURL(image.files[0]);
      promises.push(p);
    }

    //Add to the body the sound if exist (sets by the user)
    if (sound.files != null && sound.files.length != 0) {
      const reader = new FileReader();
      let p = new Promise((resolve) => {
        reader.onload = () => {
          body["soundAsset"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
          resolve(true);
        };
      });
      reader.readAsDataURL(sound.files[0]);
      promises.push(p);
    }

    // Execute and send the request POST to create the resource
    // Will be executed when all promises have been delivered
    // @ts-ignore
    Promise.all(promises).then(() => {
      // @ts-ignore
      this.http.post(this.SERVER_PATH + `/admin/projects/${this.project.id}/create/resource/`, body).subscribe((res: Created_id) => {
        this.hideNewResource();
        this.updateProjectSelected();
      });
    });


  }

  /**
   * @method uploadNewThumbnail(thumbnail: HTMLInputElement)
   * Uploads a new thumbnail for the selected resource.
   * @param {HTMLInputElement} thumbnail - The thumbnail element associated with the new resource.
   */
  uploadNewThumbnail(thumbnail: HTMLInputElement) {
    if (thumbnail.files != null) {
      const reader = new FileReader();
      reader.readAsDataURL(thumbnail.files[0]);
      reader.onload = () => {
        let thumbnail_file = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
        //Todo crééer les markers

        var srcImage = `markers_${this.resourceSelected?.name}`;

        //run NFT-Marker-Creator with path of the image in parameter
        runAPI(`markers_${this.resourceSelected?.id}`);

        //The 3 local output files are located in the "output" folder of the NFT-Marker-Creator folder
        var srcOutput = "../../../assets/NFT-Marker-Creator/output/";
        var imgIset = srcOutput+srcImage+".iset";
        var imgFset = srcOutput+srcImage+".fset";
        var imgFset3 = srcOutput+srcImage+".fset3";

        let bodyMarkers: { [key: string]: any } = {};
        bodyMarkers["name"] = `markers_${this.resourceSelected?.id}`;
        bodyMarkers["marker1"] = imgIset;
        bodyMarkers["marker2"] = imgFset;
        bodyMarkers["marker3"] = imgFset3;

        //bodyMarkers["marker1"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
        //bodyMarkers["marker2"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
        //bodyMarkers["marker3"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');

        this.http.put(this.SERVER_PATH + `/admin/projects/resources/${this.resourceSelected?.id}/thumbnail/`, {
          "name": `thumbnail_${this.resourceSelected?.id}`,
          "media": thumbnail_file
        }, {responseType: 'text'}).subscribe((res) => {
          if (this.showResponses) console.log(`/admin/projects/resources/${this.resourceSelected?.id}/thumbnail/`);
          if (this.showResponses) console.log(res);
          if (this.resourceSelected != null) {
            this.resourceSelected.thumbnail = thumbnail_file;
          }

        });

        this.http.put(this.SERVER_PATH + `/admin/projects/resources/${this.resourceSelected?.id}/markers/`, bodyMarkers, {responseType: 'text'}).subscribe((res) => {
          if (this.showResponses) console.log(`/admin/projects/resources/${this.resourceSelected?.id}/markers/`);
          if (this.showResponses) console.log(res);
        });

        //Delete the 3 local output files
        var fs = require('fs');
        fs.unlinkSync(imgIset);
        fs.unlinkSync(imgFset);
        fs.unlinkSync(imgFset3);
      };
    }

  }

  /**
   * @method uploadNewVideo(video: HTMLInputElement)
   * Uploads a new video url for the selected resource.
   * @param {HTMLInputElement} video - The video input element associated with the new resource.
   */
  uploadNewVideo(video: HTMLInputElement) {
    this.http.put(this.SERVER_PATH + `/admin/projects/resources/${this.resourceSelected?.id}/video/`, {
      "name": `video_${this.resourceSelected?.id}`,
      "media": video.value
    }, {responseType: 'text'}).subscribe((res) => {
      if (this.showResponses) console.log(`/admin/projects/resources/${this.resourceSelected?.id}/video/`);
      if (this.showResponses) console.log(res);
      if (this.resourceSelected != null) {
        this.resourceSelected.videoAsset = video.value;
        console.log(this.resourceSelected)
      }
    });
  }

  /**
   * @method uploadNewImage(image: HTMLInputElement)
   * Uploads a new image for the selected resource.
   * @param {HTMLInputElement} image - The image file associated with the new resource.
   */
  uploadNewImage(image: HTMLInputElement) {
    if (image.files != null) {
      const reader = new FileReader();
      reader.readAsDataURL(image.files[0]);
      reader.onload = () => {
        let image_file = (reader.result as string).replace('data:', '').replace(/^.+,/, '');

        this.http.put(this.SERVER_PATH + `/admin/projects/resources/${this.resourceSelected?.id}/image/`, {
          "name": `image_${this.resourceSelected?.id}`,
          "media": image_file
        }, {responseType: 'text'}).subscribe((res) => {
          if (this.showResponses) console.log(`/admin/projects/resources/${this.resourceSelected?.id}/image/`);
          if (this.showResponses) console.log(res);
          if (this.resourceSelected != null) {
            this.resourceSelected.imageAsset = image_file;
          }
        });
      };
    }
  }

  /**
   * @method uploadNewAudio(audio: HTMLInputElement)
   * Uploads a new audio file for the selected resource.
   * @param {HTMLInputElement} audio - The audio file associated with the new resource.
   */
  uploadNewAudio(audio: HTMLInputElement) {
    //TODO
    alert("not implemented uploadNewAudio");
  }

  /**
   * @method deleteProject()
   * Delete the project.
   */
  deleteProject() {
    this.http.delete(this.SERVER_PATH + `/public/projects/${this.project.getId()}/delete`, {responseType: 'text'}).subscribe((res: any) => {
      if (this.showResponses) console.log(`/public/projects/${this.project.getId()}/delete`);
      if (this.showResponses) console.log(res);
      this.updator.refresh();
      //Set the default project to indicate (select a project)
      this.project = this.defaultProject;
    });
  }

  /**
   * @method renameProject(newName: string)
   * @param {string} newName - The new name of the project.
   * Rename the project.
   */
  renameProject(newName: string) {
    this.http.put(this.SERVER_PATH + `/public/projects/${this.project.getId()}/rename`, {'new_name': newName}, {responseType: 'text'}).subscribe((res: any) => {
      if (this.showResponses) console.log(`/public/projects/${this.project.getId()}/rename`);
      if (this.showResponses) console.log(res);
      this.project.setName(newName);
      this.updator.refresh();
      this.hideRenameProject();
    });

  }

  /**
   * @method selectResource(resource: Resource)
   * Method called when a resource image is clicked. Allows you to open the edition of the latter.
   * @param {Resource} resource - The selected resource.
   */
  selectResource(resource: Resource) {
    //Select the resource
    this.resourceSelected = resource;
    //Get the data of the resource (video or (image an sound))
    this.getContentOfResource();
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

  /**
   * @method deleteResource()
   * Used to delete a resource.
   */
  deleteResource() {
    if (this.resourceSelected != null) {
      this.http.delete(this.SERVER_PATH + `/public/projects/${this.project.getId()}/resources/${this.resourceSelected.id}/delete`, {responseType: 'text'}).subscribe((res: any) => {
        if (this.showResponses) console.log(`/public/projects/${this.project.getId()}/resources/${this.resourceSelected?.id}/delete`);
        if (this.showResponses) console.log(res);
        this.resources = this.resources.filter(resource => resource.id !== this.resourceSelected?.id);
        this.filterList();
        this.exitEdition();
      });
    }
  }

  /**
   * @method renameResource()
   * Used to rename a resource.
   */
  renameResource() {
    this.http.put(this.SERVER_PATH + `/public/projects/resources/${this.resourceSelected?.id}/rename`, {'new_name': this.newName}, {responseType: 'text'}).subscribe((res: any) => {
      if (this.showResponses) console.log(`/public/projects/resources/${this.resourceSelected?.id}/rename`);
      if (this.showResponses) console.log(res);
      // @ts-ignore
      this.resourceSelected.name = this.newName;
      this.newName = '';
    });
  }

  /**
   * @method changeNewName(name: string | null)
   * @param {string | null} name - The potential new name of the selected resource.
   * To display the "Rename" button only when there is a modification in the name, you must store the new name in a variable (newName). This method allows you to modify it.
   */
  changeNewName(name: string | null) {
    if (name == null) {
      this.newName = '';
      return
    }
    this.newName = name == this.resourceSelected?.name ? "" : name;
  }

  /**
   * @method getContentOfResource()
   * Method used to obtain all the content of the selected resource to be able to edit it.
   * Recovery of image, sound or video.
   */
  getContentOfResource() {
    this.http.get<any>(this.SERVER_PATH + `/path/to/server/static/to/get/video/`).pipe(map((value: any) => {
      return value
    })).subscribe((res) => {
      if (this.showResponses) console.log("/path/to/server/static/to/get/video/");
      if (this.showResponses) console.log(res);
      //this.resourceSelected?.videoAsset = res.value;
      if (this.resourceSelected?.videoAsset != null) {
        //If it is not null then there is no image and sound
        return;
      }
    });

    this.http.get<any>(this.SERVER_PATH + `/path/to/server/static/to/get/sound/`).pipe(map((value: any) => {
      return value
    })).subscribe((res) => {
      if (this.showResponses) console.log("/path/to/server/static/to/get/sound/");
      if (this.showResponses) console.log(res);
      //this.resourceSelected?.audioAsset = res.value;
    });

    this.http.get<any>(this.SERVER_PATH + `/path/to/server/static/to/get/image/`).pipe(map((value: any) => {
      return value
    })).subscribe((res) => {
      if (this.showResponses) console.log("/path/to/server/static/to/get/image/");
      if (this.showResponses) console.log(res);
      //this.resourceSelected?.imageAsset = res.value;
    });
  }
}
