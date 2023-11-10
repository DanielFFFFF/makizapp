package fr.makizart.common.storageservice;

import fr.makizart.common.database.repositories.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SimpleStorageServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ArRessourceAssetRepository arRessourceRepository;

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
        List<Long> id  = new ArrayList<>(3);
        id.add(1L);
        id.add(3L);
        id.add(2L);
        when(projectRepository.findAllID()).thenReturn(id);

        String[] res = new String[]{"1", "3", "2"};
        Assertions.assertArrayEquals(res,storageService.getProject().toArray());
    }
}