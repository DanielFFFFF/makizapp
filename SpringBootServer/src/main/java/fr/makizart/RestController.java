package fr.makizart;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@org.springframework.web.bind.annotation.RestController
public class RestController {



	@GetMapping("/public/projects")
	public List<String> getProjects() {
		throw new NotImplementedException("Not Implemented");
	}

	@GetMapping("/public/projects/{project_id}/getProjectComposition")
	public Map<String, List<String>> getProjectComposition(@PathVariable String project_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@GetMapping("/public/projects/{project_id}/getAllMediaNameInProject")
	public List<Map<String, String>> getAllMediaNameInProject(@PathVariable String project_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@GetMapping("/public/projects/{project_id}/getLocalyStoredImage")
	public ResponseEntity<byte[]> getLocalyStoredImage(@PathVariable String project_id, @RequestParam String media_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@GetMapping("/public/projects/{project_id}/getLocalyStoredSound")
	public ResponseEntity<byte[]> getLocalyStoredSound(@PathVariable String project_id, @RequestParam String media_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@PostMapping("/admin/projects")
	public ResponseEntity<String> createProject(@RequestParam String project_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@GetMapping("/admin/projects/{project_id}")
	public ResponseEntity<String> getProjectMetadata(@PathVariable String project_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@DeleteMapping("/admin/projects/{project_id}")
	public ResponseEntity<String> deleteProject(@PathVariable String project_id) {
		throw new NotImplementedException("Not Implemented");
	}

	@PostMapping("/admin/projects/{project_id}")
	public ResponseEntity<String> renameProject(@PathVariable String project_id, @RequestParam String new_name) {
		throw new NotImplementedException("Not Implemented");
	}

	@PostMapping("/admin/projects/{project_id}/addVideo")
	public ResponseEntity<String> uploadVideo(@PathVariable String project_id, @RequestParam String picture_id, @RequestBody String videoData) {
		throw new NotImplementedException("Not Implemented");
	}

	@PostMapping("/admin/projects/{project_id}/addImage")
	public ResponseEntity<String> uploadImage(@PathVariable String project_id, @RequestParam String picture_id, @RequestBody byte[] imageData) {
		throw new NotImplementedException("Not Implemented");
	}

	@PostMapping("/admin/projects/{project_id}/addSound")
	public ResponseEntity<String> uploadSound(@PathVariable String project_id, @RequestParam String picture_id, @RequestBody byte[] soundData) {
		throw new NotImplementedException("Not Implemented");
	}

	@PostMapping("/admin/projects/{project_id}/{media_id}/rename")
	public ResponseEntity<String> renameMedia(@PathVariable String project_id, @PathVariable String media_id, @RequestParam String new_name) {
		throw new NotImplementedException("Not Implemented");
	}

	@DeleteMapping("/admin/projects/{project_id}/deleteMedia/{media_id}")
	public ResponseEntity<String> deleteMedia(@PathVariable String project_id, @PathVariable String media_id) {
		throw new NotImplementedException("Not Implemented");
	}

}
