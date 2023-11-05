import {Component, ElementRef, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Entity} from "../commons/Entity";
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
 * This component takes care of displaying the list of entities and providing menus for editing/creating/deleting entities.
 */
export class ProjectEditorComponent {

  /**
   * @property {ElementRef | null} filterEntity - Reference to the HTML element for filtering entities.
   */
  @ViewChild('filterEntity') filterEntity: ElementRef | null = null;

  /**
   * @property {Entity} defaultProject - The default project when no project has been selected.
   */
  defaultProject = new Project(-1, "Selectionnez un projet ...", "JJ/MM/AA");

  /**
   @property {Project} project - The currently edited project (initialized to a project (id = -1) to display the default text: "Select a project...").
   */
  public project: Project = this.defaultProject;

  /**
   @property {Entity[]} entities - List of all entities in the project. This list is complete and unfiltered.
   */
  entities: Entity[] = [];

  /**
   * @property {Entity[]} entitiesFiltered - List of filtered entities. This list is filtered according to the search bar.
   */
  entitiesFiltered: Entity[] = [];

  /**
   * @property {Entity | null} entitySelected - The entity clicked by the player for use in the entity edit display.
   */
  entitySelected: Entity | null = null;

  /**
   * @property {boolean} videoMod - Check if we should display the video or image menu in an entity.
   */
  videoMod: boolean = true;

  /**
   * @property {boolean} saved - Allows you to know if the user has saved his changes.
   */
  saved: boolean = false;

  /**
   * @property {boolean} renameView - Show or not the menu to rename an entity.
   */
  renameView: boolean = false;

  /**
   * @property {boolean} renameView - Show or not the menu to edit an entity.
   */
  editEntityView: boolean = false;

  /**
   * @property {boolean} newEntityView - Show or not the menu to create an entity.
   */
  newEntityView: boolean = false;


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
   * Update the list of entities in the project.
   */
  updateProjectSelected() {
    this.entities = [];
    //TODO remplacer par le code qui récupère en faisant appel au serveur
    //TODO send request to server --> on sucess set the list --> on echec show popup error
    for (let i = 0; i < this.project.id; i++) {
      const entity = new Entity(1, "toto", `Entité ${i}`, "https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg", Math.trunc(Math.random() * 100), "https://player.vimeo.com/video/879891554?h=fba301cac0", Math.trunc(Math.random() * 100), null, Math.trunc(Math.random() * 100), "https://lasonotheque.org/UPLOAD/mp3/0001.mp3", Math.trunc(Math.random() * 100), 0, Math.trunc(Math.random() * 100), this.formatDate(new Date()));
      this.entities.push(entity);
    }
    this.entitiesFiltered = this.entities;
  }

  /**
   * @method filterList()
   * Filters the list of entities based on the value entered in the search bar.
   * If nothing is entered, the complete list is displayed.
   */
  filterList() {
    if (this.filterEntity != null) {
      let filter = this.filterEntity.nativeElement.value;
      this.entitiesFiltered = this.entities.filter(entity => entity.name.includes(filter));
    } else {
      this.entitiesFiltered = this.entities;
    }

  }

  /**
   * @method formatDate(date: Date): string
   * @param date - The date to format
   * Formats a date into a string in this way: DD/MM/YY.
   */
  formatDate(date: Date): string {
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Les mois commencent à 0, donc on ajoute 1
    const year = date.getFullYear().toString();
    return `${day}/${month}/${year}`;
  }

  /**
   * @method createNewEntity(name: String)
   * @param name - The name of the entity
   * Create a new entity and push to the server.
   */
  createNewEntity(name: String) {
    //TODO create the new entity and push into the server
    //TODO send request to server --> on sucess refresh the list --> on echec show popup error
    console.log('Le bouton de création de nouvelle entité a été cliqué');
    this.hideNewEntity();
  }

  /**
   * @method uploadNewTrackedPicture()
   * Uploads a new tracked picture for the selected entity.
   */
  uploadNewTrackedPicture() {
    //TODO Upload a tracked picture for this selected entity
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Upload new Tracked Picture");
    this.saved = false;
  }

  /**
   * @method deleteTrackedPicture()
   * Deletes the tracked picture of the selected entity.
   */
  deleteTrackedPicture() {
    //TODO Delete the tracked picture of this selected entity
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Delete TrackedPicture");
    this.saved = false;
  }

  /**
   * @method uploadNewPicture()
   * Uploads a new image for the selected entity.
   */
  uploadNewPicture() {
    //TODO Upload a picture for this selected entity
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Upload new Picture");
    this.saved = false;
  }

  /**
   * @method deletePicture()
   * Deletes the picture of the selected entity.
   */
  deletePicture() {
    //TODO Delete the picture of this selected entity
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Delete Picture");
    this.saved = false;
  }

  /**
   * @method uploadNewAudio()
   * Uploads a new audio file for the selected entity.
   */
  uploadNewAudio() {
    //TODO Upload a audio for this selected entity
    //TODO send request to server --> on sucess refresh --> on echec show popup error
    console.log("Upload new Audio");
    this.saved = false;
  }

  /**
   * @method deleteAudio()
   * Deletes the audio file of the selected entity.
   */
  deleteAudio() {
    //TODO Delete the audio of this selected entity
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
   * @method saveEntity()
   * Saves the entity by pushing it to the server.
   */
  saveEntity() {
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

  selectedEntity(entity: Entity) {
    this.entitySelected = entity;
    if (this.entitySelected.picture != null) {
      this.videoMod = false;
    }
    this.showEditEntity()
  }

  /**
   * @method exitEdition()
   * Exits edit mode.
   */
  exitEdition() {
    this.entitySelected = null;
    this.saved = false;
    this.hideEditEntity();
  }

  /**
   * @method showEditEntity()
   * Displays the entity edit mode.
   */
  showEditEntity() {
    this.editEntityView = true;
  }

  /**
   * @method hideEditEntity()
   * Hides the entity edit mode.
   */
  hideEditEntity() {
    this.editEntityView = false;
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
   * @method showNewEntity()
   * Displays the new entity mode.
   */
  showNewEntity() {
    this.newEntityView = true;
  }

  /**
   * @method hideNewEntity()
   * Hides the new entity mode.
   */
  hideNewEntity() {
    this.newEntityView = false;
  }
}
