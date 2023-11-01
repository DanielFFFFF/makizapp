import {Component, OnInit} from '@angular/core';
import {Project} from "../commons/Project";
import {Entity} from "../commons/Entity";
import {DataService} from "../commons/DataService";

@Component({
  selector: 'project-editor',
  templateUrl: './project-editor.component.html',
  styleUrls: ['./project-editor.component.css']
})
export class ProjectEditorComponent implements OnInit {

  public project: Project = new Project(-1, "Selectionnez un projet ...");

  entities : Entity[] = [];

  //entitySelected : Entity | null = null;
  entitySelected = new Entity(1,`Entité ${1}`, "https://player.vimeo.com/video/879891554?h=fba301cac0",1024,"https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg",512,"https://lasonotheque.org/UPLOAD/mp3/0001.mp3",768,0,10,this.formatDate(new Date()),Math.trunc(Math.random()*100),"toto");

  modified: boolean = false;
  constructor(private projectService: DataService) {}

  ngOnInit() {
      this.projectService.project$.subscribe(project => {
          this.project = project;
          this.updateEntityList()
      });
  }

  updateEntityList() {
    this.entities = [];
    for (let i = 0; i < this.project.id; i++) {
        const entity = new Entity(1,`Entité ${i+1}`, "video.mp4",1024,"https://cdn.pixabay.com/photo/2023/10/14/23/27/airplane-8315886_1280.jpg",512,"https://lasonotheque.org/UPLOAD/mp3/0001.mp3",768,0,10,this.formatDate(new Date()),Math.trunc(Math.random()*100),"toto");
        this.entities.push(entity);
    }
  }

  createNewEntity(){
      console.log('Le bouton de création de nouvelle entité a été cliqué');
  }

  formatDate(date: Date):string{
      const day = date.getDate().toString().padStart(2, '0');
      const month = (date.getMonth() + 1).toString().padStart(2, '0'); // Les mois commencent à 0, donc on ajoute 1
      const year = date.getFullYear().toString();
      return `${day}/${month}/${year}`;
  }

  uploadNewPicture(){
    this.setModified();
    //Upload a picture for this selected entity
    console.log("Upload new Picture");
  }

  deletePicture(){
    this.setModified();
    //Delete the picture of this selected entity
    console.log("Delete Picture");
  }

  uploadNewAudio(){
    this.setModified();
    //Upload a audio for this selected entity
    console.log("Upload new Audio");
  }

  deleteAudio(){
    this.setModified();
    //Delete the audio of this selected entity
    console.log("Delete Audio");
  }
  saveEntity(){

  }

  setModified(){
    this.modified =true;
  }
}
