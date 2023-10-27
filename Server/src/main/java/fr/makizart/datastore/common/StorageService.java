package fr.makizart.datastore.common;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface StorageService  {

	List<String> getProjects();

	Map<String, List<String>> getProjectComposition(String projectId);

	List<Map<String, String>> getAllMediaNameInProject(String projectId);

	File getLocallyStoredImage(String projectId, String mediaId);

	File getLocallyStoredSound( String projectId, String mediaId);

	String createProject( String projectId);

	String getProjectMetadata( String projectId);

	String deleteProject(String projectId);

	String renameProject( String projectId,  String newName);

	void saveRemoteVideo(String projectId,  String pictureId,  String videoData);


	void saveImage( String projectId, String pictureId,  Image imageData);

	void saveSound( String projectId, String pictureId,  byte[] soundData);

	void renameMedia( String projectId,  String mediaId,  String newName) throws NoSuchElementException;

	String deleteMedia( String projectId,  String mediaId) throws NoSuchElementException;
}
