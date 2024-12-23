import {Component, OnInit, OnDestroy, ViewChild, ElementRef, Renderer2, AfterViewInit} from '@angular/core';
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

export class ClientComponent implements OnInit, AfterViewInit {
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
        console.log('ClientComponent ngOnInit called');

        // Ensure scripts are loaded dynamically before initialization
        this.projectId = this.route.snapshot.paramMap.get("projectId");

        // Ensures that the project exists before loading resources
        this.config.getConfig().subscribe(data => {
            this.SERVER_PATH = data["SERVER_PATH"];
            this.checkProjectExists().subscribe(isExisting => {
                if (isExisting) {
                    console.log("Project exists, loading resources...");
                    this.getResources();
                } else {
                    console.log("Project does not exist, redirecting to login.");
                    this.router.navigate(['/admin']);
                }
            });
        });
    }


    ngAfterViewInit(): void {
        this.projectId = this.route.snapshot.paramMap.get("projectId");

        setTimeout(() => {
            this.arService.initializeARScene(this.projectId);
        }, 500); // 3000ms = 3 seconds
    }

    checkProjectExists(){
        return this.http.get<boolean>(`${this.SERVER_PATH}/projects/${this.projectId}/exist`)
        .pipe(
            catchError(error => {
                console.error("Erreur lors de la vérification de l'existence du projet", error);
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
                console.log("VIDEO URL = " + res)
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

}
