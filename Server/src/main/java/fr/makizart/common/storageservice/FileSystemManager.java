package fr.makizart.common.storageservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileSystemManager {

    private static String PATH = "/var/lib/MAKIZART/data/";

    static {
        try {
            Files.createDirectories(Paths.get(PATH));
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
     * @param id The id of the saved resource.
     * @param data     The byte array representing the data to be written.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    private static void writeFile(String id, FileType type , byte[] data) throws IOException {
        Path filePath = Paths.get(PATH, id,".",type.name() );
        Files.write(filePath, data);
    }

    /**
     * Example usage of writing an image file.
     *
     * @param imageName The name of the image file.
     * @param imageData The byte array representing the image data.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    public static void writeImage(String imageName, byte[] imageData) throws IOException {
        writeFile(imageName, FileType.IMAGE , imageData);
    }

    /**
     * Example usage of writing a sound file.
     *
     * @param soundName The name of the sound file.
     * @param soundData The byte array representing the sound data.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    public static void writeSound(String soundName, byte[] soundData) throws IOException {
        writeFile(soundName, FileType.SOUND , soundData);
    }

    /**
     * Example usage of writing a generic file with random byte sequence.
     *
     * @param fileName  The name of the generic file.
     * @param fileData  The byte array representing the file data.
     * @throws IOException If an I/O error occurs during the file writing process.
     */
    public static void writeGenericMarkers(String fileName, byte[] fileData) throws IOException {
        writeFile(fileName, FileType.MARKERS , fileData);
    }

    /**
     * Reads data from a file.
     *
     * @param id   The id of the resource.
     * @param type The type of the file (e.g., IMAGE, SOUND, MARKERS).
     * @return The byte array representing the data read from the file.
     * @throws IOException If an I/O error occurs during the file reading process.
     */
    private static byte[] readFile(String id, FileType type) throws IOException {
        Path filePath = Paths.get(PATH, id + "." + type.name());
        return Files.readAllBytes(filePath);
    }

    /**
     * Example usage of reading an image file.
     *
     * @param imageName The name of the image file.
     * @return The byte array representing the image data.
     * @throws IOException If an I/O error occurs during the file reading process.
     */
    public static byte[] readImage(String imageName) throws IOException {
        return readFile(imageName, FileType.IMAGE);
    }

    /**
     * Example usage of reading a sound file.
     *
     * @param soundName The name of the sound file.
     * @return The byte array representing the sound data.
     * @throws IOException If an I/O error occurs during the file reading process.
     */
    public static byte[] readSound(String soundName) throws IOException {
        return readFile(soundName, FileType.SOUND);
    }

    /**
     * Example usage of reading a generic file with random byte sequence.
     *
     * @param fileName The name of the generic file.
     * @return The byte array representing the file data.
     * @throws IOException If an I/O error occurs during the file reading process.
     */
    public static byte[] readGenericMarkers(String fileName) throws IOException {
        return readFile(fileName, FileType.MARKERS);
    }

    /**
     * Deletes a file.
     *
     * @param id The id of the saved resource.
     * @throws IOException If an I/O error occurs during the file deletion process.
     */
    private static void deleteFile(String id, FileType type) throws IOException {
        Path filePath = Paths.get(PATH, id + "." + type.name());
        Files.delete(filePath);
    }

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
     * Example usage of deleting a generic file with random byte sequence.
     *
     * @param fileName The name of the generic file.
     * @throws IOException If an I/O error occurs during the file deletion process.
     */
    public static void deleteGenericMarkers(String fileName) throws IOException {
        deleteFile(fileName, FileType.MARKERS);
    }




}
