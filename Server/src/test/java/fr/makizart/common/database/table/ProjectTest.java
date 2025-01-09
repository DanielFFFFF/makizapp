package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void testConstructorAndGetters() {
        // Create an instance using the constructor
        String projectName = "Test Project";
        Project project = new Project(projectName);

        // Verify the constructor correctly sets the name and initializes arResource
        assertEquals(projectName, project.getName(), "Project name should match");
        assertNotNull(project.getArResource(), "arResource should be initialized as an empty list");
        assertTrue(project.getArResource().isEmpty(), "arResource should be empty initially");
    }

    @Test
    void testSetName() {
        // Create an instance
        Project project = new Project();

        // Set a new name
        String projectName = "Updated Project";
        project.setName(projectName);

        // Verify the name was set correctly
        assertEquals(projectName, project.getName(), "Project name should be updated");
    }

    @Test
    void testAddResource() {
        // Create an instance
        Project project = new Project();

        // Create and add a resource
        ArResource resource = new ArResource(); // Assuming ArResource class exists
        project.addResource(resource);

        // Verify the resource is added to the arResource list
        assertEquals(1, project.getArResource().size(), "arResource should contain one resource");
        assertTrue(project.getArResource().contains(resource), "arResource should contain the added resource");
    }

    @Test
    void testRemoveResource() {
        // Create an instance
        Project project = new Project();

        // Create and add a resource
        ArResource resource = new ArResource(); // Assuming ArResource class exists
        project.addResource(resource);

        // Remove the resource
        project.removeResource(resource);

        // Verify the resource is removed from the arResource list
        assertTrue(project.getArResource().isEmpty(), "arResource should be empty after removal");
    }

    /*
    @Test
    void testEqualsAndHashCode() {
        // Create two Project instances with the same id
        UUID projectId = UUID.randomUUID();
        Project project1 = new Project("Test Project");
        project1.setId(projectId);
        Project project2 = new Project("Test Project");
        project2.setId(projectId);

        // Test equals method
        assertEquals(project1, project2, "Projects with the same id should be equal");

        // Test hashCode method
        assertEquals(project1.hashCode(), project2.hashCode(), "Projects with the same id should have the same hashCode");
    }*/

    @Test
    void testToString() {
        // Create a Project instance
        Project project = new Project("Test Project");

        // Check the toString output contains the expected details
        String expected = "Project{id=null, arResource=[], name='Test Project'}";
        assertTrue(project.toString().contains("Test Project"), "toString should contain project name");
    }
}
