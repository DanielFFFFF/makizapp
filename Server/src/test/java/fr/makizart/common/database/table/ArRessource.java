package fr.makizart.common.database.table;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ArResourceTest {

    /*
    private static EntityManagerFactory emf;
    private EntityManager em;

    @BeforeAll
    static void setupEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("test-pu");
    }

    @BeforeEach
    void setupEntityManager() {
        em = emf.createEntityManager();
    }

    @AfterEach
    void tearDownEntityManager() {
        if (em != null) {
            em.close();
        }
    }

    @AfterAll
    static void closeEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void testValidateWithNoAssets() {
        ArResource resource = createValidResource();
        resource.setImageAsset(null);
        resource.setSoundAsset(null);
        resource.setVideoAsset(null);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            persistResource(resource);
        });

        assertEquals("Cannot create resource without asset", exception.getMessage());
    }

    @Test
    void testValidateWithBothImageAndVideo() {
        ArResource resource = createValidResource();
        resource.setImageAsset(new ImageAsset());
        resource.setVideoAsset(new VideoAsset());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            persistResource(resource);
        });

        assertEquals("(ImageAsset, SoundAsset) and videoAsset are mutually exclusive.", exception.getMessage());
    }

    @Test
    void testValidateWithValidImageAsset() {
        ArResource resource = createValidResource();
        resource.setVideoAsset(null); // Only image asset is set

        assertDoesNotThrow(() -> persistResource(resource));
    }

    @Test
    void testValidateWithValidVideoAsset() {
        ArResource resource = createValidResource();
        resource.setImageAsset(null); // Only video asset is set

        assertDoesNotThrow(() -> persistResource(resource));
    }

    private ArResource createValidResource() {
        ArResource resource = new ArResource();
        resource.setId(UUID.randomUUID());
        resource.setName("Test Resource");
        resource.setThumbnail(new ImageAsset());
        resource.setMarkers(new ARjsMarker());
        resource.setImageAsset(new ImageAsset()); // Default valid asset
        return resource;
    }

    private void persistResource(ArResource resource) {
        em.getTransaction().begin();
        em.persist(resource);
        em.getTransaction().commit();
    }
    */
}
