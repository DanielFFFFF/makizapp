package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class SoundAssetTest {

    @Test
    void testSetAndGetPathToRessource() throws Exception {
        // Create an instance
        SoundAsset soundAsset = new SoundAsset();

        // Set the path to resource
        URI pathToResource = new URI("http://example.com/sound");
        soundAsset.setPathToRessource(pathToResource);

        // Verify the path was set correctly
        assertEquals(pathToResource, soundAsset.getPathToRessource(), "Path to resource should match the set value");
    }

    @Test
    void testDefaultConstructor() {
        // Create an instance using the default constructor
        SoundAsset soundAsset = new SoundAsset();

        // Verify the path to resource is initially null
        assertNull(soundAsset.getPathToRessource(), "Path to resource should be null by default");
    }
}
