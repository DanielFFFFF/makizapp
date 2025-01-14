package fr.makizart.restserver;

import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.SimpleStorageService;
import fr.makizart.common.storageservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    final SimpleStorageService storageService;

    public RestController(@Autowired SimpleStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/admin/projects/create/project/")
    public ResponseEntity<IdDTO> createProject(
            @RequestBody ProjectDTO project) throws NameAlreadyBoundException {
        return new ResponseEntity<>(storageService.createProject(project.name()), HttpStatus.CREATED);
    }

    @PostMapping("/admin/projects/{project_id}/create/resource/")
    public ResponseEntity<IdDTO> createResource(
            @PathVariable String project_id,
            @RequestBody IncomingResourceDTO dto) throws NameAlreadyBoundException, IOException {
        return new ResponseEntity<>(storageService.createResource(project_id, dto), HttpStatus.CREATED);

    }

    @PostMapping("/admin/projects/{project_id}/create/videoParameters/")
    public ResponseEntity<String> createSettings(
            @PathVariable String project_id,
            @RequestBody VideoSettingsDTO settings) throws IOException {
        return new ResponseEntity<>(storageService.createSettings(project_id, settings), HttpStatus.CREATED);
    }

    //----------------- PUT -----------------

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

    //----------------- Delete -----------------

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

    //----------------- Get -----------------

    private final String baseDynamicPath = "SpringBootServer/mind-markers/markers";

    @GetMapping("/markers/{projectId}/png-count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Integer> getPngCount(@PathVariable String projectId) {
        // Build the full path to the project folder
        File projectFolder = new File(baseDynamicPath + "/" + projectId + "/");

        System.out.println("Looking for folder: " + projectFolder.getAbsolutePath());
        System.out.println("Folder exists: " + projectFolder.exists());
        System.out.println("Is directory: " + projectFolder.isDirectory());

        if (!projectFolder.exists() || !projectFolder.isDirectory()) {
            return ResponseEntity.badRequest().body(0); // Return 0 if the folder doesn't exist
        }

        // Count the number of .png files in the folder
        int pngCount = (int) Arrays.stream(projectFolder.listFiles())
                .filter(file -> file.isFile() && file.getName().endsWith(".png"))
                .count();

        System.out.println("Amount of pngs:" + pngCount);
        return ResponseEntity.ok(pngCount);
    }

    @GetMapping("/public/projects/")
    @ResponseStatus(HttpStatus.OK)
    public Page<Project> getProjects(@RequestParam int page, @RequestParam int size) {
        System.out.println("get projects api");

        return storageService.getProjects(page, size);
    }

    @GetMapping("/public/projects/{project_id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectDTO getProject(@PathVariable String project_id) {
        System.out.println("1");
        return storageService.getProject(project_id);
    }

    @GetMapping("/public/projects/{project_id}/resources")
    @ResponseStatus(HttpStatus.OK)
    public List<ArResourceDTO> getResourcesInProject(@PathVariable String project_id) {
        System.out.println("2");
        return storageService.getResourcesInProject(project_id);
    }

    @GetMapping("/public/projects/resources/{resource_id}")
    @ResponseStatus(HttpStatus.OK)
    public ArResourceDTO getResource(@PathVariable String resource_id) {
        return storageService.getResource(resource_id);
    }


    @GetMapping("/public/projects/resources/{projectID}/{resourceId}/settings")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getResourceSettings(@PathVariable String resourceId, @PathVariable String projectID) {
        try {

            // Path to the marker directory
            String dirPath = "SpringBootServer/mind-markers/markers/" + projectID + "/settings/";
            // Path to the settings file

            // Define the base directory where the JSON files are stored
            Path basePath = Paths.get(dirPath);

            // Construct the full path to the requested file
            Path filePath = basePath.resolve(resourceId + ".json");

            // Check if the file exists and is readable
            if (!Files.exists(filePath) || !Files.isReadable(filePath)) {
                throw new FileNotFoundException("Resource not found: " + resourceId);
            }

            // Read the file contents as a String
            String content = Files.readString(filePath);

            // Return the file content with the appropriate JSON Content-Type header
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(content);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\": \"Resource not found\"}");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to read the resource\"}");
        }
    }



    @GetMapping("/admin/storage/")
    @ResponseStatus(HttpStatus.OK)
    public StorageInformationDTO getStorageInformation() throws IOException {
        return storageService.getStorageInformation();
    }

    @GetMapping("/public/video/{id}")
    @ResponseStatus(HttpStatus.OK)

    public ResponseEntity<String> getVideo(@PathVariable UUID id) throws IOException {
        return new ResponseEntity<>(storageService.getVideoURL(id), HttpStatus.OK);
    }

    @GetMapping("/resources/IMAGE/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) throws IOException {
        byte[] image = storageService.getThumbnail(id); // Assume this method fetches the image bytes
        return ResponseEntity.ok()
                .header("Content-Type", "image/jpeg") // Or "image/png" depending on your image type
                .body(image);
    }

    @GetMapping("/projects/{project_id}/exist")
    public ResponseEntity<Boolean> checkProjectExists(@PathVariable String project_id) {
        return ResponseEntity.ok(storageService.projectExists(project_id));
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
