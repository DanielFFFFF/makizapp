package fr.makizart.common.storageservice;

import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.ArResource;
import fr.makizart.common.database.table.Project;
import fr.makizart.common.database.table.SoundAsset;
import fr.makizart.common.database.table.VideoAsset;
import fr.makizart.common.storageservice.dto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.NameAlreadyBoundException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.regex.Pattern;

@Component
@Transactional
public class SimpleStorageService implements StorageService {


	public static final String FS_SAVE_PATH = "";
	public ProjectRepository projectRepository;

	public ArRessourceAssetRepository arRessourceRepository;

	public ImageAssetRepository imageAssetRepository;

	public VideoAssetRepository videoAssetRepository;
	public SoundAssetReposetory soundAssetReposetory;

	Pattern invalidName = Pattern.compile("[^-_.A-Za-z0-9]");


	public SimpleStorageService(
			@Autowired ProjectRepository projectRepository,
			@Autowired ArRessourceAssetRepository arRessourceRepository,
			@Autowired ImageAssetRepository imageAssetRepository,
			@Autowired VideoAssetRepository videoAssetRepository,
			@Autowired SoundAssetReposetory soundAssetReposetory) {
		this.projectRepository = projectRepository;
		this.arRessourceRepository = arRessourceRepository;
		this.imageAssetRepository = imageAssetRepository;
		this.videoAssetRepository = videoAssetRepository;
		this.soundAssetReposetory = soundAssetReposetory;
	}

	@Override
	public List<String> getProject() {
		return projectRepository.findAllID()
				.stream()
				.map(Objects::toString).toList();
	}

	@Override
	public ProjectDTO getProject(String projectId) throws NoSuchElementException, InvalidParameterException {
		return new ProjectDTO(tryGetProject(projectId));
	}

	@Override
	public List<String> getResourcesInProject(String projectId) throws InvalidParameterException, NoSuchElementException {
		return tryGetProject(projectId).getArResource().stream().map(Objects::toString).toList();
	}

	@Override
	public ArResourceDTO getResource(String resourceId) throws InvalidParameterException, NoSuchElementException {
		throw new NotImplementedException();
	}

	@Override
	public StorageInformationDTO getStorageInformation() {
		throw new NotImplementedException();
	}

	@Override
	public void uploadMarkers(String resourceId, String name, Map<String, byte[]> markers) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		throw new NotImplementedException();
	}

	@Override
	public void uploadSound(String resourceId, String name, byte[] sound) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		try {
			AudioFileFormat audio = AudioSystem.getAudioFileFormat(new ByteArrayInputStream(sound));
			if(!soundAssetReposetory.existsById(Long.valueOf(resourceId))){
				throw new  NoSuchElementException();
			}
			//TODO write file to disk
			SoundAsset soundAsset = new SoundAsset();
			soundAsset.setName(name);

		} catch (UnsupportedAudioFileException e) {
			throw new InvalidParameterException("audio type not supported");
		}
		throw new NotImplementedException();
	}

	@Override
	public void uploadVideo(String videoId, String name, String url) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		try {
			tryGetVideo(videoId).setVideoURL(URI.create(url).toURL());
		}catch (IllegalArgumentException e){
			throw new InvalidParameterException();
		}
	}

	@Override
	public void renameProject(String projectId, String newName) throws InvalidParameterException, NoSuchElementException {
		validateName(newName);
		Project project = tryGetProject(projectId);
		project.setName(newName);
		projectRepository.save(project);

	}

	@Override
	public void renameResource(String resourceId, String newName) throws InvalidParameterException, NoSuchElementException {
		validateName(newName);
		ArResource resource = tryGetResource(resourceId);
		resource.setName(newName);
		arRessourceRepository.save(resource);
	}

	@Override
	public void deleteProject(String projectId) throws InvalidParameterException, NoSuchElementException {
		projectRepository.delete(tryGetProject(projectId));
	}

	@Override
	public void deleteResource(String resourceId) throws InvalidParameterException, NoSuchElementException {
		arRessourceRepository.delete(tryGetResource(resourceId));
	}

	@Override
	public IdDTO createProject(String name) throws InvalidParameterException, NameAlreadyBoundException {
		validateName(name);
		Project project = new Project(name);
		projectRepository.save(project);
		return new IdDTO(project.getId().toString());
	}

	@Override
	public ArResourceDTO createResource(String projectId, IncomingResourceDTO incomingResourceDTO) throws InvalidParameterException, NameAlreadyBoundException {
		throw new NotImplementedException();
	}

	@Override
	public void uploadImage(String resourceId, String name, byte[] image) throws InvalidParameterException, IOException, NameAlreadyBoundException {
		throw new NotImplementedException();
	}

	@Override
	public void uploadTrackedImage(String resourceId, String name, byte[] trackedImage) throws InvalidParameterException, IOException, NameAlreadyBoundException {
		throw new NotImplementedException();
	}

	private void validateName(String newName) {
		if(invalidName.matcher(newName).find())
			throw new InvalidParameterException();
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
	private ArResource tryGetResource(String resourceID) {
		try {
			return arRessourceRepository.getReferenceById(Long.valueOf(resourceID));
		}catch (NumberFormatException e){
			throw new InvalidParameterException();
		}catch (EntityNotFoundException e){
			throw new NoSuchElementException();
		}
	}
	private VideoAsset tryGetVideo(String resourceID) {
		try {
			return videoAssetRepository.getReferenceById(Long.valueOf(resourceID));
		}catch (NumberFormatException e){
			throw new InvalidParameterException();
		}catch (EntityNotFoundException e){
			throw new NoSuchElementException();
		}
	}

}
