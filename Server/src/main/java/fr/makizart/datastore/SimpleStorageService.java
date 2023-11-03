package fr.makizart.datastore;

import fr.makizart.database.repositories.ArRessourceAssetRepository;
import fr.makizart.database.repositories.ImageAssetRepository;
import fr.makizart.database.repositories.ProjectRepository;
import fr.makizart.database.repositories.VideoAssetRepository;
import fr.makizart.database.table.ArResource;
import fr.makizart.database.table.Project;
import fr.makizart.datastore.common.StorageService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NameAlreadyBoundException;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class SimpleStorageService implements StorageService {


	public ProjectRepository projectRepository;

	public ArRessourceAssetRepository arRessourceAssetRepository;

	public ImageAssetRepository imageAssetRepository;

	public VideoAssetRepository videoAssetRepository;


	public SimpleStorageService(
			@Autowired ProjectRepository projectRepository,
			@Autowired ArRessourceAssetRepository arRessourceAssetRepository,
			@Autowired ImageAssetRepository imageAssetRepository,
			@Autowired VideoAssetRepository videoAssetRepository) {
		this.projectRepository = projectRepository;
		this.arRessourceAssetRepository = arRessourceAssetRepository;
		this.imageAssetRepository = imageAssetRepository;
		this.videoAssetRepository = videoAssetRepository;
	}

	@Override
	public Collection<String> getProjects() {
		return projectRepository.findAll().stream().map(Project::getName).toList();
	}

	@Override
	public Map<String, List<String>> getProjectComposition(String projectId) throws NoSuchElementException, InvalidParameterException {
		tryGetProject(projectId);
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
	@Transactional
	public String deleteMedia(String projectId, String mediaId) throws NoSuchElementException, InvalidParameterException {
        return projectId;
    }

	private Project tryGetProject(String projectId) {
		try {
			return projectRepository.getReferenceById(Long.valueOf(projectId));
		}catch (NumberFormatException e){
			throw new InvalidParameterException();
		}catch (EntityNotFoundException e){
			throw new NoSuchElementException();
		}
	}
}
