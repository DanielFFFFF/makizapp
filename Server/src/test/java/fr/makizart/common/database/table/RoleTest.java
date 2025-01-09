package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    @Test
    void testSetAndGetId() {
        // Create an instance
        Role role = new Role();

        // Set the ID
        Long id = 1L;
        role.setId(id);

        // Verify the ID was set correctly
        assertEquals(id, role.getId(), "ID should match the set value");
    }

    @Test
    void testSetAndGetName() {
        // Create an instance
        Role role = new Role();

        // Set the name
        String roleName = "Admin";
        role.setName(roleName);

        // Verify the name was set correctly
        assertEquals(roleName, role.getName(), "Name should match the set value");
    }

    @Test
    void testDefaultConstructor() {
        // Create an instance using the default constructor
        Role role = new Role();

        // Verify the fields are initialized to null
        assertNull(role.getId(), "ID should be null by default");
        assertNull(role.getName(), "Name should be null by default");
    }
}
