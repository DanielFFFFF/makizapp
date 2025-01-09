package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ImageAssetTest {

    @Test
    void testConstructorAndGetters() throws Exception {
        // Create an instance using the constructor
        URI pathToResource = new URI("http://example.com/resource");
        ImageAsset imageAsset = new ImageAsset(pathToResource);

        // Verify the constructor correctly sets the field
        assertEquals(pathToResource, imageAsset.getPathToRessource(), "Path to resource should match");
    }

    @Test
    void testSetPathToRessource() throws Exception {
        // Create an instance
        ImageAsset imageAsset = new ImageAsset();

        // Set the path to resource
        URI pathToResource = new URI("http://example.com/resource");
        imageAsset.setPathToRessource(pathToResource);

        // Verify the path was set correctly
        assertEquals(pathToResource, imageAsset.getPathToRessource(), "Path to resource should match");
    }

    @Test
    void testSetData() {
        // Create an instance
        ImageAsset imageAsset = new ImageAsset();

        // Set image data
        byte[] imageData = {1, 2, 3, 4, 5};
        imageAsset.setData(imageData);

        // Verify the data was set correctly
        assertArrayEquals(imageData, imageAsset.getData(), "Image data should match");
    }

    @Test
    void testDefaultConstructor() {
        // Create an instance using the default constructor
        ImageAsset imageAsset = new ImageAsset();

        // Verify the fields are initialized to null
        assertNull(imageAsset.getPathToRessource(), "Path to resource should be null by default");
        assertNull(imageAsset.getData(), "Image data should be null by default");
    }
}
