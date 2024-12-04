package fr.makizart.common.storageservice.unitest;

import fr.makizart.common.storageservice.FileSystemManager;
import fr.makizart.common.storageservice.dto.MarkerDTO;
import fr.makizart.common.storageservice.dto.StorageInformationDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileSystemManagerTest {

    /*
    private static final String TEST_PATH = System.getProperty("java.io.tmpdir");

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(Paths.get(TEST_PATH));
        FileSystemManager.PATH = TEST_PATH;
    }

    @Test
    void testWriteImage() throws IOException {
        String imageName = "testImage.png";
        byte[] imageData = "dummyImageData".getBytes();

        Path result = FileSystemManager.writeImage(imageName, imageData);

        assertTrue(Files.exists(result.resolve(imageName)));
        assertArrayEquals(imageData, Files.readAllBytes(result.resolve(imageName)));
    }

    @Test
    void testDeleteImage() throws IOException {
        String imageName = "testImage.png";
        byte[] imageData = "dummyImageData".getBytes();

        Path result = FileSystemManager.writeImage(imageName, imageData);
        assertTrue(Files.exists(result.resolve(imageName)));

        FileSystemManager.deleteImage(imageName);
        assertFalse(Files.exists(result.resolve(imageName)));
    }

    @Test
    void testWriteGenericMarkers() throws IOException {
        String resourceId = "resource123";
        MarkerDTO markerDTO = mock(MarkerDTO.class);
        when(markerDTO.marker1()).thenReturn("marker1Data");
        when(markerDTO.marker2()).thenReturn("marker2Data");
        when(markerDTO.marker3()).thenReturn("marker3Data");

        Map<String, Path> result = FileSystemManager.writeGenericMarkers(resourceId, markerDTO);

        assertTrue(Files.exists(result.get("marker1").resolve("m.iset")));
        assertTrue(Files.exists(result.get("marker2").resolve("m.fset")));
        assertTrue(Files.exists(result.get("marker3").resolve("m.fset3")));

        assertEquals("marker1Data", Files.readString(result.get("marker1").resolve("m.iset")));
        assertEquals("marker2Data", Files.readString(result.get("marker2").resolve("m.fset")));
        assertEquals("marker3Data", Files.readString(result.get("marker3").resolve("m.fset3")));
    }

    @Test
    void testDeleteMarker() throws IOException {
        String resourceId = "resource123";
        MarkerDTO markerDTO = mock(MarkerDTO.class);
        when(markerDTO.marker1()).thenReturn("marker1Data");
        when(markerDTO.marker2()).thenReturn("marker2Data");
        when(markerDTO.marker3()).thenReturn("marker3Data");

        FileSystemManager.writeGenericMarkers(resourceId, markerDTO);
        FileSystemManager.deleteMarker(resourceId);

        Path resourcePath = Paths.get(TEST_PATH, "MARKERS", resourceId);
        assertFalse(Files.exists(resourcePath));
    }

    @Test
    void testGetDiskSpace() throws IOException {
        StorageInformationDTO diskSpace = FileSystemManager.getDiskSpace();

        assertNotNull(diskSpace);
        assertTrue(diskSpace.total() > 0);
        assertTrue(diskSpace.used() >= 0);
    }

    @Test
    void testWriteSound() throws IOException {
        String soundName = "testSound.wav";
        byte[] soundData = "dummySoundData".getBytes();

        Path result = FileSystemManager.writeSound(soundName, soundData);

        assertTrue(Files.exists(result.resolve(soundName)));
        assertArrayEquals(soundData, Files.readAllBytes(result.resolve(soundName)));
    }

    @Test
    void testDeleteSound() throws IOException {
        String soundName = "testSound.wav";
        byte[] soundData = "dummySoundData".getBytes();

        Path result = FileSystemManager.writeSound(soundName, soundData);
        assertTrue(Files.exists(result.resolve(soundName)));

        FileSystemManager.deleteSound(soundName);
        assertFalse(Files.exists(result.resolve(soundName)));
    }
     */
}
