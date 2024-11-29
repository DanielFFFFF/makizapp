import { Injectable, Renderer2, RendererFactory2 } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ARService {
  private renderer: Renderer2;

  constructor(private rendererFactory: RendererFactory2) {
    this.renderer = this.rendererFactory.createRenderer(null, null);
  }

  initializeARScene() {
    // Create the a-scene element
    const aScene = this.renderer.createElement('a-scene');
    this.renderer.setAttribute(aScene, 'mindar-image', 'imageTargetSrc: ./targets.mind');
    this.renderer.setAttribute(aScene, 'vr-mode-ui', 'enabled: false');
    this.renderer.setAttribute(aScene, 'device-orientation-permission-ui', 'enabled: false');

    // Create and configure the a-camera element
    const camera = this.renderer.createElement('a-camera');
    this.renderer.setAttribute(camera, 'position', '0 0 0');
    this.renderer.setAttribute(camera, 'look-controls', 'enabled: false');
    this.renderer.appendChild(aScene, camera);

    // Create the a-entity element with mindar-image-target attribute
    const aEntity = this.renderer.createElement('a-entity');
    this.renderer.setAttribute(aEntity, 'mindar-image-target', 'targetIndex: 0');

    // Create and configure the a-plane element
    const aPlane = this.renderer.createElement('a-plane');
    this.renderer.setAttribute(aPlane, 'color', 'blue');
    this.renderer.setAttribute(aPlane, 'opacity', '0.5');
    this.renderer.setAttribute(aPlane, 'position', '0 0 0');
    this.renderer.setAttribute(aPlane, 'height', '0.552');
    this.renderer.setAttribute(aPlane, 'width', '1');
    this.renderer.setAttribute(aPlane, 'rotation', '0 0 0');

    // Append the plane to the entity
    this.renderer.appendChild(aEntity, aPlane);

    // Append the entity to the scene
    this.renderer.appendChild(aScene, aEntity);

    // Append the scene to the body
    this.renderer.appendChild(document.body, aScene);

    // Return the scene for any further modifications if needed
    return aScene;
  }
}
