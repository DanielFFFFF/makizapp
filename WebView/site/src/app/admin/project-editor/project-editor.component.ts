import {Component, ElementRef, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Resource} from "../../commons/Resource";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";
import {map} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {Created_id} from "../commons/Created_id";
import {AppConfigService} from "../../config/app.config.service";
import {QRCodeElementType, FixMeLater} from "angularx-qrcode"

/**
 * @enum Onglet
 * Enumeration representing the list of tabs for modifying a resource.
 */
enum Onglet {
  Thumbnail, Video, Sound, Image,
}

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
   * @property {boolean} sharePopup - Show or not the share popup.
   */
  sharePopup: boolean = false;

  /**
   */
   qrCodeElementType : QRCodeElementType = "canvas" as QRCodeElementType;

  /**
   * @property {boolean} showResponses
   * @private
   * Is used for show in console the response of all requests to server.
   */
  private showResponses: boolean = true;

  /**
   * @property {Onglet} ongletSelected - The selected tab.
   */
  ongletSelected: Onglet = Onglet.Thumbnail;

  /**
   * @property {Onglet} Onglet - Variable containing the enumeration. This allows it to be used within the class.
   */
  Onglet = Onglet;

  /**
   * @property {string} newName - When editing a resource, this variable allows you to modify the name later.
   * @private
   */
  newName: string = "";

  /**
   * @property {string} qrData
   */
  public qrData: string = '';

  confirmDelete(): void {
    if(confirm("Are you sure wo want to delete this resource?")) {
      this.deleteResource()
    }
  }


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

    this.http.get<any>(`${this.SERVER_PATH}/public/projects/${this.project.getId()}/resources`).pipe(map((value: Resource[]) => {
      return value
    })).subscribe((res: Resource[]) => {
      if (this.showResponses) console.log("/public/projects/resources/${id}/");
      if (this.showResponses) console.log(res);
      this.resources = res;
      this.filterList();

      this.resources.map(resource => {
        // For each resource, we send a request to the server to get the thumbnail

        this.http.get(`${this.SERVER_PATH}/resources/IMAGE/${resource.id}`, {responseType: 'blob'})
          .subscribe((res) => {
            let reader = new FileReader();
            reader.addEventListener("loadend", () => {
              if (this.showResponses) console.log(this.SERVER_PATH + `/resources/IMAGE/${resource.id}`);
              if (this.showResponses) console.log(res);
              resource.thumbnail = (reader.result as string);
            });
            if (res) {
              reader.readAsDataURL(res);
            }
          });

      });
    });
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
    createNewResource(name: HTMLInputElement, thumbnail: HTMLInputElement,
                      video: HTMLInputElement, sound: HTMLInputElement, image: HTMLInputElement) {
        const body: { [key: string]: any } = {};
        body["name"] = name.value;

        if (name.value === "") {
            alert("Il manque le nom de la ressource !");
            return;
        }

        const isImagePresent = image.files != null && image.files.length > 0;
        const isSoundPresent = sound.files != null && sound.files.length > 0;
        const isVideoPresent = video.value !== "";

        if ((isImagePresent || isSoundPresent) === isVideoPresent) {
            alert("Impossible d'avoir du son ou une image en meme temps qu'une video");
            return;
        }

        if (isVideoPresent) {
            body["videoAsset"] = video.value;
        } else if (!isSoundPresent && !isImagePresent) {
            alert("Aucun media à jouer en AR choisit");
            return;
        }

        const promises = [];

        const createThumbnailFromVideo = (videoUrl: string): Promise<string> => {
            return new Promise((resolve, reject) => {
                const videoElement = document.createElement("video");
                const canvas = document.createElement("canvas");
                const context = canvas.getContext("2d");

                videoElement.src = videoUrl;
                videoElement.crossOrigin = "anonymous";
                videoElement.addEventListener("loadeddata", () => {
                    canvas.width = videoElement.videoWidth;
                    canvas.height = videoElement.videoHeight;
                    videoElement.currentTime = 0; // Seek to the first frame
                });

                videoElement.addEventListener("seeked", () => {
                    context?.drawImage(videoElement, 0, 0, canvas.width, canvas.height);
                    const thumbnailBase64 = canvas.toDataURL("image/png").replace('data:', '').replace(/^.+,/, '');
                    resolve(thumbnailBase64);
                });

                videoElement.addEventListener("error", (err) => {
                    reject(`Error loading video: ${err}`);
                });
            });
        };

        if (thumbnail.files != null && thumbnail.files.length !== 0) {
            const reader = new FileReader();
            const p = new Promise<void>((resolve) => {
                reader.onload = () => {
                    body["thumbnail"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
                    resolve();
                };
            });
            reader.readAsDataURL(thumbnail.files[0]);
            promises.push(p);
        } else if (isVideoPresent) {
            const videoUrl = video.value;
            const p = createThumbnailFromVideo(videoUrl).then((thumbnailBase64) => {
                body["thumbnail"] = thumbnailBase64;
            });
            promises.push(p);
        } else {
            alert("Il manque l'image à capturer !");
            return;
        }

        if (image.files != null && image.files.length !== 0) {
            const reader = new FileReader();
            const p = new Promise<void>((resolve) => {
                reader.onload = () => {
                    body["imageAsset"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
                    resolve();
                };
            });
            reader.readAsDataURL(image.files[0]);
            promises.push(p);
        }

        if (sound.files != null && sound.files.length !== 0) {
            const reader = new FileReader();
            const p = new Promise<void>((resolve) => {
                reader.onload = () => {
                    body["soundAsset"] = (reader.result as string).replace('data:', '').replace(/^.+,/, '');
                    resolve();
                };
            });
            reader.readAsDataURL(sound.files[0]);
            promises.push(p);
        }

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
    if (thumbnail.files != null && thumbnail.files.length > 0) {
      const reader = new FileReader();
      reader.readAsDataURL(thumbnail.files[0]);
      reader.onload = () => {
        let thumbnail_file = (reader.result as string).replace('data:', '').replace(/^.+,/, '');

        this.http.put(this.SERVER_PATH + `/admin/projects/resources/${this.resourceSelected?.id}/thumbnail/`, {
          "name": `thumbnail_${this.resourceSelected?.id}`,
          "media": thumbnail_file
        }, { responseType: 'text' }).subscribe((res) => {
          if (this.showResponses) console.log(`/admin/projects/resources/${this.resourceSelected?.id}/thumbnail/`);
          if (this.showResponses) console.log(res);
          if (this.resourceSelected != null) {
            this.resourceSelected.thumbnail = thumbnail_file;
          }
        });
      };
    } else {
      alert("Il manque l'image à capturer !");
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
    this.http.delete(this.SERVER_PATH + `/admin/projects/${this.project.getId()}/delete`, {responseType: 'text'}).subscribe((res: any) => {
      if (this.showResponses) console.log(`/admin/projects/${this.project.getId()}/delete`);
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
    this.http.put(this.SERVER_PATH + `/admin/projects/${this.project.getId()}/rename`, {'new_name': newName}, {responseType: 'text'}).subscribe((res: any) => {
      if (this.showResponses) console.log(`/admin/projects/${this.project.getId()}/rename`);
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
    this.getContentOfResource(resource);
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

  //----------------------------------------- Share Popup -----------------------------------------

  /**
   * @method showSharePopup()
   * Displays the share popup.
   */
  showSharePopup() {
        this.qrData = `${this.SERVER_PATH}/#/${this.project.getId()}`;
        this.sharePopup = true;
  }

  /**
   * @method hideSharePopup()
   * Hides the share popup.
   */
  hideSharePopup() {
      this.sharePopup = false;
  }

  /**
   * @method getShareUrl
   * Generates a shareable URL and attempts to copy it to the clipboard.
   */
  getShareUrl(): void {
      const url = `${this.SERVER_PATH}/#/${this.project.getId()}`;

      if (navigator.clipboard && navigator.clipboard.writeText) {
          navigator.clipboard.writeText(url)
              .then(() => {
                  alert(`Link has been copied to the clipboard`);
              })
              .catch((error) => {
                  console.error('Failed to copy to clipboard:', error);
                  alert(`Cannot copy to clipboard. Share link is ${url}`);
              });
      } else {
          this.fallbackCopyToClipboard(url);
      }
  }

  /**
   * @method fallbackCopyToClipboard
   * Copies the given text to the clipboard using a fallback method for unsupported browsers.
   * @param {string} text - The text to copy to the clipboard.
   */
  fallbackCopyToClipboard(text: string): void {
      const textarea = document.createElement('textarea');
      textarea.value = text;
      textarea.style.position = 'fixed'; // Prevent scrolling to bottom of the page
      textarea.style.opacity = '0'; // Make it invisible
      document.body.appendChild(textarea);
      textarea.select();

      try {
          const successful = document.execCommand('copy');
          const message = successful ? 'successful' : 'unsuccessful';
          alert(`Link ${text} has been copied to clipboard (copy command was ${message}).`);
      } catch (error) {
          console.error('Unable to copy using fallback method:', error);
          alert(`Cannot copy to clipboard. Share link is ${text}`);
      } finally {
          document.body.removeChild(textarea); // Clean up the temporary element
      }
  }

  /**
   * @method saveAsImage
   * Download QR Code currently displayed
   * @param {FixMeLater} parent - QR Code to download
   */
  saveAsImage(parent: FixMeLater) {
    let parentElement = null

    if (this.qrCodeElementType === "canvas") {
      // fetches base 64 data from canvas
      parentElement = parent.qrcElement.nativeElement
        .querySelector("canvas")
        .toDataURL("image/png")
    } else if (this.qrCodeElementType === "img" || this.qrCodeElementType === "url") {
      // fetches base 64 data from image
      parentElement = parent.qrcElement.nativeElement.querySelector("img").src
    } else {
      alert("Set elementType to 'canvas', 'img' or 'url'.")
    }

    if (parentElement) {
      let blobData = this.convertBase64ToBlob(parentElement) // converts base 64 encoded image to blobData
      const blob = new Blob([blobData], { type: "image/png" }) // saves as image
      const url = window.URL.createObjectURL(blob)
      const link = document.createElement("a")
      link.href = url
      link.download = "qrcode" // name of the file
      link.click()
    }
  }

  /**
   * @method convertBase64ToBlob
   * Convert base 64 to blob
   * @param {string} Base64Image - image to convert
   */
  private convertBase64ToBlob(Base64Image: string) {
    // split into two parts
    const parts = Base64Image.split(";base64,")
    // hold the content type
    const imageType = parts[0].split(":")[1]
    // decode base64 string
    const decodedData = window.atob(parts[1])
    // create unit8array of size same as row data length
    const uInt8Array = new Uint8Array(decodedData.length)
    // insert all character code into uint8array
    for (let i = 0; i < decodedData.length; ++i) {
      uInt8Array[i] = decodedData.charCodeAt(i)
    }
    // return blob image after conversion
    return new Blob([uInt8Array], { type: imageType })
  }

  //----------------------------------------- Ressource -----------------------------------------

  /**
   * @method deleteResource()
   * Used to delete a resource.
   */
  deleteResource() {
    if (this.resourceSelected != null) {
      this.http.delete(this.SERVER_PATH + `/admin/projects/${this.project.getId()}/resources/${this.resourceSelected.id}/delete`, {responseType: 'text'}).subscribe((res: any) => {
        if (this.showResponses) console.log(`/admin/projects/${this.project.getId()}/resources/${this.resourceSelected?.id}/delete`);
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
    this.http.put(this.SERVER_PATH + `/admin/projects/resources/${this.resourceSelected?.id}/rename`, {'new_name': this.newName}, {responseType: 'text'}).subscribe((res: any) => {
      if (this.showResponses) console.log(`/admin/projects/resources/${this.resourceSelected?.id}/rename`);
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
  getContentOfResource(resource:Resource) {
    console.log(resource);

    if (resource.videoAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/public/video/${resource.videoAssetId}`, {responseType: 'text'})
        .subscribe((res: any) => {
          console.log(res);
          resource.videoAsset = res;
        });
    }

    if (resource.soundAssetId != null) {
    this.http.get(`${this.SERVER_PATH}/resources/SOUND/${resource.soundAssetId}`, {responseType: 'blob'})
      .subscribe((res) => {
        resource.soundAsset = URL.createObjectURL(res);
      });
    }

    if (resource.imageAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/resources/IMAGE/${resource.imageAssetId}`, {responseType: 'blob'})
        .subscribe((res) => {
          let reader = new FileReader();
          reader.addEventListener("loadend", () => {
            if (this.showResponses) console.log(this.SERVER_PATH + `/resources/IMAGE/${resource.imageAssetId}`);
            if (this.showResponses) console.log(res);
            resource.imageAsset = (reader.result as string);
          });
          if (res) {
            reader.readAsDataURL(res);
          }
        });
    }
  }

}
