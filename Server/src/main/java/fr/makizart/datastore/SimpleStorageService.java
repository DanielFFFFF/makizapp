package fr.makizart.datastore;

import fr.makizart.datastore.common.StorageService;
import org.springframework.stereotype.Component;

import javax.naming.NameAlreadyBoundException;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Component
public class SimpleStorageService implements StorageService {
	@Override
	public List<String> getProjects() {
		return null;
	}

	@Override
	public Map<String, List<String>> getProjectComposition(String projectId) throws NoSuchElementException {
		return null;
	}

	@Override
	public List<Map<String, String>> getAllMediaNameInProject(String projectId) throws NoSuchElementException {
		return null;
	}

	@Override
	public File getLocallyStoredImage(String projectId, String mediaId) throws NoSuchElementException {
		return null;
	}

	@Override
	public File getLocallyStoredSound(String projectId, String mediaId) throws NoSuchElementException {
		return null;
	}

	@Override
	public String createProject(String projectName) throws NameAlreadyBoundException {
		return null;
	}

	@Override
	public String getProjectMetadata(String projectId) throws NoSuchElementException {
		return null;
	}

	@Override
	public boolean deleteProject(String projectId) throws NoSuchElementException {
		return false;
	}

	@Override
	public String renameProject(String projectId, String newName) throws NoSuchElementException, InvalidParameterException {
		return null;
	}

	@Override
	public void saveRemoteVideo(String projectId, String pictureId, URL videoUrl) throws NoSuchElementException, InvalidParameterException {

	}

	@Override
	public void saveImage(String projectId, String pictureId, Image imageData) throws InvalidParameterException, NoSuchElementException {

	}

	@Override
	public void saveSound(String projectId, String pictureId, byte[] soundData) throws InvalidParameterException, NoSuchElementException, NameAlreadyBoundException {

	}

	@Override
	public void saveSound(String projectId, String pictureId, ByteArrayInputStream inputStream) throws InvalidParameterException, NoSuchElementException {

	}

	@Override
	public void renameMedia(String projectId, String mediaId, String newName) throws NoSuchElementException, InvalidParameterException {

	}

	@Override
	public String deleteMedia(String projectId, String mediaId) throws NoSuchElementException, InvalidParameterException {
		return null;
	}
}
