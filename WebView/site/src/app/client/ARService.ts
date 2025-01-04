import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Resource } from "../commons/Resource";
import {AppConfigService} from "../config/app.config.service";
import {map, switchMap} from "rxjs/operators";
import {Observable} from "rxjs";

/*
* This injectable is responsible for creating the HTML file that runs the MindAR script
* This is done instead of using the angular html file directly because the script needs to run outside of the angular app-root
 */

@Injectable({
  providedIn: 'root',
})
export class ARService {
  private renderer: Renderer2;
  SERVER_PATH: string = "";


  constructor(private rendererFactory: RendererFactory2, private http: HttpClient, private config: AppConfigService) {
    this.renderer = this.rendererFactory.createRenderer(null, null);
    this.config.getConfig().subscribe(data => {
      this.SERVER_PATH = data["SERVER_PATH"];

    });
  }

  resources: Resource[] = [];

/*
* Creates an AR scene
* Requires projectID to find the corresponding .mind marker file
* Requires pngcount to know how many images to add
* It loops through all the images and creates an entity with the corresponding index ( the index picks the correct marker in the .mind)
* This index is also used to find the corresponding video/ressource associated to the png
 */

  createARScene(project_id: string | null, pngcount: number) {
    // Create the a-scene element
    // This is done only once as all the images will be children of it as they share the same .mind file

    const aScene = this.renderer.createElement('a-scene');
    this.renderer.setAttribute(aScene, 'mindar-image', `imageTargetSrc: ./markers/${project_id}/targets.mind`);
    this.renderer.setAttribute(aScene, 'vr-mode-ui', 'enabled: false');
    this.renderer.setAttribute(aScene, 'device-orientation-permission-ui', 'enabled: false');

    // Create and configure the a-camera element
    const camera = this.renderer.createElement('a-camera');
    this.renderer.setAttribute(camera, 'position', '0 0 0');
    this.renderer.setAttribute(camera, 'look-controls', 'enabled: false');
    this.renderer.appendChild(aScene, camera);

    const assets = this.renderer.createElement('a-assets');

    // Create as many entities as there are photos
    for (let i = 0; i < pngcount; i++) {
      this.getResourceVideoURL(project_id, pngcount - i - 1).subscribe(videoURL => {
          this.getResourceSettings(project_id, pngcount - i - 1).subscribe(settings => {
        // Create the a-entity element with mindar-image-target attribute
        var aEntity = this.renderer.createElement(`a-entity`);

        // Associate the entity with the correct index in the .mind file
        // This means the entity will look for the features in the i*th "marker" in the .mind
        this.renderer.setAttribute(aEntity, 'mindar-image-target', `targetIndex: ${i}`);

        // Create a video element to display on this entity if found
        var video = this.renderer.createElement('video');


        // The video to be displayed
        this.renderer.setAttribute(video, 'src', videoURL);

        this.renderer.setAttribute(video, 'id', i.toString());
        // The settings
        this.renderer.setAttribute(video, 'opacity', '0.8');
        this.renderer.setAttribute(video, 'preload', 'auto');

        this.renderer.setAttribute(video, 'autoplay', '');



        /*
        this.renderer.setAttribute(video, 'position', '0 0 0');
        this.renderer.setAttribute(video, 'height', '0.552');
        this.renderer.setAttribute(video, 'width', '1');
        this.renderer.setAttribute(video, 'rotation', '0 0 0');
        */



        var aVideo = this.renderer.createElement('a-video');


        // The video to be displayed
        this.renderer.setAttribute(aVideo, 'src', "#" + i.toString());

        console.log(settings);
        // The settings
        this.renderer.setAttribute(aVideo, 'opacity', (settings.videoOpacity / 100).toString());
        this.renderer.setAttribute(aVideo, 'preload', 'auto');
        this.renderer.setAttribute(aVideo, 'position', '0 0 0');
        let ratio = settings.height / settings.width;
        this.renderer.setAttribute(aVideo, 'height', (ratio * settings.videoSize/100).toString() );
        this.renderer.setAttribute(aVideo, 'width', (1 * settings.videoSize/100).toString());
        this.renderer.setAttribute(aVideo, 'rotation', '0 0 0');




        // Listen for the targetFound event
        aEntity.addEventListener('targetFound', () => {

            let arSystem = aScene.systems["mindar-image-system"];
          console.log(`Target ${i} found`);
          // Add additional code to handle the target being found, such as playing the video
          video.play();
        });

        // Listen for the targetLost event
        aEntity.addEventListener('targetLost', () => {
          let arSystem = aScene.systems["mindar-image-system"];
          console.log(`Target ${i} lost`);
          // Add additional code to handle the target being lost, such as pausing the video
          video.pause();
          video.timestamp(0);
        });


       // this.renderer.setAttribute(aEntity, 'src', "#" + i.toString());
        this.renderer.appendChild(assets, video);

        // Make it a child of the entity
        this.renderer.appendChild(aEntity, aVideo);

        // Append the entity to the scene

        this.renderer.appendChild(aScene, aEntity);



          });
      });



    }
    this.renderer.appendChild(aScene, assets);

    // Append the scene to the body
    this.renderer.appendChild(document.body, aScene);

    // Return the scene for any further modifications if needed
    return aScene;
  }


  // Creates a scene after using endpoint to count the amount of pngs in the project folder
  initializeARScene(project_id: string | null) {
    // Fetch the PNG count for the given project_id
    this.http.get<number>(`./markers/${project_id}/png-count`)
      .subscribe(pngCount => {
        // Now create the AR scene with the PNG count
        this.createARScene(project_id, pngCount);
      }, error => {
        console.error("Failed to fetch PNG count:", error);
      });
  }

  // Gets the URL of the video given the index in the directory
  getResourceVideoURL(projectId: string | null, k: number): Observable<string> {
    return this.http.get<Resource[]>(`${this.SERVER_PATH}/public/projects/${projectId}/resources`).pipe(
      map((resources: Resource[]) => resources[k]), // Get the k-th resource
      switchMap((resource: Resource) =>
        this.http.get(`${this.SERVER_PATH}/public/video/${resource.videoAssetId}`, { responseType: 'text' }) // Fetch the video URL
      )
    );
  }

    getResourceSettings(projectId: string | null, k: number): Observable<any> {
        return this.http.get<Resource[]>(`${this.SERVER_PATH}/public/projects/${projectId}/resources`).pipe(
            map((resources: Resource[]) => resources[k]), // Get the k-th resource
            switchMap((resource: Resource) =>
                this.http.get<any>(`${this.SERVER_PATH}/public/projects/resources/${projectId}/${resource.id}/settings`, { responseType: 'json' }) // Fetch the JSON file
            )
        );
    }

}
