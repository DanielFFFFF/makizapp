package fr.makizart.datastore;

import fr.makizart.datastore.common.StorageService;
import org.apache.commons.lang3.NotImplementedException;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class SimpleStorageService implements StorageService {
	@Override
	public List<String> getProjects() {
		throw new NotImplementedException();
	}

	@Override
	public Map<String, List<String>> getProjectComposition(String projectId) {
		throw new NotImplementedException();
	}

	@Override
	public List<Map<String, String>> getAllMediaNameInProject(String projectId) {
		throw new NotImplementedException();
	}

	@Override
	public File getLocallyStoredImage(String projectId, String mediaId) {
		throw new NotImplementedException();
	}

	@Override
	public File getLocallyStoredSound(String projectId, String mediaId) {
		throw new NotImplementedException();
	}

	@Override
	public String createProject(String projectId) {
		throw new NotImplementedException();
	}

	@Override
	public String getProjectMetadata(String projectId) {
		throw new NotImplementedException();
	}

	@Override
	public String deleteProject(String projectId) {
		throw new NotImplementedException();
	}

	@Override
	public String renameProject(String projectId, String newName) {
		throw new NotImplementedException();
	}

	@Override
	public void saveRemoteVideo(String projectId, String pictureId, String videoData) {
		throw new NotImplementedException();
	}

	@Override
	public void saveImage(String projectId, String pictureId, Image imageData) {
		throw new NotImplementedException();
	}

	@Override
	public void saveSound(String projectId, String pictureId, byte[] soundData) {
		throw new NotImplementedException();
	}

	@Override
	public void renameMedia(String projectId, String mediaId, String newName) throws NoSuchElementException {
		throw new NotImplementedException();
	}

	@Override
	public String deleteMedia(String projectId, String mediaId) throws NoSuchElementException {
		throw new NotImplementedException();
	}
}
