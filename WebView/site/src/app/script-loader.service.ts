import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root',
})
export class ScriptLoaderService {
    private loadedScripts: { [key: string]: boolean } = {};

    loadScript(src: string): Promise<void> {
        return new Promise((resolve, reject) => {
            if (this.loadedScripts[src]) {
                resolve();
                return;
            }

            const script = document.createElement('script');
            script.src = src;
            script.setAttribute('data-persistent', 'true'); // Add a custom attribute
            script.async = true;
            script.onload = () => {
                this.loadedScripts[src] = true;
                resolve();
            };
            script.onerror = () => reject(new Error(`Failed to load script: ${src}`));
            document.head.appendChild(script);
        });
    }

    loadScripts(scripts: string[]): Promise<void[]> {
        return Promise.all(scripts.map((src) => this.loadScript(src)));
    }
}
