import { TestBed } from '@angular/core/testing';
import { AppComponent } from './app.component';
import {ProjectManagerComponent} from "./project-manager/project-manager.component";
import {ProjectEditorComponent} from "./project-editor/project-editor.component";
import {TopBarComponent} from "./top-bar/top-bar.component";
import {SafePipe} from "./commons/safe.pipe";

describe('AppComponent', () => {
  beforeEach(() => TestBed.configureTestingModule({
    declarations: [
      AppComponent,
      ProjectManagerComponent,
      ProjectEditorComponent,
      TopBarComponent,
      SafePipe
    ]
  }));

  it('should create the app', () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app).toBeTruthy();
  });

  it(`should have as title 'site'`, () => {
    const fixture = TestBed.createComponent(AppComponent);
    const app = fixture.componentInstance;
    expect(app.title).toEqual('site');
  });

});
