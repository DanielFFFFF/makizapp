package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;

import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class VideoAssetTest {

    @Test
    void testSetAndGetVideoURL() throws Exception {
        // Create an instance
        VideoAsset videoAsset = new VideoAsset();

        // Set the video URL
        URL videoURL = new URL("http://example.com/video");
        videoAsset.setVideoURL(videoURL);

        // Verify the video URL was set correctly
        assertEquals(videoURL, videoAsset.getVideoURL(), "Video URL should match the set value");
    }

    @Test
    void testDefaultConstructor() {
        // Create an instance using the default constructor
        VideoAsset videoAsset = new VideoAsset();

        // Verify the video URL is initially null
        assertNull(videoAsset.getVideoURL(), "Video URL should be null by default");
    }
}
