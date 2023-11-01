import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectEditorComponent } from './project-editor.component';

describe('ProjectEditorComponent', () => {
  let component: ProjectEditorComponent;
  let fixture: ComponentFixture<ProjectEditorComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectEditorComponent]
    });
    fixture = TestBed.createComponent(ProjectEditorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
