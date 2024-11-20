package fr.makizart.common.storageservice;

import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.*;
import fr.makizart.common.storageservice.dto.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.naming.NameAlreadyBoundException;
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
	public boolean projectExists(String projectId) {
		try {
			return projectRepository.existsById(UUID.fromString(projectId));
		} catch (Exception e) {
			return false;
		}
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
	public String getVideoURL(UUID id) {
		return videoAssetRepository.getReferenceById(id).getVideoURL().toString();
	}

	@Override
	@Transactional
	public void overrideThumbnail(String resourceId, String name, String thumbnail) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		ArResource resource = tryGetResource(resourceId);
		Objects.requireNonNull(resource.getThumbnail());
		//Override file system content, nothing to change in db
		try {
			FileSystemManager.deleteImage(resource.getThumbnail().getId().toString());
			saveImage(thumbnail, resource);
		}catch(IOException e){
			throw new InvalidParameterException("Can't create thumbnail");
		}
	}

	@Override
	public byte[] getThumbnail(String resourceID) {
		ArResource resource = tryGetResource(resourceID);
		Objects.requireNonNull(resource.getThumbnail());
		return resource.getThumbnail().getData();
	}


	@Override
	public byte[] getMarker1(String resourceID) {
		ArResource resource = tryGetResource(resourceID);
		Objects.requireNonNull(resource.getMarkers());
		return resource.getMarkers().getMarkerData1();
	}

	@Override
	public byte[] getMarker2(String resourceID) {
		ArResource resource = tryGetResource(resourceID);
		Objects.requireNonNull(resource.getMarkers());
		return resource.getMarkers().getMarkerData1();
	}

	@Override
	public byte[] getMarker3(String resourceID) {
		ArResource resource = tryGetResource(resourceID);
		Objects.requireNonNull(resource.getMarkers());
		return resource.getMarkers().getMarkerData1();
	}



	private void saveImage(String thumbnail, ArResource resource) throws IOException {
		ImageAsset image = new ImageAsset();
		imageAssetRepository.save(image);//id created
		image.setData(Base64.getDecoder().decode(thumbnail));
		resource.setThumbnail(image);
		arResourceRepository.save(resource);
	}


	@Override
	@Transactional
	public void overrideMarkers(String resourceId, String name, String marker1, String marker2, String marker3) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		// Check 1: Is the name valid?
		validateName(name);

		ArResource resource = tryGetResource(resourceId);

		var decoder=Base64.getDecoder();

		// Check 2: Do we have 3 markers and are they “marker”+{1-3}
		if(marker1 == null || marker2 == null || marker3 == null){
			throw new InvalidParameterException("3 markers are required (Iset, Fset, Fset3)");
		}
			FileSystemManager.deleteMarker(resource.getMarkers().getId().toString());
			FileSystemManager.writeGenericMarkers(resourceId, new MarkerDTO(resource.getId(),name,marker1,marker2,marker3));
	}


	@Override
	@Transactional
	public void overrideSound(String resourceId, String name, String sound) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException {
		validateName(name);
		ArResource resource = tryGetResource(resourceId);
		try {
			SoundAsset audio = new SoundAsset();
			soundAssetReposetory.save(audio);//id created
			FileSystemManager.deleteSound(resource.getSoundAsset().getId().toString());
			Path path = FileSystemManager.writeSound(audio.getId().toString(), Base64.getDecoder().decode(sound));
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
		return new IdDTO(project.getId());
	}

	@Override
	public ArResourceDTO createResource(String projectId, IncomingResourceDTO incomingResourceDTO)
			throws InvalidParameterException, NameAlreadyBoundException {

		// Create a new AR resource object*
		ArResource resource = new ArResource();

		// Generate a random UUID for the resource and set its ID
		UUID id = UUID.randomUUID();
		resource.setId(id);

		// Set the resource's name based on the incoming DTO
		resource.setName(incomingResourceDTO.name());

		// Check if there are no assets (image, sound, video); an asset must be present
		boolean atLeastOneAsset = incomingResourceDTO.imageAsset() == null
				&& incomingResourceDTO.soundAsset() == null
				&& incomingResourceDTO.videoAsset() == null;

		// Check if both (image or sound) and video are provided, which is invalid
		boolean noVideoAndSoundOrImage = (incomingResourceDTO.imageAsset() != null
				|| incomingResourceDTO.soundAsset() != null)
				&& (incomingResourceDTO.videoAsset() != null);

		// If no asset is provided, throw an exception
		if (atLeastOneAsset) {
			throw new IllegalStateException("Cannot create resource without asset");
		}

		// If both video and (image or sound) are present, throw an exception
		if (noVideoAndSoundOrImage) {
			throw new IllegalStateException("(ImageAsset, SoundAsset) and videoAsset are mutually exclusive.");
		}

		// Ensure that markers (marker1, marker2, marker3) are not null
		Objects.requireNonNull(incomingResourceDTO.marker1(), "Marker1 must not be null");
		Objects.requireNonNull(incomingResourceDTO.marker2(), "Marker2 must not be null");
		Objects.requireNonNull(incomingResourceDTO.marker3(), "Marker3 must not be null");

		// Retrieve the project by its ID, throwing an error if it doesn't exist
		Project project = tryGetProject(projectId);

		// Try to save the thumbnail image asset and assign it to the resource

			ImageAsset thumbnail = new ImageAsset();
			thumbnail.setData(Base64.getDecoder().decode(incomingResourceDTO.thumbnail()));
			imageAssetRepository.save(thumbnail); // ID is created here



			//Path path = FileSystemManager.writeImage(thumbnail.getId().toString(),
			//		);


			resource.setThumbnail(thumbnail);


		// Save marker data (marker1, marker2, marker3) as part of the resource
        ARjsMarker markers = new ARjsMarker();
        markerAssetRepository.save(markers); // ID is created here
        MarkerDTO markerDTO = new MarkerDTO(
                markers.getId(),
                markers.getId().toString(),
                incomingResourceDTO.marker1(),
                incomingResourceDTO.marker2(),
                incomingResourceDTO.marker3()
        ); // Create marker DTO with marker data

        // Write markers to file system and set marker paths in the resource

			/*
			Map<String, Path> paths = FileSystemManager.writeGenericMarkers(id.toString(), markerDTO);
			markers.setMarker1Path(paths.get("marker1").toUri());
			markers.setMarker2Path(paths.get("marker2").toUri());
			markers.setMarker3Path(paths.get("marker3").toUri());
			resource.setMarkers(markers);
			 */

        markers.setMarkerData1(Base64.getDecoder().decode(incomingResourceDTO.marker1()));
		markers.setMarkerData2(Base64.getDecoder().decode(incomingResourceDTO.marker2()));
		markers.setMarkerData3(Base64.getDecoder().decode(incomingResourceDTO.marker3()));

		resource.setMarkers(markers);

        // If no video asset is present, handle image and/or sound assets
		if (incomingResourceDTO.videoAsset() == null) {
			// Try to save the image asset and assign it to the resource
			try {
				ImageAsset image = new ImageAsset();
				imageAssetRepository.save(image); // ID is created here
				Path path = FileSystemManager.writeImage(image.getId().toString(),
						Base64.getDecoder().decode(incomingResourceDTO.imageAsset()));
				image.setPathToRessource(path.toUri());
				resource.setImageAsset(image);
			} catch (IOException e) {
				throw new InvalidParameterException("Can't create image");
			}

			// If a sound asset is present, save and assign it to the resource
			if (incomingResourceDTO.soundAsset() != null) {
				try {
					SoundAsset sound = new SoundAsset();
					soundAssetReposetory.save(sound); // ID is created here
					Path path = FileSystemManager.writeSound(sound.getId().toString(),
							Base64.getDecoder().decode(incomingResourceDTO.soundAsset()));
					sound.setPathToRessource(path.toUri());
					resource.setSoundAsset(sound);
				} catch (IOException e) {
					throw new InvalidParameterException("Can't create sound");
				}
			}
		}
		// If a video asset is present, save it and assign it to the resource
		else {
			try {
				VideoAsset video = new VideoAsset();
				videoAssetRepository.save(video); // ID is created here
				video.setVideoURL(URI.create(incomingResourceDTO.videoAsset()).toURL());
				resource.setVideoAsset(video);
			} catch (IOException e) {
				throw new InvalidParameterException("Can't create video");
			}
		}

		// Add the created resource to the project and save the project
		project.addResource(resource);
		projectRepository.save(project);

		// Return the created resource as a DTO
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

	private Project tryGetProject(String projectId) {
			return projectRepository.findById(UUID.fromString(projectId)).orElseThrow();
	}

	private ArResource tryGetResource(String resourceID) {
		return arResourceRepository.findById(UUID.fromString(resourceID)).orElseThrow();
	}

	private VideoAsset tryGetVideo(String resourceID) {
			return videoAssetRepository.findById(UUID.fromString(resourceID)).orElseThrow();
	}

}
