package fr.makizart.common.storageservice.unitest;

import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.ArResource;
import fr.makizart.common.database.table.ImageAsset;
import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.SimpleStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.*;


import static org.mockito.Mockito.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleStorageServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ArResourceAssetRepository arRessourceRepository;

    @Mock
    private ImageAssetRepository imageAssetRepository;

    @Mock
    private VideoAssetRepository videoAssetRepository;

    @Mock
    private SoundAssetReposetory soundAssetReposetory;

    @InjectMocks
    private SimpleStorageService storageService;


    @BeforeEach
    public void setUp() {

    }

    @Test
    void testGetAllReturnAllProject() {
        List<Project> projeeeeeeeets  = new ArrayList<>(3);
        projeeeeeeeets.add(new Project("a"));
        projeeeeeeeets.add(new Project("b"));
        projeeeeeeeets.add(new Project("c"));
        when(projectRepository.findAll(Mockito.isA(Pageable.class))).thenReturn(new PageImpl<>(projeeeeeeeets));

        Assertions.assertArrayEquals(projeeeeeeeets.toArray(),storageService.getProjects(1,1).stream().toArray());
    }



    @Mock
    private ArResourceAssetRepository arResourceRepository;

    @InjectMocks
    private SimpleStorageService simpleStorageService;

    @Test
    public void testSaveImage() throws IOException {
        // Arrange
        String thumbnail = Base64.getEncoder().encodeToString("sampleImageData".getBytes());
        ArResource resource = new ArResource();

        // Mock behavior for imageAssetRepository.save()
        ImageAsset imageAsset = new ImageAsset();
        when(imageAssetRepository.save(any(ImageAsset.class))).thenReturn(imageAsset);

        // Act
        // Use reflection to invoke the private method
        try {
            java.lang.reflect.Method method = SimpleStorageService.class.getDeclaredMethod("saveImage", String.class, ArResource.class);
            method.setAccessible(true);
            method.invoke(simpleStorageService, thumbnail, resource);
        } catch (ReflectiveOperationException e) {
            fail("Failed to invoke the private method: " + e.getMessage());
        }

        // Assert
        assertNotNull(resource.getThumbnail());
        assertArrayEquals(Base64.getDecoder().decode(thumbnail), resource.getThumbnail().getData());
        verify(imageAssetRepository, times(1)).save(any(ImageAsset.class));
        verify(arResourceRepository, times(1)).save(resource);
    }






}
