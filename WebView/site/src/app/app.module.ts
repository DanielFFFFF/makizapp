import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ProjectManagerComponent } from './project-manager/project-manager.component';
import { ProjectEditorComponent } from './project-editor/project-editor.component';
import { TopBarComponent } from './top-bar/top-bar.component';
import {SafePipe} from "./commons/safe.pipe";

@NgModule({
  declarations: [
    AppComponent,
    ProjectManagerComponent,
    ProjectEditorComponent,
    TopBarComponent,
    SafePipe
  ],
  imports: [
    BrowserModule,
    NgbModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
