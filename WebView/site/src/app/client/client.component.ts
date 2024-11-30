import {Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2} from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { catchError, map, switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { Resource } from "../commons/Resource";
import { AppConfigService } from "../config/app.config.service";
import { ARService } from './ARService';

@Component({
  selector: 'app-user',
  styleUrls: ['./client.component.css'],
  templateUrl: './client.component.html'
})

export class ClientComponent {
  @ViewChild('video') video!: ElementRef<HTMLVideoElement>;
  @ViewChild('canvas') canvas!: ElementRef<HTMLCanvasElement>;
  private stream: MediaStream | null = null;
  videoUrl: string | null = null;

  SERVER_PATH: string = "";
  resources: Resource[] = [];
  projectId: string | null = null;
  @ViewChild('targetContainer', { static: false }) targetContainer!: ElementRef;
  sceneLoaded = false;
  constructor(private route: ActivatedRoute, private http:HttpClient, private config: AppConfigService, private router: Router, private elRef: ElementRef, private renderer: Renderer2, private arService: ARService) {
  }


  ngOnInit(): void {
    // Ensure scripts are loaded dynamically before initialization
    //
    this.projectId = this.route.snapshot.paramMap.get("projectId");

    //
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];

    });

    setTimeout(() => {
      this.sceneLoaded = true;
    }, 500); // 3000ms = 3 seconds



    // Wait for the scene to be rendered before moving it

    console.log('ClientComponent ngOnInit called');
  }


  ngAfterViewInit(): void {
    this.projectId = this.route.snapshot.paramMap.get("projectId");
    this.arService.initializeARScene(this.projectId);
  }




  checkProjectExists(){
    return this.http.get<boolean>(`${this.SERVER_PATH}/projects/${this.projectId}/exist`)
      .pipe(
        catchError(error => {
          console.error("Erreur lors de la vérification de l'éxistence du projet", error);
          return of([]);
      }));
  }

  getResources(){
      this.http.get<any>( `${this.SERVER_PATH}/public/projects/${this.projectId}/resources`).pipe(
        map((value: Resource[]) => {
          return value
        }
      )).subscribe((res: Resource[]) => {
        console.log(res);
        this.resources = res;

        this.resources.map(resource => {
          this.getContentOfResource(resource);
        });
      });
  }

  getContentOfResource(resource: Resource) {
    if (resource.videoAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/public/video/${resource.videoAssetId}`, { responseType: 'text' })
        .subscribe((res: any) => {
          resource.videoAsset = res;
        });
    }

    if (resource.soundAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/resources/SOUND/${resource.soundAssetId}`, { responseType: 'blob' })
        .subscribe((res) => {
          resource.soundAsset = URL.createObjectURL(res);
        });
    }

    if (resource.imageAssetId != null) {
      this.http.get(`${this.SERVER_PATH}/resources/IMAGE/${resource.imageAssetId}`, { responseType: 'blob' })
        .subscribe((res) => {
          let reader = new FileReader();
          reader.addEventListener("loadend", () => {
            resource.imageAsset = (reader.result as string);
          });
          if (res) {
            reader.readAsDataURL(res);
          }
        });
    }
  }

  // Starts the camera if compatible and permissions are granted
  async startCamera() {
    // Check for browser compatibility
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
      console.error("Camera access is not supported in this browser.");
      //alert("Your browser does not support camera access. Please use a modern browser like Chrome, Firefox, or Edge.");
      return;
    }

    try {
      // Request access to the camera
      this.stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } });
      this.video.nativeElement.srcObject = this.stream;
    } catch (error) {
      // Type guard to check if `error` has a `name` property
      if (error instanceof DOMException) {
        if (error.name === "NotAllowedError") {
          console.error("Camera access was denied. Please allow camera permissions to use this feature.");
        } else if (error.name === "NotFoundError") {
          console.error("No camera found on this device.");
        } else {
          console.error("An unexpected error occurred while trying to access the camera.");
        }
      } else {
        console.error("Unexpected error:", error);
      }
    }
  }

  // Stops the camera when the component is destroyed
  stopCamera() {
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
      this.stream = null;
    }
  }

}
