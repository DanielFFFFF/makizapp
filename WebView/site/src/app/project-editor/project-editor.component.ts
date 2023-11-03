import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Entity} from "../commons/Entity";
import {ProjectSelector} from "../commons/ProjectSelector";
import {ProjectRemover} from "../commons/ProjectRemover";

@Component({
  selector: 'project-editor',
  templateUrl: './project-editor.component.html',
  styleUrls: ['./project-editor.component.css']
})
/**
 * @class ProjectEditorComponent
 *
 * Cette classe est un composant Angular qui permet d'éditer des projets.
 */
export class ProjectEditorComponent {
  /**
   * @property {ElementRef | null} filterEntity - Reference to the HTML element for filtering entities.
   * @property {Project} project - The currently edited project (initialized to a project (id = -1) to display the default text: "Select a project...").
   * @property {Entity[]} entities - List of all entities in the project. This list is complete and unfiltered.
   * @property {Entity[]} entitiesFiltered - List of filtered entities. This list is filtered according to the search bar.
   * @property {Entity | null} entitySelected - The currently selected entity for editing. It can be null; in that case, the entity edit menu is not displayed.
   * @property {boolean} videoMod - Indicates if the video mode is enabled. This variable is used when the entity edit mode is active, as the user can choose either a video or an image; the edit block changes according to this variable.
   * @property {boolean} saved - Indicates whether changes have been saved. It provides a visual (red button) when leaving edit mode.
   */
  @ViewChild('filterEntity') filterEntity: ElementRef | null = null;
  defaultProject = new Project(-1, "Selectionnez un projet ...");
  public project: Project = this.defaultProject;
  entities : Entity[] = [];
  entitiesFiltered : Entity[] = [];
  entitySelected : Entity | null = null;
  videoMod : boolean = true;
  saved : boolean = false;


  constructor(private projectSelected: ProjectSelector, private projectToDelete: ProjectRemover) {}

  /**
   * @method ngOnInit()
   * Component initialization method.
   * It is used to communicate with the ProjectSelector to retrieve
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
   * Step 1: Update the list of entities in the project.
   * Step 2: Update the project information: (accessCount, creation date)
   */
  updateProjectSelected() {
    this.entities = [];
    //TODO remplacer par le code qui récupère en faisant appel au serveur
    for (let i = 0; i < this.project.id; i++) {
        const entity = new Entity(1, "toto",`Entité ${i}`,"https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg",Math.trunc(Math.random()*100),"https://player.vimeo.com/video/879891554?h=fba301cac0",Math.trunc(Math.random()*100),null,Math.trunc(Math.random()*100),"https://lasonotheque.org/UPLOAD/mp3/0001.mp3",Math.trunc(Math.random()*100),0,Math.trunc(Math.random()*100),this.formatDate(new Date()));
        this.entities.push(entity);
    }
    this.entitiesFiltered = this.entities;
  }

  /**
   * @method filterList()
   * Filters the list of entities based on the value entered in the search bar.
   * If nothing is entered, the complete list is displayed.
   */
  filterList(){
    if(this.filterEntity != null){
      let filter = this.filterEntity.nativeElement.value;
      this.entitiesFiltered = this.entities.filter(entity => entity.name.includes(filter));
    } else {
      this.entitiesFiltered = this.entities;
    }

  }

  /**
   * @method formatDate(date: Date): string
   * Formats a date into a string in this way: DD/MM/YY.
   */
  formatDate(date: Date):string{
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Les mois commencent à 0, donc on ajoute 1
    const year = date.getFullYear().toString();
    return `${day}/${month}/${year}`;
  }

  /**
   * @method switchEntityDisplayed(entity: Entity)
   * Changes the currently displayed entity for editing.
   * Also selects the entity mode (Video or image).
   */
  switchEntityDisplayed(entity : Entity){
    this.entitySelected = entity;
    if(this.entitySelected.picture!=null){
      this.videoMod = false;
    }
  }

  /**
   * @method createNewEntity()
   * Creates a new entity and sends it to the server.
   */
  createNewEntity(){
    //TODO create the new entity and push into the server
    console.log('Le bouton de création de nouvelle entité a été cliqué');
  }

  /**
   * @method uploadNewTrackedPicture()
   * Uploads a new tracked picture for the selected entity.
   */
  uploadNewTrackedPicture(){
    //TODO Upload a tracked picture for this selected entity
    console.log("Upload new Tracked Picture");
    this.saved = false;
  }

  /**
   * @method deleteTrackedPicture()
   * Deletes the tracked picture of the selected entity.
   */
  deleteTrackedPicture(){
    //TODO Delete the tracked picture of this selected entity
    console.log("Delete TrackedPicture");
    this.saved = false;
  }
  /**
   * @method uploadNewPicture()
   * Uploads a new image for the selected entity.
   */
  uploadNewPicture(){
    //TODO Upload a picture for this selected entity
    console.log("Upload new Picture");
    this.saved = false;
  }

  /**
   * @method deletePicture()
   * Deletes the picture of the selected entity.
   */
  deletePicture(){
    //TODO Delete the picture of this selected entity
    console.log("Delete Picture");
    this.saved = false;
  }

  /**
   * @method uploadNewAudio()
   * Uploads a new audio file for the selected entity.
   */
  uploadNewAudio(){
    //TODO Upload a audio for this selected entity
    console.log("Upload new Audio");
    this.saved = false;
  }

  /**
   * @method deleteAudio()
   * Deletes the audio file of the selected entity.
   */
  deleteAudio(){
    //TODO Delete the audio of this selected entity
    console.log("Delete Audio");
    this.saved = false;
  }

  /**
   * @method reloadMarker()
   * Reloads the marker.
   */
  reloadMarker(){
    //TODO reload the marker
    console.log("Marker reloaded");
  }

  /**
   * @method saveEntity()
   * Saves the entity by pushing it to the server.
   */
  saveEntity(){
    //TODO Save the entity by push into the server
    this.saved = true;
  }

  /**
   * @method exitEdition()
   * Exits edit mode.
   */
  exitEdition(){
    this.entitySelected = null;
    this.saved = false;
  }

  /**
   * @method deleteProject()
   * Delete the project.
   */
  deleteProject(){
    console.log("Delete project required");
    this.projectToDelete.setProject(this.project);
    this.project = this.defaultProject;
  }
}
