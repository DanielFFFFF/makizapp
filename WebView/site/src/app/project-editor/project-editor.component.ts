import {Component, ElementRef, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Ressource} from "../commons/Ressource";
import {ProjectSelectorService} from "../commons/ProjectSelector.service";
import {UpdatorService} from "../commons/Updator.service";

@Component({
  selector: 'project-editor',
  templateUrl: './project-editor.component.html',
  styleUrls: ['./project-editor.component.css']
})
/**
 * @class ProjectEditorComponent
 *
 * This class is an Angular component that allows you to edit a project.
 * This component takes care of displaying the list of ressources and providing menus for editing/creating/deleting ressources.
 */
export class ProjectEditorComponent {

  /**
   * @property {ElementRef | null} filterRessource - Reference to the HTML element for filtering ressources.
   */
  @ViewChild('filterRessource') filterRessource: ElementRef | null = null;

  /**
   * @property {Ressource} defaultProject - The default project when no project has been selected.
   */
  defaultProject = new Project(-1, "Selectionnez un projet ...", "JJ/MM/AA");

  /**
   @property {Project} project - The currently edited project (initialized to a project (id = -1) to display the default text: "Select a project...").
   */
  public project: Project = this.defaultProject;

  /**
   @property {Ressource[]} ressources - List of all ressources in the project. This list is complete and unfiltered.
   */
  ressources: Ressource[] = [];

  /**
   * @property {Ressource[]} ressourcesFiltered - List of filtered ressources. This list is filtered according to the search bar.
   */
  ressourcesFiltered: Ressource[] = [];

  /**
   * @property {Ressource | null} ressourceSelected - The ressource clicked by the player for use in the ressource edit display.
   */
  ressourceSelected: Ressource | null = null;

  /**
   * @property {boolean} videoMod - Check if we should display the video or image menu in an ressource.
   */
  videoMod: boolean = true;

  /**
   * @property {boolean} saved - Allows you to know if the user has saved his changes.
   */
  saved: boolean = false;

  /**
   * @property {boolean} renameView - Show or not the menu to rename an ressource.
   */
  renameView: boolean = false;

  /**
   * @property {boolean} renameView - Show or not the menu to edit an ressource.
   */
  editRessourceView: boolean = false;

  /**
   * @property {boolean} newRessourceView - Show or not the menu to create an ressource.
   */
  newRessourceView: boolean = false;


  constructor(private projectSelected: ProjectSelectorService, private updator: UpdatorService) {
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
   * Update the list of ressources in the project.
   */
  updateProjectSelected() {
    this.ressources = [];
    //TODO remplacer par le code qui récupère en faisant appel au serveur
    //TODO send request to server --> on sucess set the list --> on echec show popup error
    for (let i = 0; i < this.project.id; i++) {
      const ressource = new Ressource(1, "toto", `Entité ${i}`, "https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg", Math.trunc(Math.random() * 100), "https://player.vimeo.com/video/879891554?h=fba301cac0", Math.trunc(Math.random() * 100), null, Math.trunc(Math.random() * 100), "https://lasonotheque.org/UPLOAD/mp3/0001.mp3", Math.trunc(Math.random() * 100), 0, Math.trunc(Math.random() * 100), this.formatDate(new Date()));
      this.ressources.push(ressource);
    }
    this.ressourcesFiltered = this.ressources;
  }

  /**
   * @method filterList()
   * Filters the list of ressources based on the value entered in the search bar.
   * If nothing is entered, the complete list is displayed.
   */
  filterList() {
    if (this.filterRessource != null) {
      let filter = this.filterRessource.nativeElement.value;
      this.ressourcesFiltered = this.ressources.filter(ressource => ressource.name.includes(filter));
    } else {
      this.ressourcesFiltered = this.ressources;
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
   * @method createNewRessource(name: String)
   * @param {string} name - The name of the ressource
   * Create a new ressource and push to the server.
   */
  createNewRessource(name: String) {
    //TODO create the new ressource and push into the server
    //TODO send request to server --> on sucess refresh the list --> on echec show popup error
    console.log('Le bouton de création de nouvelle entité a été cliqué');
    this.hideNewRessource();
  }

  /**
   * @method uploadNewTrackedImage()
   * Uploads a new tracked image for the selected ressource.
   */
  uploadNewTrackedImage() {
    //TODO Upload a tracked image for this selected ressource
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Upload new Tracked Image");
    this.saved = false;
  }

  /**
   * @method deleteTrackedImage()
   * Deletes the tracked image of the selected ressource.
   */
  deleteTrackedImage() {
    //TODO Delete the tracked image of this selected ressource
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Delete TrackedImage");
    this.saved = false;
  }

  /**
   * @method uploadNewImage()
   * Uploads a new image for the selected ressource.
   */
  uploadNewImage() {
    //TODO Upload a image for this selected ressource
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Upload new Image");
    this.saved = false;
  }

  /**
   * @method deleteImage()
   * Deletes the image of the selected ressource.
   */
  deleteImage() {
    //TODO Delete the image of this selected ressource
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Delete Image");
    this.saved = false;
  }

  /**
   * @method uploadNewAudio()
   * Uploads a new audio file for the selected ressource.
   */
  uploadNewAudio() {
    //TODO Upload a audio for this selected ressource
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Upload new Audio");
    this.saved = false;
  }

  /**
   * @method deleteAudio()
   * Deletes the audio file of the selected ressource.
   */
  deleteAudio() {
    //TODO Delete the audio of this selected ressource
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Delete Audio");
    this.saved = false;
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
   * @method saveRessource()
   * Saves the ressource by pushing it to the server.
   */
  saveRessource() {
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    this.saved = true;
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

  selectedRessource(ressource: Ressource) {
    this.ressourceSelected = ressource;
    if (this.ressourceSelected.image != null) {
      this.videoMod = false;
    }
    this.showEditRessource()
  }

  /**
   * @method exitEdition()
   * Exits edit mode.
   */
  exitEdition() {
    this.ressourceSelected = null;
    this.saved = false;
    this.hideEditRessource();
  }

  /**
   * @method showEditRessource()
   * Displays the ressource edit mode.
   */
  showEditRessource() {
    this.editRessourceView = true;
  }

  /**
   * @method hideEditRessource()
   * Hides the ressource edit mode.
   */
  hideEditRessource() {
    this.editRessourceView = false;
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
   * @method showNewRessource()
   * Displays the new ressource mode.
   */
  showNewRessource() {
    this.newRessourceView = true;
  }

  /**
   * @method hideNewRessource()
   * Hides the new ressource mode.
   */
  hideNewRessource() {
    this.newRessourceView = false;
  }
}
