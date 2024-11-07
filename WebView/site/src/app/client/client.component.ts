import { Component, OnInit, OnDestroy, ViewChild, ElementRef } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { HttpClient } from "@angular/common/http";
import { map } from "rxjs";
import { Resource } from "../commons/Resource";
import { AppConfigService } from "../config/app.config.service";

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
  projectId: string = "";

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private config: AppConfigService,
    private router: Router
  ) { }

  ngOnInit() {
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];
      this.projectId = this.route.snapshot.paramMap.get("projectID") as string;

      if (!this.projectId) {
        //alert("Project ID is missing or invalid. Redirecting to admin.");
        this.router.navigate(['/admin']); // Redirect to admin page if projectId is invalid
        return;
      }

      this.getResources();
    });

    this.startCamera();
  }

  ngOnDestroy(): void {
    this.stopCamera();
  }

  getResources() {
    this.http.get<any>(`${this.SERVER_PATH}/public/projects/${this.projectId}/resources`).pipe(
      map((value: Resource[]) => {
        return value;
      })
    ).subscribe(
      (res: Resource[]) => {
        if (res.length === 0) {
          console.error("No resources found for the specified project.");
          this.router.navigate(['/admin']); // Redirect to admin if no resources found
        } else {
          console.log(res);
          this.resources = res;

          this.resources.map(resource => {
            this.getContentOfResource(resource);
          });
        }
      },
      (error) => {
        console.error("Failed to fetch project resources.");
        this.router.navigate(['/admin']); // Redirect to admin if API call fails
      }
    );
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
    if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
      console.error("Camera access is not supported in this browser.");
      //alert("Your browser does not support camera access. Please use a modern browser like Chrome, Firefox, or Edge.");
      return;
    }

    try {
      this.stream = await navigator.mediaDevices.getUserMedia({ video: { facingMode: 'environment' } });
      this.video.nativeElement.srcObject = this.stream;
    } catch (error) {
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

  stopCamera() {
    if (this.stream) {
      this.stream.getTracks().forEach(track => track.stop());
      this.stream = null;
    }
  }

  toto(id: string) {
    console.log(id);
  }
}
