package fr.makizart.common.storageservice;


import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.dto.*;
import org.springframework.data.domain.Page;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface StorageService  {



	/**
	 * Retrieves a page of projects.
	 *
	 * @param pageNumber The page number.
	 * @param pageSize   The size of the page.
	 * @return A Page containing Project entities.
	 */
	Page<Project> getProjects(int pageNumber, int pageSize);

	/**
	 * Retrieves details of a project by its ID.
	 *
	 * @param projectId The ID of the project.
	 * @return A ProjectDTO representing the project details.
	 * @throws NoSuchElementException    If the id is not a number.
	 * @throws InvalidParameterException  If the provided parameters are invalid.
	 */
	ProjectDTO getProject(String projectId) throws NoSuchElementException, InvalidParameterException;

	/**
	 * Return a boolean that said if the project is in the database or not
	 *
	 * @param projectId The ID of the project.
	 * @return A boolean that said if the project is in the database or not
	 */
	boolean projectExists(String projectId);

    /**
	 * Retrieves a list of resource names in a project.
	 *
	 * @param projectId The ID of the project.
	 * @return A List of resource .
	 * @throws InvalidParameterException If the id is not a number.
	 * @throws NoSuchElementException    If the project with the given ID is not found.
	 */
	List<ArResourceDTO> getResourcesInProject(String projectId) throws InvalidParameterException, NoSuchElementException;

	/**
	 * Retrieves details of a resource by its ID.
	 *
	 * @param resourceId  The ID of the resource, a long int encoded as a string.
	 * @return An ArResourceDTO representing the resource details.
	 * @throws InvalidParameterException If the id is not a number.
	 * @throws NoSuchElementException    If the resource with the given ID is not found.
	 */
	ArResourceDTO getResource(String resourceId) throws InvalidParameterException, NoSuchElementException;

	/**
	 * Retrieves storage information.
	 *
	 * @return A StorageInformationDTO containing storage-related information.
	 */
	StorageInformationDTO getStorageInformation() throws IOException;

	/**
	 * Return a video stored in database by its id
	 * @param id the id of the video
	 * @return The video url
	 */
	String getVideoURL(UUID id);

	/**
	 * Uploads markers for a resource.
	 *
	 * @param resourceId  The ID of the resource, a long int encoded as a string.
	 * @param marker1	The marker 1 of the resource.
	 * @param marker2	The marker 2 of the resource.
	 * @param marker3	The marker 3 of the resource.
	 * @throws InvalidParameterException  If the id is not a number, the name contain invalid character. Note that Marker are not preprocessed on this method
	 * @throws NoSuchElementException     If the resource with the given ID is not found.
	 * @throws IOException               If an I/O error occurs.
	 * @throws NameAlreadyBoundException  If the name is already bound.
	 */
	void overrideMarkers(String resourceId, String name, String marker1, String marker2, String marker3)
			throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException;


	/**
	 * Uploads sound for a resource.
	 *
	 * @param resourceId  The ID of the resource, a long int encoded as a string.
	 * @param name       The name of the sound.
	 * @param sound      The byte array representing the sound data.
	 * @throws InvalidParameterException  If the id is not a number, the name contain invalid character, or the sound cannot be parsed
	 * @throws NoSuchElementException     If the resource with the given ID is not found.
	 * @throws IOException               If an I/O error occurs.
	 * @throws NameAlreadyBoundException  If the name is already bound.
	 */
	void overrideSound(String resourceId, String name, String sound)
			throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException;

	/**
	 * Uploads video for a resource.
	 *
	 * @param resourceId  The ID of the resource, a long int encoded as a string.
	 * @param name       The n	@Override
	 * @param url        The URL of the video.
	 * @throws InvalidParameterException  If the id is not a number, the name contain invalid character, or the url cannot be parsed
	 * @throws NoSuchElementException     If the resource with the given ID is not found.
	 * @throws IOException               If an I/O error occurs.
	 * @throws NameAlreadyBoundException  If the name is already bound.
	 */
	void overrideVideo(String resourceId, String name, String url) throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException;

	/**
	 * Renames a project.
	 *
	 * @param projectId The ID of the project to be renamed.
	 * @param newName    The new name for the project.
	 * @throws InvalidParameterException  If the id is not a number.
	 * @throws NoSuchElementException     If the project with the given ID is not found.
	 * @throws NameAlreadyBoundException  If the name is already exist for the project.
	 */
	void renameProject(String projectId, String newName)
			throws InvalidParameterException, NoSuchElementException, NameAlreadyBoundException;

	/**
	 * Renames a resource.
	 *
	 * @param resourceId The ID of the resource to be renamed.
	 * @param newName    The new name for the resource.
	 * @throws InvalidParameterException  IIf the id is not a number.
	 * @throws NoSuchElementException     If the resource with the given ID is not found.
	 */
	void renameResource(String resourceId, String newName)
			throws InvalidParameterException, NoSuchElementException;

	/**
	 * Deletes a project.
	 *
	 * @param projectId The ID of the project to be deleted, a long int encoded as a string.
	 * @throws InvalidParameterException  If the id is not a number.
	 * @throws NoSuchElementException     If the project with the given ID is not found.
	 */
	void deleteProject(String projectId)
			throws InvalidParameterException, NoSuchElementException;

	/**
	 * Deletes a resource.
	 *
	 * @param resourceId he ID of the resource to be deleted, a long int encoded as a string.
	 * @throws InvalidParameterException  If the id is not a number.
	 * @throws NoSuchElementException     If the resource with the given ID is not found.
	 */
	void deleteResource(String projectId, String resourceId)
			throws InvalidParameterException, NoSuchElementException;

	/**
	 * Creates a new project.
	 *
	 * @param name The name of the new project.
	 * @return An IdDTO containing the ID of the newly created project.
	 * @throws InvalidParameterException   If the id is not a number.
	 * @throws NameAlreadyBoundException  If the name is already exist for the project
	 */
	IdDTO createProject(String name) throws InvalidParameterException, NameAlreadyBoundException;

	/**
     * Creates a new resource in a project.
     *
     * @param projectId           The ID of the project, a long int encoded as a string.
     * @param incomingResourceDTO The DTO containing information about the new resource.
     * @return An ArResourceDTO containing information about the newly created resource.
     * @throws InvalidParameterException If the provided parameters are invalid.
     * @throws NameAlreadyBoundException If the name is already exist for the resource.
     */
	IdDTO createResource(String projectId, IncomingResourceDTO incomingResourceDTO)
            throws InvalidParameterException, NameAlreadyBoundException, IOException;


	/**
	 * Create a new json file with settings of a video
	 * @param projectId -> ID of the project, a long int encoded as a string.
	 * @param settings -> The DTO containing information about the new settings.
	 * @return the json with the settings
	 * @throws IOException If we can't create a dirctory or save the settings
	 */
	String createSettings(String projectId, VideoSettingsDTO settings) throws IOException;


	/**
	 * Uploads an image for a resource.
	 *
	 * @param resourceId  The ID of the resource, a long int encoded as a string.
	 * @param name       The name of the image.
	 * @param image      The byte array representing the image data.
	 * @throws InvalidParameterException   If the id is not a number, the name contain invalid character, or the image cannot be parsed
	 * @throws IOException               If an I/O error occurs.
	 * @throws NameAlreadyBoundException  If the name is already exist for the resource.
	 */
	void overrideImage(String resourceId, String name, String image)
			throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException;

	/**
	 * Uploads an thumbnail for a resource.
	 *
	 * @param resourceId  The ID of the resource, a long int encoded as a string.
	 * @param name       The name of the thumbnail.
	 * @param thumbnail      The byte array representing the thumbnail data.
	 * @throws InvalidParameterException   If the id is not a number, the name contain invalid character, or the image cannot be parsed
	 * @throws IOException               If an I/O error occurs.
	 * @throws NameAlreadyBoundException  If the name is already exist for the resource.
	 */
	void overrideThumbnail(String resourceId, String name, String thumbnail)
			throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException;


    byte[] getThumbnail(String resourceID);



}

