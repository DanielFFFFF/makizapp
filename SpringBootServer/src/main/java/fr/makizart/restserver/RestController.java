package fr.makizart.restserver;

import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.SimpleStorageService;
import fr.makizart.common.storageservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    final String urlsSource = "http://localhost:4200";


    final SimpleStorageService storageService;

    public RestController(@Autowired SimpleStorageService storageService) {
        this.storageService = storageService;
    }


    //POST------
    @PostMapping("/admin/projects/create/project/")

    public ResponseEntity<IdDTO> createProject(
            @RequestBody ProjectDTO project) throws NameAlreadyBoundException {
        return new ResponseEntity<>(storageService.createProject(project.name()), HttpStatus.CREATED);
    }

    @PostMapping("/admin/projects/{project_id}/create/resource/")

    public ResponseEntity<ArResourceDTO> createResource(
            @PathVariable String project_id,
            @RequestBody IncomingResourceDTO dto) throws NameAlreadyBoundException {
        return new ResponseEntity<>(storageService.createResource(project_id, dto), HttpStatus.CREATED);
    }

    //PUT--------

    @PutMapping("/admin/projects/resources/{resource_id}/image/")

    public ResponseEntity<String> uploadImage(
            @PathVariable String resource_id,
            @RequestBody IncomingMediaDTO body) throws NameAlreadyBoundException, IOException {
        storageService.overrideImage(resource_id, body.name(), body.media());
        return ResponseEntity.ok("Image uploaded successfully.");
    }

    @PutMapping("/admin/projects/resources/{resource_id}/thumbnail/")

    public ResponseEntity<String> uploadThumbnail(
            @PathVariable String resource_id,
            @RequestBody IncomingMediaDTO body) throws NameAlreadyBoundException, IOException {
        storageService.overrideThumbnail(resource_id, body.name(), body.media());
        return ResponseEntity.ok("Thumbnail uploaded successfully.");
    }

    @PutMapping("/admin/projects/resources/{resource_id}/markers/")

    public ResponseEntity<String> uploadMarkers(
            @PathVariable String resource_id,
            @RequestBody IncomingMarkerDTO body) throws NameAlreadyBoundException, IOException {
        storageService.overrideMarkers(
                resource_id,
                body.name(),
                body.marker1(),
                body.marker2(),
                body.marker3());
        return ResponseEntity.ok("Markers uploaded successfully.");
    }



    @PutMapping("/admin/projects/resources/{resource_id}/sound/")

    public ResponseEntity<String> uploadSound(
            @PathVariable String resource_id,
			@RequestBody IncomingMediaDTO body) throws NameAlreadyBoundException, IOException {
        storageService.overrideSound(resource_id, body.name(), body.media());
        return ResponseEntity.ok("Sound uploaded successfully.");
    }

    @PutMapping("/admin/projects/resources/{resource_id}/video/")

    public ResponseEntity<String> uploadVideo(
            @PathVariable String resource_id,
            @RequestBody IncomingMediaDTO body) throws NameAlreadyBoundException, IOException {
        storageService.overrideVideo(resource_id, body.name(), body.media());
        return ResponseEntity.ok("Video uploaded successfully.");
    }

    @PutMapping("/admin/projects/{project_id}/rename")

    public ResponseEntity<String> renameProject(
            @PathVariable String project_id,
            @RequestBody RenameDTO name) {
        storageService.renameProject(project_id, name.new_name());
        return ResponseEntity.ok("Rename successful.");
    }

    @PutMapping("/admin/projects/resources/{resource_id}/rename")

    public ResponseEntity<String> renameResource(
            @PathVariable String resource_id,
            @RequestBody RenameDTO name) {
        storageService.renameResource(resource_id, name.new_name());
        return ResponseEntity.ok("Rename successful.");
    }

    @PutMapping("/admin/projects/medias/{media_id}/rename")

    public ResponseEntity<String> renameMedia(
            @PathVariable String media_id,
            @RequestParam String new_name) {
        storageService.renameResource(media_id, new_name);
        return ResponseEntity.ok("Rename successful.");
    }


    //Delete-----------------
    @DeleteMapping("/admin/projects/{project_id}/delete")

    public ResponseEntity<String> deleteProject(@PathVariable String project_id) {
        storageService.deleteProject(project_id);
        return ResponseEntity.ok("Delete successful.");
    }

    @DeleteMapping("/admin/projects/{project_id}/resources/{resource_id}/delete")

    public ResponseEntity<String> deleteResource(@PathVariable String project_id, @PathVariable String resource_id) {
        storageService.deleteResource(project_id, resource_id);
        return ResponseEntity.ok("Delete successful.");
    }


    //Get--------------------

    @GetMapping("/public/projects/")
    @ResponseStatus(HttpStatus.OK)

    public Page<Project> getProjects(@RequestParam int page, @RequestParam int size) {
        return storageService.getProjects(page, size);
    }

    @GetMapping("/public/projects/{project_id}/")
    @ResponseStatus(HttpStatus.OK)

    public ProjectDTO getProject(@PathVariable String project_id) {
        return storageService.getProject(project_id);
    }

    @GetMapping("/public/projects/{project_id}/resources/")
    @ResponseStatus(HttpStatus.OK)

    public List<ArResourceDTO> getResourcesInProject(@PathVariable String project_id) {
        return storageService.getResourcesInProject(project_id);
    }

    @GetMapping("/public/projects/resources/{resource_id}/")
    @ResponseStatus(HttpStatus.OK)

    public ArResourceDTO getResource(@PathVariable String resource_id) {
        return storageService.getResource(resource_id);
    }

    @GetMapping("/admin/storage/")
    @ResponseStatus(HttpStatus.OK)

    public StorageInformationDTO getStorageInformation() throws IOException {
        return storageService.getStorageInformation();
    }

    @GetMapping("/public/video/{id}")
    @ResponseStatus(HttpStatus.OK)

    public ResponseEntity<String> getStorageInformation(@PathVariable Long id) throws IOException {
        return new ResponseEntity<>(storageService.getVideoURL(id), HttpStatus.OK);
    }

/*
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleFileNotFound(NoSuchElementException exc) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<?> handleNumberFormatException(NumberFormatException exc) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<?> handleMalformedURL(MalformedURLException exc) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(NameAlreadyBoundException.class)
    public ResponseEntity<?> HandleInvalidParameter(NameAlreadyBoundException exc) {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<?> HandleInvalidID(NoSuchElementException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<?> HandleIOException(IOException exc) {
        return ResponseEntity.internalServerError().build();
    }

 */

}
