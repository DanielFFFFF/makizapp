import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppComponent} from './app.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {ProjectManagerComponent} from './admin/project-manager/project-manager.component';
import {ProjectEditorComponent} from './admin/project-editor/project-editor.component';
import {TopBarComponent} from './admin/top-bar/top-bar.component';
import {SafePipe} from "./admin/commons/safe.pipe";
import { FormsModule } from '@angular/forms';
import {HttpClientModule, provideHttpClient, withInterceptors} from "@angular/common/http";
import {AppRoutingModule} from './app-routing.module';
import {AdminComponent} from './admin/admin.component';
import {ClientComponent} from './client/client.component';
import {RouterModule, RouterOutlet} from "@angular/router";
import {MatIconModule} from "@angular/material/icon";
import {LoginComponent} from './login/login.component';
import { CommonModule } from '@angular/common';
import { AuthInterceptor } from './auth.interceptor';
import { RegisterComponent } from './register/register.component';
import { QRCodeModule } from 'angularx-qrcode';

@NgModule({
  declarations: [
    AppComponent,
    ProjectManagerComponent,
    ProjectEditorComponent,
    TopBarComponent,
    SafePipe,
    AdminComponent,
    ClientComponent,
    LoginComponent,
    RegisterComponent
  ],
    imports: [
        BrowserModule,
        CommonModule,
        NgbModule,
        FormsModule,
        HttpClientModule,
        AppRoutingModule,
        RouterModule,
        RouterOutlet,
        MatIconModule,
        QRCodeModule,
    ],
  providers: [
      provideHttpClient(withInterceptors([AuthInterceptor])),
    ],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppModule {
}
