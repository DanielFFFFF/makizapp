package fr.makizart.common.storageservice;

import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.*;
import fr.makizart.common.storageservice.dto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.naming.NameAlreadyBoundException;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.regex.Pattern;

@Component
@Transactional
public class SimpleStorageService implements StorageService {


	public static final String FS_SAVE_PATH = "";
	public final ProjectRepository projectRepository;

	public final ArRessourceAssetRepository arRessourceRepository;

	public final ImageAssetRepository imageAssetRepository;

	public final VideoAssetRepository videoAssetRepository;
	public final SoundAssetReposetory soundAssetReposetory;

	public final MarkerAssetRepository markerAssetRepository;


	Pattern invalidName = Pattern.compile("[^-_.A-Za-z0-9]");


	public SimpleStorageService(
			@Autowired ProjectRepository projectRepository,
			@Autowired ArRessourceAssetRepository arRessourceRepository,
			@Autowired MarkerAssetRepository markerAssetRepository,
			@Autowired ImageAssetRepository imageAssetRepository,
			@Autowired VideoAssetRepository videoAssetRepository,
			@Autowired SoundAssetReposetory soundAssetReposetory,
			@Autowired FileSystemManager fileSystemManager) {
		this.projectRepository = projectRepository;
		this.arRessourceRepository = arRessourceRepository;
		this.imageAssetRepository = imageAssetRepository;
		this.videoAssetRepository = videoAssetRepository;
		this.soundAssetReposetory = soundAssetReposetory;
		this.markerAssetRepository = markerAssetRepository;
    }

	@Override
	public Page<Project> getProjects(int nbPage, int size) {
		return projectRepository.findAll(PageRequest.of(nbPage, size));
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
	@Transactional
	public void uploadMarkers(String resourceId, String name, Map<String, byte[]> markers) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		// Check 1: Is the name valid?
		validateName(name);

		// Check 2: Does the resource exist?
		if(!markerAssetRepository.existsById(Long.valueOf(resourceId))){
			throw new NoSuchElementException();
		}

		// Check 3: Do we have 3 markers and are they “marker”+{1-3}
		if(!(markers.containsKey("marker1") && markers.containsKey("marker2") && markers.containsKey("marker3")) && markers.size() != 3){
			throw new InvalidParameterException("3 markers are required (Iset, Fset, Fset3)");
		}

		// List containing markers already saved to delete them if unsuccessful
		List<Marker> savedMarkers = new ArrayList<>();

		for(int i = 0 ; i<3 ; i++){
			try{
				Marker newMarker = new Marker();
				newMarker.setName(name + "." + "marker"+ (i+1) );
				byte[] marker = markers.get("marker"+ (i+1));
				markerAssetRepository.save(newMarker);
				FileSystemManager.writeGenericMarkers(newMarker.getId().toString(), marker);
				savedMarkers.add(newMarker); // Adds the marker to the list of saved markers
			} catch (Exception e) {
				// Delete all markers that have been saved
				for (Marker savedMarker : savedMarkers) {
					markerAssetRepository.delete(savedMarker);
					FileSystemManager.deleteGenericMarkers(savedMarker.getId().toString());
				}
				throw new IOException(e);
			}
		}
	}

	@Override
	@Transactional
	public void uploadSound(String resourceId, String name, byte[] sound) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		try {
			AudioFileFormat audio = AudioSystem.getAudioFileFormat(new ByteArrayInputStream(sound));
			if(!soundAssetReposetory.existsById(Long.valueOf(resourceId))){
				throw new  NoSuchElementException();
			}
			SoundAsset soundAsset = new SoundAsset();
			soundAsset.setName(name);
			soundAssetReposetory.save(soundAsset);
			FileSystemManager.writeSound(soundAsset.getId().toString(),sound);
		} catch (UnsupportedAudioFileException e) {
			throw new InvalidParameterException("audio type not supported");
		}
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
