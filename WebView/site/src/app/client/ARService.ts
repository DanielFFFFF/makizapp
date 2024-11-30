import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class ARService {
  private renderer: Renderer2;

  constructor(private rendererFactory: RendererFactory2, private http: HttpClient) {
    this.renderer = this.rendererFactory.createRenderer(null, null);
  }

  createARScene(project_id: string | null, pngcount: number) {
    // Create the a-scene element
    const aScene = this.renderer.createElement('a-scene');
    this.renderer.setAttribute(aScene, 'mindar-image', `imageTargetSrc: ./markers/${project_id}/targets.mind`);
    this.renderer.setAttribute(aScene, 'vr-mode-ui', 'enabled: false');
    this.renderer.setAttribute(aScene, 'device-orientation-permission-ui', 'enabled: false');

    // Create and configure the a-camera element
    const camera = this.renderer.createElement('a-camera');
    this.renderer.setAttribute(camera, 'position', '0 0 0');
    this.renderer.setAttribute(camera, 'look-controls', 'enabled: false');
    this.renderer.appendChild(aScene, camera);

    for (let i = 0; i < pngcount; i++) {
      // Create the a-entity element with mindar-image-target attribute
      var aEntity = this.renderer.createElement(`a-entity`);
      this.renderer.setAttribute(aEntity, 'mindar-image-target', `targetIndex: ${i}`);


      var aVideo = this.renderer.createElement('a-video');
      this.renderer.setAttribute(aVideo, 'src', 'https://i.imgur.com/w8VTzDm.mp4');
      this.renderer.setAttribute(aVideo, 'opacity', '0.5');
      this.renderer.setAttribute(aVideo, 'position', '0 0 0');
      this.renderer.setAttribute(aVideo, 'height', '0.552');
      this.renderer.setAttribute(aVideo, 'width', '1');
      this.renderer.setAttribute(aVideo, 'rotation', '0 0 0');

      this.renderer.appendChild(aEntity, aVideo);

      // Append the entity to the scene
      this.renderer.appendChild(aScene, aEntity);


      /**
       *
       *
       *
       *  var aPlane = this.renderer.createElement('a-plane');
       *       this.renderer.setAttribute(aPlane, 'color', 'blue');
       *       this.renderer.setAttribute(aPlane, 'opacity', '0.5');
       *       this.renderer.setAttribute(aPlane, 'position', '0 0 0');
       *       this.renderer.setAttribute(aPlane, 'height', '0.552');
       *       this.renderer.setAttribute(aPlane, 'width', '1');
       *       this.renderer.setAttribute(aPlane, 'rotation', '0 0 0');
       *
       * // Append the plane to the entity
       *       this.renderer.appendChild(aEntity, aPlane);
       *
       *       // Append the entity to the scene
       *       this.renderer.appendChild(aScene, aEntity);
       *
       *
      // Create and configure the a-plane element
      */


    }
    // Append the scene to the body
    this.renderer.appendChild(document.body, aScene);

    // Return the scene for any further modifications if needed
    return aScene;
  }

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
}
