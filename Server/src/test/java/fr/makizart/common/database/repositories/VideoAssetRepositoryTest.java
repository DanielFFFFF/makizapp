/*package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.VideoAsset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.net.URL;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VideoAssetRepositoryTest {

    @Autowired
    private VideoAssetRepository videoAssetRepository;

    @Test
    void testSaveAndFindById() throws Exception {
        // Create a VideoAsset instance
        VideoAsset videoAsset = new VideoAsset();
        URL videoURL = new URL("http://example.com/video");
        videoAsset.setVideoURL(videoURL);

        // Save the VideoAsset to the repository
        VideoAsset savedVideoAsset = videoAssetRepository.save(videoAsset);

        // Verify it is saved and retrievable by ID
        Optional<VideoAsset> retrievedVideoAsset = videoAssetRepository.findById(savedVideoAsset.getId());
        assertTrue(retrievedVideoAsset.isPresent(), "VideoAsset should be found by ID");
        assertEquals(savedVideoAsset.getId(), retrievedVideoAsset.get().getId(), "Saved VideoAsset ID should match the retrieved ID");
        assertEquals(videoURL, retrievedVideoAsset.get().getVideoURL(), "Video URL should match the saved URL");
    }

    @Test
    void testFindByIdNotFound() {
        // Try to find a VideoAsset by a non-existing ID
        UUID nonExistingId = UUID.randomUUID();
        Optional<VideoAsset> retrievedVideoAsset = videoAssetRepository.findById(nonExistingId);

        // Verify it is not found
        assertFalse(retrievedVideoAsset.isPresent(), "VideoAsset should not be found with a non-existing ID");
    }

    @Test
    void testSaveNullVideoURL() {
        // Create a VideoAsset instance with null video URL
        VideoAsset videoAsset = new VideoAsset();
        videoAsset.setVideoURL(null);

        // Save the VideoAsset to the repository
        VideoAsset savedVideoAsset = videoAssetRepository.save(videoAsset);

        // Verify the saved VideoAsset has null URL
        Optional<VideoAsset> retrievedVideoAsset = videoAssetRepository.findById(savedVideoAsset.getId());
        assertTrue(retrievedVideoAsset.isPresent(), "VideoAsset should be found by ID");
        assertNull(retrievedVideoAsset.get().getVideoURL(), "Video URL should be null for this VideoAsset");
    }
}
*/