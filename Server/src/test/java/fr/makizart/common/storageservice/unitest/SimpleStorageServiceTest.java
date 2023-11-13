package fr.makizart.common.storageservice.unitest;

import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.SimpleStorageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

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
}