import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ProjectManagerComponent } from './project-manager.component';
import {HttpClientModule} from "@angular/common/http";
import {StorageInformations} from "./class";

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


  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('GET : /public/projects/', () => {
    const dummyProjects = { projects: [{ id: 1, nom: 'Projet 1', creationDate: new Date().toString() }, { id: 2, nom: 'Projet 2', creationDate: new Date().toString() }] };

    component.ngOnInit();

    const req = httpMock.expectOne(SERVER_PATH + "/public/projects/");
    expect(req.request.method).toBe('GET');
    req.flush(dummyProjects);

    httpMock.match(req => {
      return req.url !== SERVER_PATH + "/public/projects/";
    }).forEach(req => req.flush({}));

    // @ts-ignore
    expect(component.projects).toEqual(dummyProjects.projects);
    httpMock.verify();
  });

  it('GET : /public/storage/', () => {
    const mockStorageInfo: StorageInformations = {used: 100, total: 200};

    const req = httpMock.expectOne(SERVER_PATH + "/public/storage/");
    expect(req.request.method).toBe('GET');
    req.flush(mockStorageInfo);

    httpMock.match(req => {
      return req.url !== SERVER_PATH + "/public/storage/";
    }).forEach(req => req.flush({}));

    // @ts-ignore
    expect(component.storage[0]).toEqual(mockStorageInfo.used);
    expect(component.storage[1]).toEqual(mockStorageInfo.total);
    httpMock.verify();
  });
});
