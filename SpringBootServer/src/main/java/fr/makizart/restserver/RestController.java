package fr.makizart.restserver;

import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.SimpleStorageService;
import fr.makizart.common.storageservice.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
public class RestController {


	final SimpleStorageService storageService;

	public RestController(@Autowired SimpleStorageService storageService) {
		this.storageService = storageService;
	}



	//POST------
	@PostMapping("/admin/projects/create/project/")
	public ResponseEntity<IdDTO> createProject(
			@RequestParam String name) throws NameAlreadyBoundException {
		return new ResponseEntity<>(storageService.createProject(name), HttpStatus.CREATED);
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
			@RequestParam String name,
			@RequestBody String image) throws NameAlreadyBoundException, IOException {
		storageService.overrideImage(resource_id, name, image);
		return ResponseEntity.ok("Image uploaded successfully.");
	}

	@PutMapping("/admin/projects/resources/{resource_id}/trackedImage/")
	public ResponseEntity<String> uploadTrackedImage(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody String trackedImage) throws NameAlreadyBoundException, IOException {
		storageService.overrideImage(resource_id, name, trackedImage);
		return ResponseEntity.ok("Tracked image uploaded successfully.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/markers")
	public ResponseEntity<String> uploadMarkers(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody Map<String, String> markers) throws NameAlreadyBoundException, IOException {
		storageService.overrideMarkers(
				new MarkerDTO(
						Long.parseLong(resource_id),
						name,
						markers.get("marker1"),
						markers.get("marker2"),
						markers.get("marker3")));
		return ResponseEntity.ok("Markers uploaded successfully.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/sound")
	public ResponseEntity<String> uploadSound(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestBody byte[] sound) throws NameAlreadyBoundException, IOException {
		storageService.overrideSound(resource_id, name, sound);
		return ResponseEntity.ok("Sound uploaded successfully.");
	}

	@PutMapping("/public/projects/resources/{resource_id}/video")
	public ResponseEntity<String> uploadVideo(
			@PathVariable String resource_id,
			@RequestParam String name,
			@RequestParam String url) throws NameAlreadyBoundException, IOException {
		storageService.overrideVideo(resource_id, name, url);
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

	@GetMapping("/public/projects/{page}{size}")
	@ResponseStatus(HttpStatus.OK)
	public Page<Project> getProjects(@PathVariable int page, @PathVariable int size) {
		return storageService.getProjects(page,size);
	}

	@GetMapping("/public/projects/{project_id}/")
	@ResponseStatus(HttpStatus.OK)
	public ProjectDTO getProject(@PathVariable String project_id) {
		return storageService.getProject(project_id);
	}

	@GetMapping("/public/projects/{project_id}/resources/")
	@ResponseStatus(HttpStatus.OK)
	public List<String> getResourcesInProject(@PathVariable String project_id) {
		return storageService.getResourcesInProject(project_id);
	}

	@GetMapping("/public/projects/resources/{resource_id}/")
	@ResponseStatus(HttpStatus.OK)
	public ArResourceDTO getResource(@PathVariable String resource_id) {
		return storageService.getResource(resource_id);
	}

	@GetMapping("/public/storage/")
	@ResponseStatus(HttpStatus.OK)
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
