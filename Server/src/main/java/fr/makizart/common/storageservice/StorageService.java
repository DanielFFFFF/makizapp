package fr.makizart.common.storageservice;


import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.dto.*;
import org.springframework.data.domain.Page;

import javax.naming.NameAlreadyBoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface StorageService  {


	Page<Project> getProject(int pageNumber, int pageSize);
	ProjectDTO getProject(String projectId)throws NoSuchElementException,InvalidParameterException;

	List<String> getResourcesInProject(String projectId) throws InvalidParameterException,NoSuchElementException;

	ArResourceDTO getResource(String resourceId) throws InvalidParameterException,NoSuchElementException;

	StorageInformationDTO getStorageInformation();

	void uploadMarkers(String resourceId, String name, Map<String, byte[]> markers)
			throws InvalidParameterException, NoSuchElementException, IOException, NameAlreadyBoundException;

	void uploadSound(String resourceId, String name, byte[] sound)
			throws InvalidParameterException,NoSuchElementException, IOException, NameAlreadyBoundException;

	void uploadVideo(String resourceId, String name, String url)
			throws InvalidParameterException,NoSuchElementException, IOException, NameAlreadyBoundException;

	void renameProject(String projectId, String newName)
			throws InvalidParameterException, NoSuchElementException;

	void renameResource(String resourceId, String newName)
			throws InvalidParameterException, NoSuchElementException;

	void deleteProject(String projectId)
			throws InvalidParameterException, NoSuchElementException;

	void deleteResource(String resourceId)
			throws InvalidParameterException, NoSuchElementException;

	IdDTO createProject(String name) throws InvalidParameterException, NameAlreadyBoundException;

	ArResourceDTO createResource(String projectId, IncomingResourceDTO incomingResourceDTO) throws InvalidParameterException, NameAlreadyBoundException;

	void uploadImage(String resourceId, String name, byte[] image)
			throws InvalidParameterException, IOException, NameAlreadyBoundException;

	void uploadTrackedImage(String resourceId, String name, byte[] trackedImage)
			throws InvalidParameterException, IOException, NameAlreadyBoundException;
}
