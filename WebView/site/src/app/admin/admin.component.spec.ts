import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminComponent } from './admin.component';
import {TopBarComponent} from "./top-bar/top-bar.component";
import {ProjectManagerComponent} from "./project-manager/project-manager.component";
import {ProjectEditorComponent} from "./project-editor/project-editor.component";
import {HttpClientTestingModule} from "@angular/common/http/testing";

describe('AdminComponent', () => {
  let component: AdminComponent;
  let fixture: ComponentFixture<AdminComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AdminComponent,TopBarComponent,ProjectManagerComponent,ProjectEditorComponent]
    });
    fixture = TestBed.createComponent(AdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
