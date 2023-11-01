package fr.makizart;

import fr.makizart.datastore.SimpleStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.naming.NameAlreadyBoundException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.RestController
public class RestController {


	SimpleStorageService storageService;

	public RestController(@Autowired SimpleStorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/public/projects")
	public List<String> getProjects() {
		return storageService.getProjects();
	}

	@GetMapping("/public/projects/{project_id}/getProjectComposition")
	public ResponseEntity<Map<String, List<String>>> getProjectComposition(@PathVariable String project_id) {
			return new ResponseEntity<>(storageService.getProjectComposition(project_id), HttpStatus.OK);
    }

	@GetMapping("/public/projects/{project_id}/getAllMediaNameInProject")
	public ResponseEntity<List<Map<String, String>>> getAllMediaNameInProject(@PathVariable String project_id) {
			return new ResponseEntity<>(storageService.getAllMediaNameInProject(project_id), HttpStatus.OK);
	}

	@GetMapping("/public/projects/{project_id}/getLocallyStoredImage")
	public ResponseEntity<byte[]> getLocallyStoredImage(@PathVariable String project_id, @RequestParam String media_id) throws IOException {
			return new ResponseEntity<>(Files.readAllBytes(storageService.getLocallyStoredImage(project_id,media_id).toPath()), HttpStatus.OK);
    }

	@GetMapping("/public/projects/{project_id}/getLocallyStoredSound")
	public ResponseEntity<byte[]> getLocallyStoredSound(@PathVariable String project_id, @RequestParam String media_id) throws IOException {
			return new ResponseEntity<>(Files.readAllBytes(storageService.getLocallyStoredSound(project_id, media_id).toPath()), HttpStatus.OK);
	}

	@PostMapping("/admin/projects")
	public ResponseEntity<String> createProject(@RequestParam String project_id) throws NameAlreadyBoundException {
		return new ResponseEntity<>(storageService.createProject(project_id), HttpStatus.CREATED);
	}

	@GetMapping("/admin/projects/{project_id}")
	public ResponseEntity<String> getProjectMetadata(@PathVariable String project_id) {
		return new ResponseEntity<>(storageService.getProjectMetadata(project_id), HttpStatus.OK);
	}

	@DeleteMapping("/admin/projects/{project_id}")
	public ResponseEntity<Boolean> deleteProject(@PathVariable String project_id) {
			return new ResponseEntity<>(storageService.deleteProject(project_id),HttpStatus.OK);
	}

	@PostMapping("/admin/projects/{project_id}")
	public ResponseEntity<String> renameProject(@PathVariable String project_id, @RequestParam String new_name) {
			storageService.renameProject(project_id, new_name);
		return ResponseEntity.ok().build();
    }

	@PostMapping("/admin/projects/{project_id}/addVideo")
	public ResponseEntity<String> uploadVideo(@PathVariable String project_id, @RequestParam String name, @RequestParam String url) throws MalformedURLException {
			storageService.saveRemoteVideo(project_id, name , new URL(url));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/admin/projects/{project_id}/addImage")
	public ResponseEntity<String> uploadImage(@PathVariable String project_id, @RequestParam String name, @RequestBody byte[] imageData) throws IOException {
			storageService.saveImage(project_id, name, ImageIO.read(new ByteArrayInputStream(imageData)));
		return ResponseEntity.ok().build();

	}

	@PostMapping("/admin/projects/{project_id}/addSound")
	public ResponseEntity<String> uploadSound(@PathVariable String project_id, @RequestParam String name, @RequestBody byte[] soundData) {
			storageService.saveSound(project_id, name, new ByteArrayInputStream(soundData));
		return ResponseEntity.ok().build();
	}

	@PostMapping("/admin/projects/{project_id}/{media_id}/rename")
	public ResponseEntity<String> renameMedia(@PathVariable String project_id, @PathVariable String media_id, @RequestParam String new_name) {
		storageService.renameMedia(project_id,media_id,new_name);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/admin/projects/{project_id}/deleteMedia/{media_id}")
	public ResponseEntity<String> deleteMedia(@PathVariable String project_id, @PathVariable String media_id) {
		storageService.deleteMedia(project_id,media_id);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleFileNotFound(NoSuchElementException exc) {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleMalformedURL(MalformedURLException exc) {
		return ResponseEntity.badRequest().build();
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> HandleInvalidParameter(InvalidParameterException exc) {
		return ResponseEntity.notFound().build();
	}

	@ExceptionHandler(NameAlreadyBoundException.class)
	public ResponseEntity<?> HandleInvalidParameter(NameAlreadyBoundException exc) {
		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	}

	@ExceptionHandler(IOException.class)
	public ResponseEntity<?> HandleIOException(IOException exc) {
		return ResponseEntity.internalServerError().build();
	}

}
