import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ProjectManagerComponent} from './admin/project-manager/project-manager.component';
import {ProjectEditorComponent} from './admin/project-editor/project-editor.component';
import {TopBarComponent} from './admin/top-bar/top-bar.component';
import {SafePipe} from "./admin/commons/safe.pipe";
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AdminComponent} from './admin/admin.component';
import {ClientComponent} from './client/client.component';
import {RouterModule, RouterOutlet} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import { BaseComponent } from './Base/base.component';


@NgModule({
  declarations: [
    AppComponent,
    ProjectManagerComponent,
    ProjectEditorComponent,
    TopBarComponent,
    SafePipe,
    AdminComponent,
    ClientComponent,
    BaseComponent,
  ],
    imports: [
        BrowserModule,
        NgbModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule,
        RouterModule,
        RouterOutlet,
        MatIconModule,
    ],
  providers: [],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
