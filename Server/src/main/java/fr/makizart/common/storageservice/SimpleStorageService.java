package fr.makizart.common.storageservice;

import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.*;
import fr.makizart.common.storageservice.dto.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.regex.Pattern;

@Component
@Transactional
public class SimpleStorageService implements StorageService {

	private final ProjectRepository projectRepository;

	private final ArResourceAssetRepository arResourceRepository;

	private final ImageAssetRepository imageAssetRepository;

	private final VideoAssetRepository videoAssetRepository;
	private final SoundAssetReposetory soundAssetReposetory;

	private final MarkerAssetRepository markerAssetRepository;


	final Pattern invalidName = Pattern.compile("[^-_.A-Za-z0-9]");


	public SimpleStorageService(
			@Autowired ProjectRepository projectRepository,
			@Autowired ArResourceAssetRepository arResourceRepository,
			@Autowired MarkerAssetRepository markerAssetRepository,
			@Autowired ImageAssetRepository imageAssetRepository,
			@Autowired VideoAssetRepository videoAssetRepository,
			@Autowired SoundAssetReposetory soundAssetReposetory,
			@Autowired FileSystemManager fileSystemManager) {
		this.projectRepository = projectRepository;
		this.arResourceRepository = arResourceRepository;
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
	public List<ArResourceDTO> getResourcesInProject(String projectId) throws InvalidParameterException, NoSuchElementException {
		List<ArResourceDTO> list = new ArrayList<>();
		for(ArResource rs : tryGetProject(projectId).getArResource()){
			list.add(new ArResourceDTO(rs));
		}
		return list;
	}

	@Override
	public ArResourceDTO getResource(String resourceId) throws InvalidParameterException, NoSuchElementException {
		return new ArResourceDTO(tryGetResource(resourceId));
	}

	@Override
	public StorageInformationDTO getStorageInformation() throws IOException {
		return FileSystemManager.getDiskSpace();
	}

	@Override
	public String getVideoURL(Long id) {
		return videoAssetRepository.getReferenceById(id).getVideoURL().toString();
	}

	@Override
	@Transactional
	public void overrideThumbnail(String resourceId, String name, String thumbnail) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		ArResource resource = tryGetResource(resourceId);
		try {
			saveImage(thumbnail, resource);
		}catch(IOException e){
			throw new InvalidParameterException("Can't create thumbnail");
		}
	}

	private void saveImage(String thumbnail, ArResource resource) throws IOException {
		ImageAsset image = new ImageAsset();
		imageAssetRepository.save(image);//id created
		Path path = FileSystemManager.writeImage(image.getId().toString(), Base64.getDecoder().decode(thumbnail));
		image.setPathToRessource(path.toUri());
		resource.setThumbnail(image);
		arResourceRepository.save(resource);
	}


	@Override
	@Transactional
	public void overrideMarkers(String resourceId, String name, String marker1, String marker2, String marker3) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		// Check 1: Is the name valid?
		validateName(name);

		ArResource resource = tryGetResource(resourceId);

		// Check 2: Do we have 3 markers and are they “marker”+{1-3}
		if(marker1 == null || marker2 == null || marker3 == null){
			throw new InvalidParameterException("3 markers are required (Iset, Fset, Fset3)");
		}
		try {
			FileSystemManager.writeGenericMarkers(new MarkerDTO(resource.getMarkers().getId(),name,marker1,marker2,marker3));
		}catch (IOException e){
			//hide error to end-user
			throw new IOException("Write failed");
		}

	}


	@Override
	@Transactional
	public void overrideSound(String resourceId, String name, String sound) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		ArResource resource = tryGetResource(resourceId);
		try {
			SoundAsset audio = new SoundAsset();
			soundAssetReposetory.save(audio);//id created
			Path path = FileSystemManager.writeImage(audio.getId().toString(), Base64.getDecoder().decode(sound));
			audio.setPathToRessource(path.toUri());
			resource.setSoundAsset(audio);
			arResourceRepository.save(resource);
		}catch(IOException e){
			throw new InvalidParameterException("Can't create sound");
		}
	}

	@Override
	public void overrideVideo(String resourceId, String name, String url) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		ArResource res = tryGetResource(resourceId);
		try {
			res.getVideoAsset().setVideoURL(URI.create(url).toURL());
			arResourceRepository.save(res);
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
		arResourceRepository.save(resource);
	}

	@Override
	public void deleteProject(String projectId) throws InvalidParameterException, NoSuchElementException {
		projectRepository.delete(tryGetProject(projectId));
	}

	@Override
	public void deleteResource(String projectId, String resourceId) throws InvalidParameterException, NoSuchElementException {
		ArResource res = tryGetResource(resourceId);
		Project p = tryGetProject(projectId);
		p.removeResource(res);
		projectRepository.save(p);
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
		ArResource resource = new ArResource();
		resource.setName(incomingResourceDTO.name());

		boolean atLeastOneAsset = incomingResourceDTO.imageAsset() == null && incomingResourceDTO.soundAsset() == null && incomingResourceDTO.videoAsset() == null;
		boolean noVideoAndSoundOrImage = (incomingResourceDTO.imageAsset() != null || incomingResourceDTO.soundAsset() != null) && (incomingResourceDTO.videoAsset() != null);
		if (atLeastOneAsset){
			throw new IllegalStateException("Cannot create resource without asset");
		}
		if (noVideoAndSoundOrImage) {
			throw new IllegalStateException("(ImageAsset, SoundAsset) and videoAsset are mutually exclusive.");
		}

		try {
			ImageAsset thumbnail = new ImageAsset();
			imageAssetRepository.save(thumbnail);//id created
			Path path = FileSystemManager.writeImage(thumbnail.getId().toString(), Base64.getDecoder().decode(incomingResourceDTO.thumbnail()));
			thumbnail.setPathToRessource(path.toUri());
			resource.setThumbnail(thumbnail);
		}catch(IOException e){
			throw new InvalidParameterException("Can't create thumbnail");
		}

		try {
			ARjsMarker markers = new ARjsMarker();
			markerAssetRepository.save(markers);
			MarkerDTO markerDTO = new MarkerDTO(markers.getId(),markers.getId().toString(),incomingResourceDTO.marker1(),incomingResourceDTO.marker2(),incomingResourceDTO.marker3()); //(Long id, String name, String marker1, String marker2, String marker3)
			Map<String,Path> paths = FileSystemManager.writeGenericMarkers(markerDTO);
			markers.setMarker1Path(paths.get("marker1").toUri());
			markers.setMarker2Path(paths.get("marker2").toUri());
			markers.setMarker3Path(paths.get("marker3").toUri());
			resource.setMarkers(markers);
		}catch(IOException e){
			throw new InvalidParameterException("Can't create markers");
		}

		if(incomingResourceDTO.videoAsset() == null) {
			try {
				ImageAsset image = new ImageAsset();
				imageAssetRepository.save(image);//id created
				Path path = FileSystemManager.writeImage(image.getId().toString(), Base64.getDecoder().decode(incomingResourceDTO.imageAsset()));
				image.setPathToRessource(path.toUri());
				resource.setImageAsset(image);
			} catch (IOException e) {
				throw new InvalidParameterException("Can't create image");
			}

			try {
				SoundAsset sound = new SoundAsset();
				soundAssetReposetory.save(sound);//id created
				Path path = FileSystemManager.writeSound(sound.getId().toString(), Base64.getDecoder().decode(incomingResourceDTO.soundAsset()));
				sound.setPathToRessource(path.toUri());
				resource.setSoundAsset(sound);
			} catch (IOException e) {
				throw new InvalidParameterException("Can't create sound");
			}
		} else {
			try {
				VideoAsset video = new VideoAsset();
				videoAssetRepository.save(video);//id created
				video.setVideoURL(URI.create(incomingResourceDTO.videoAsset()).toURL());
				resource.setVideoAsset(video);
			} catch (IOException e) {
				throw new InvalidParameterException("Can't create video");
			}
		}

		Project p = tryGetProject(projectId);
		p.addResource(resource);
		projectRepository.save(p);
		return new ArResourceDTO(resource);
	}

	@Override
	public void overrideImage(String resourceId, String name, String image) throws InvalidParameterException, IOException, NameAlreadyBoundException {
		validateName(name);
		ArResource resource = tryGetResource(resourceId);
		try {
			saveImage(image, resource);
		}catch(IOException e){
			throw new InvalidParameterException("Can't create image");
		}
	}


	private void validateName(String newName) {
		if(invalidName.matcher(newName).find())
			throw new InvalidParameterException();
	}

	private ImageAsset tryGetImage(String imageId) {
		try {
			return imageAssetRepository.getReferenceById(Long.valueOf(imageId));
		}catch (NumberFormatException e){
			throw new InvalidParameterException();
		}catch (EntityNotFoundException e){
			throw new NoSuchElementException();
		}
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
			return arResourceRepository.getReferenceById(Long.valueOf(resourceID));
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
