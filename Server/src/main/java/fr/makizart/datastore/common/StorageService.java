package fr.makizart.datastore.common;

import javax.naming.NameAlreadyBoundException;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface StorageService  {

	List<String> getProjects();

	Map<String, List<String>> getProjectComposition(String projectId) throws NoSuchElementException;

	List<Map<String, String>> getAllMediaNameInProject(String projectId) throws NoSuchElementException;

	File getLocallyStoredImage(String projectId, String mediaId) throws NoSuchElementException;

	File getLocallyStoredSound( String projectId, String mediaId) throws NoSuchElementException;

	/**
	 * @param projectName id of the project to be created in the persistent storage
	 * @return tuple containing, id of the matching project
	 * boolean representing if a new project was created
	 */
	String createProject(String projectName) throws NameAlreadyBoundException;

	String getProjectMetadata( String projectId)throws NoSuchElementException;;

	boolean deleteProject(String projectId)throws NoSuchElementException;;

	String renameProject( String projectId,  String newName)throws NoSuchElementException,InvalidParameterException, NameAlreadyBoundException;

	void saveRemoteVideo(String projectId,  String pictureId,  URL videoUrl) throws NoSuchElementException, InvalidParameterException, NameAlreadyBoundException;


	void saveImage( String projectId, String pictureId,  Image imageData) throws InvalidParameterException, NoSuchElementException, NameAlreadyBoundException;

	void saveSound( String projectId, String pictureId,  byte[] soundData)throws InvalidParameterException, NoSuchElementException, NameAlreadyBoundException;

	void saveSound(String projectId, String pictureId, ByteArrayInputStream inputStream) throws InvalidParameterException, NoSuchElementException;

	void renameMedia(String projectId, String mediaId, String newName) throws NoSuchElementException, InvalidParameterException, NameAlreadyBoundException;

	String deleteMedia( String projectId,  String mediaId) throws NoSuchElementException, InvalidParameterException;
}
