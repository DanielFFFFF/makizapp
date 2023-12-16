package fr.makizart.common.storageservice;

import fr.makizart.common.storageservice.dto.MarkerDTO;
import fr.makizart.common.storageservice.dto.StorageInformationDTO;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Component
public class FileSystemManager {

    //private static String PATH = "/var/lib/MAKIZART/data/";
    public static final String PATH = System.getProperty("user.home")+"/"+".makizart/resources";

    static {
        try {
            Files.createDirectories(Paths.get(PATH));

            for(FileType type : FileType.values()){
                Path filePath = Paths.get(PATH, type.name());
                Files.createDirectories(filePath);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private enum FileType {
        SOUND,
        IMAGE,
        MARKERS
    }

    /**
     * Writes data to a file.
     *
     * @param fileName The fileName of the saved resource.
     * @param data     The byte array representing the data to be written.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    private static Path writeFile(String fileName, FileType type , byte[] data) throws IOException {
        Path filePath = Paths.get(PATH, type.name());
        Files.write(Path.of(String.valueOf(filePath),fileName), data);
        return filePath;
    }

    private static Path deleteFile(String fileName, FileType type) throws IOException {
        Path filePath = Paths.get(PATH, type.name());
        Files.delete(Path.of(String.valueOf(filePath),fileName));
        return filePath;
    }

    private static Path writeFile(String directory,String fileName, FileType type , byte[] data) throws IOException {
        Path dirPath = Paths.get(PATH, type.name(),directory);
        Files.createDirectories(dirPath);
        Files.write(Path.of(String.valueOf(dirPath),fileName), data);
        return dirPath;
    }


    private static void deleteAllFileInDir(String id, FileType type) throws IOException {
        File dirToNuke = Paths.get(PATH, type.name()).toFile();
        FileSystemUtils.deleteRecursively(dirToNuke);
    }

    /**
     * Example usage of writing an image file.
     *
     * @param imageName The name of the image file.
     * @param imageData The byte array representing the image data.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    public static Path writeImage(String imageName, byte[] imageData) throws IOException {
        return writeFile(imageName, FileType.IMAGE , imageData);
    }

    /**
     * Example usage of writing a sound file.
     *
     * @param soundName The name of the sound file.
     * @param soundData The byte array representing the sound data.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    public static Path writeSound(String soundName, byte[] soundData) throws IOException {
        return writeFile(soundName, FileType.SOUND , soundData);
    }

    /**
     * Example usage of writing a generic file with random byte sequence.
     *
     * @param resourceId
     * @param dto        the marker dto to write to disk
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    public static Map<String,Path> writeGenericMarkers(String resourceId, MarkerDTO dto) throws IOException {
        Map<String,Path> paths = new HashMap<>();
        try {
            paths.put("marker1",writeFile(  resourceId,"m.iset", FileType.MARKERS , dto.marker1().getBytes()));
            paths.put("marker2",writeFile(resourceId,"m.fset", FileType.MARKERS , dto.marker2().getBytes()));
            paths.put("marker3",writeFile(resourceId,"m.fset3", FileType.MARKERS , dto.marker3().getBytes()));
            return paths;
        }catch (IOException e){
            //try to avoid half written state by deleting all the markers

            deleteMarker(resourceId);
            throw e;
        }

    }

    /**
     * Delete a marker set
     * @throws IOException If an I/O error occurs during the file deletion process.
     */
    public static void deleteMarker(String resourceId) throws IOException {
        deleteAllFileInDir(resourceId, FileType.MARKERS);
    }

    /**
     * Deletes a file.
     *
     * @param id The id of the saved resource.
     * @throws IOException If an I/O error occurs during the file deletion process.
     */

    /**
     * Example usage of deleting an image file.
     *
     * @param imageName The name of the image file.
     * @throws IOException If an I/O error occurs during the file deletion process.
     */
    public static void deleteImage(String imageName) throws IOException {
        deleteFile(imageName, FileType.IMAGE);
    }

    /**
     * Example usage of deleting a sound file.
     *
     * @param soundName The name of the sound file.
     * @throws IOException If an I/O error occurs during the file deletion process.
     */
    public static void deleteSound(String soundName) throws IOException {
        deleteFile(soundName, FileType.SOUND);
    }


    /**
     * Returns the total and used disk space.
     *
     * This method uses the java.nio.file.FileStore class to get disk space information.
     * It returns a map with two entries: "total" for the total disk space, and "used" for the used disk space.
     * If an IOException occurs, it returns a map with "total" and "used" set to 0.
     * @return A map with the total and used disk space.
     */
    public static StorageInformationDTO getDiskSpace() throws IOException {
            // Get the FileStore representing the file system
            FileStore store = Files.getFileStore(Paths.get(PATH));

            // Get the total and usable (free) space
            long totalSpace = store.getTotalSpace();
            long usableSpace = store.getUsableSpace();

            // Calculate the used space
            long usedSpace = totalSpace - usableSpace;

            // Put the total and used space in the map
            return new StorageInformationDTO(usedSpace,totalSpace);

    }

}
