package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MediaTest {

    // Helper class to test the abstract Media class
    static class MediaImpl extends Media {
    }

    @Test
    void testSetAndGetId() {
        // Create an instance of the Media implementation
        Media media = new MediaImpl();

        // Set the ID
        UUID id = UUID.randomUUID();
        media.setId(id);

        // Verify the ID was set correctly
        assertEquals(id, media.getId(), "ID should match the set value");
    }

    @Test
    void testSetAndGetName() {
        // Create an instance of the Media implementation
        Media media = new MediaImpl();

        // Set the name
        String name = "Test Media";
        media.setName(name);

        // Verify the name was set correctly
        assertEquals(name, media.getName(), "Name should match the set value");
    }
}
