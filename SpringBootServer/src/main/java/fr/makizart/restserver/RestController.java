package fr.makizart.restserver;

import fr.makizart.common.storageservice.SimpleStorageService;
import fr.makizart.common.storageservice.dto.ArResourceDTO;
import fr.makizart.common.storageservice.dto.IncomingResourceDTO;
import fr.makizart.common.storageservice.dto.ProjectDTO;
import fr.makizart.common.storageservice.dto.StorageInformationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.RestController
@ComponentScan("fr.makizart")
public class RestController {


	SimpleStorageService storageService;

	public RestController(@Autowired SimpleStorageService storageService) {
		this.storageService = storageService;
	}



	//POST------
	@PostMapping("/admin/projects/create/project/")
	public ResponseEntity<String> createProject(
			@RequestParam String name) throws NameAlreadyBoundException {
		storageService.createProject(name);
		return ResponseEntity.ok("Project created successfully.");
	}

	@PostMapping("/admin/projects/{project_id}/create/resource/")
	public ResponseEntity<String> createResource(
			@PathVariable String project_id,
			@RequestBody IncomingResourceDTO dto) throws NameAlreadyBoundException {
		storageService.createResource(project_id, dto);
		return ResponseEntity.ok("Resource created successfully.");
	}

	//PUT--------

	@PutMapping("/admin/projects/resources/{resource_id}/image/")
	public ResponseEntity<String> uploadImage(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody byte[] image) throws NameAlreadyBoundException, IOException {
		storageService.uploadImage(resource_id, name, image);
		return ResponseEntity.ok("Image uploaded successfully.");
	}

	@PutMapping("/admin/projects/resources/{resource_id}/trackedImage/")
	public ResponseEntity<String> uploadTrackedImage(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody byte[] trackedImage) throws NameAlreadyBoundException, IOException {
		storageService.uploadTrackedImage(resource_id, name, trackedImage);
		return ResponseEntity.ok("Tracked image uploaded successfully.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/markers")
	public ResponseEntity<String> uploadMarkers(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody Map<String, byte[]> markers) throws NameAlreadyBoundException, IOException {
		storageService.uploadMarkers(resource_id, name, markers);
		return ResponseEntity.ok("Markers uploaded successfully.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/sound")
	public ResponseEntity<String> uploadSound(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody byte[] sound) throws NameAlreadyBoundException, IOException {
		storageService.uploadSound(resource_id, name, sound);
		return ResponseEntity.ok("Sound uploaded successfully.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/video")
	public ResponseEntity<String> uploadVideo(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestParam String url) throws NameAlreadyBoundException, IOException {
		storageService.uploadVideo(resource_id, name, url);
		return ResponseEntity.ok("Video uploaded successfully.");
	}

	@PutMapping("/public/projects/{project_id}/rename")
	public ResponseEntity<String> renameProject(
			@PathVariable String project_id,
			@RequestParam String new_name) {
		storageService.renameProject(project_id, new_name);
		return ResponseEntity.ok("Rename successful.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/rename")
	public ResponseEntity<String> renameResource(
			@PathVariable String resource_id,
			@RequestParam String new_name) {
		storageService.renameResource(resource_id, new_name);
		return ResponseEntity.ok("Rename successful.");
	}

	@PutMapping("/public/projects/medias/{media_id}/rename")
	public ResponseEntity<String> renameMedia(
			@PathVariable String media_id,
			@RequestParam String new_name) {
		storageService.renameResource(media_id, new_name);
		return ResponseEntity.ok("Rename successful.");
	}


	//Delete-----------------
	@DeleteMapping("/public/projects/{project_id}/delete")
	public ResponseEntity<String> deleteProject(@PathVariable String project_id) {
		storageService.deleteProject(project_id);
		return ResponseEntity.ok("Delete successful.");
	}

	@DeleteMapping("/public/projects/resources/{resource_id}/delete")
	public ResponseEntity<String> deleteResource(@PathVariable String resource_id) {
		storageService.deleteResource(resource_id);
		return ResponseEntity.ok("Delete successful.");
	}


	//Get--------------------

	@GetMapping("/public/projects/")
	public List<String> getProjects() {
		return storageService.getProject();
	}

	@GetMapping("/public/projects/{project_id}/")
	public ProjectDTO getProject(@PathVariable String project_id) {
		return storageService.getProject(project_id);
	}

	@GetMapping("/public/projects/{project_id}/resources/")
	public List<String> getResourcesInProject(@PathVariable String project_id) {
		return storageService.getResourcesInProject(project_id);
	}

	@GetMapping("/public/projects/resources/{resource_id}/")
	public ArResourceDTO getResource(@PathVariable String resource_id) {
		return storageService.getResource(resource_id);
	}

	@GetMapping("/public/storage/")
	public StorageInformationDTO getStorageInformation() {
		return storageService.getStorageInformation();
	}



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

}
