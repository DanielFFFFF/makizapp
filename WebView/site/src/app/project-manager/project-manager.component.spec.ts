import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProjectManagerComponent } from './project-manager.component';
import {HttpClientModule} from "@angular/common/http";

describe('ProjectManagerComponent', () => {
  let component: ProjectManagerComponent;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule,HttpClientModule],
      declarations: [ProjectManagerComponent]
    });

    component = TestBed.createComponent(ProjectManagerComponent).componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });

  it('Test Structure : Creation', () => {
    expect(component).toBeTruthy();
  });

  it('Test API : Get a list of projects', () => {
    const dummyProjects = { response: { projects: [{ id: 1, nom: 'Projet 1', creationDate: new Date().toString() }, { id: 2, nom: 'Projet 2' }] } };

    component.ngOnInit();

    const req = httpMock.expectOne(`http://test/public/projects`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyProjects);

    // @ts-ignore
    expect(component.projects).toEqual(dummyProjects.response.projects);
    httpMock.verify();
  });
});
