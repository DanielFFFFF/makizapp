import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {Project} from "../commons/Project";
import {Entity} from "../commons/Entity";
import {DataService} from "../commons/DataService";

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
   * @property {ElementRef | null} filterEntity - Référence à l'élément HTML pour filtrer les entités.
   * @property {Project} project - Le projet actuellement édité (initialisé à un projet (id = -1) pour afficher le texte par défaut : "Selectionnez un projet...".
   * @property {Entity[]} entities - Liste de toutes les entités du projet. Cette liste est complête et non filtrée.
   * @property {Entity[]} entitiesFiltered - Liste des entités filtrées. Cette liste est filtrée selon la barre de recherche.
   * @property {Entity | null} entitySelected - L'entité actuellement sélectionnée pour l'édition. Elle peut être null, dans ce cas le menu d'édition d'entité n'est pas affiché.
   * @property {boolean} videoMod - Indique si le mode vidéo est activé. Cette variable sert lorsque le mode d'édition d'entité est activé, comme l'utilisateur peut choisir soit une vidéo, soit une image, le bloc d'édition change selon cette variable.
   * @property {boolean} saved - Indique si les modifications ont été enregistrées. Permet d'avoir un visuel (boutton rouge) lorsqu'on quitte le mode d'édition.
   */
  @ViewChild('filterEntity') filterEntity: ElementRef | null = null;
  public project: Project = new Project(-1, "Selectionnez un projet ...");
  entities : Entity[] = [];
  entitiesFiltered : Entity[] = [];
  entitySelected : Entity | null = null;
  videoMod : boolean = true;
  saved : boolean = false;


  constructor(private projectService: DataService) {}

  /**
   * @method ngOnInit()
   * Méthode d'initialisation du composant.
   * Elle est utilisée pour communiquer avec le service DataService
   * permettant de récupérer le projet selectionné dans la liste des projets.
   */
  ngOnInit() {
      this.projectService.project$.subscribe(project => {
          this.project = project;
          this.updateProjectSelected()
      });
  }

  /**
   * @method updateProjectSelected()
   * Met à jour le projet selectionné.
   * Etape 1 : Mise à jour de la liste des entités du projet.
   * Etape 2 : Mise à jour des informations du projet : (accessCount, date de création)
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
   * Filtre la liste des entités en fonction de la valeur saisie dans la barre de recherche.
   * Si rien n'a été saisi, alors c'est la liste complête qui est affichée.
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
   * Formate une date en chaîne de caractères de cette façon : DD/MM/YY.
   */
  formatDate(date: Date):string{
    const day = date.getDate().toString().padStart(2, '0');
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Les mois commencent à 0, donc on ajoute 1
    const year = date.getFullYear().toString();
    return `${day}/${month}/${year}`;
  }

  /**
   * @method switchEntityDisplayed(entity : Entity)
   * Change l'entité actuellement affichée pour l'édition.
   * Selectionne également le mode de l'entité (Vidéo ou image)
   */
  switchEntityDisplayed(entity : Entity){
    this.entitySelected = entity;
    if(this.entitySelected.picture!=null){
      this.videoMod = false;
    }
  }

  /**
   * @method createNewEntity()
   * Crée une nouvelle entité et l'envoie au serveur.
   */
  createNewEntity(){
    //TODO create the new entity and push into the server
    console.log('Le bouton de création de nouvelle entité a été cliqué');
  }

  /**
   * @method uploadNewTrackedPicture()
   * Télécharge une nouvelle image suivie pour l'entité sélectionnée.
   */
  uploadNewTrackedPicture(){
    //TODO Upload a tracked picture for this selected entity
    console.log("Upload new Tracked Picture");
    this.saved = false;
  }

  /**
   * @method deleteTrackedPicture()
   * Supprime l'image suivie de l'entité sélectionnée.
   */
  deleteTrackedPicture(){
    //TODO Delete the tracked picture of this selected entity
    console.log("Delete TrackedPicture");
    this.saved = false;
  }
  /**
   * @method uploadNewPicture()
   * Télécharge une nouvelle image pour l'entité sélectionnée.
   */
  uploadNewPicture(){
    //TODO Upload a picture for this selected entity
    console.log("Upload new Picture");
    this.saved = false;
  }

  /**
   * @method uploadNewPicture()
   * Télécharge une nouvelle image pour l'entité sélectionnée.
   */
  deletePicture(){
    //TODO Delete the picture of this selected entity
    console.log("Delete Picture");
    this.saved = false;
  }

  /**
   * @method deletePicture()
   * Supprime l'image de l'entité sélectionnée.
   */
  uploadNewAudio(){
    //TODO Upload a audio for this selected entity
    console.log("Upload new Audio");
    this.saved = false;
  }

  /**
   * @method deleteAudio()
   * Supprime le fichier audio de l'entité sélectionnée.
   */
  deleteAudio(){
    //TODO Delete the audio of this selected entity
    console.log("Delete Audio");
    this.saved = false;
  }

  /**
   * @method reloadMarker()
   * Recharge le marqueur.
   */
  reloadMarker(){
    //TODO reload the marker
    console.log("Marker reloaded");
  }

  /**
   * @method saveEntity()
   * Enregistre l'entité en la poussant sur le serveur.
   */
  saveEntity(){
    //TODO Save the entity by push into the server
    this.saved = true;
  }

  /**
   * @method exitEdition()
   * Quitte le mode d'édition.
   */
  exitEdition(){
    this.entitySelected = null;
    this.saved = false;
  }
}
