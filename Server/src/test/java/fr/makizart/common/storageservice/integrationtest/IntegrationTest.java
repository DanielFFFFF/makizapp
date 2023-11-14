package fr.makizart.common.storageservice.integrationtest;


import fr.makizart.common.database.repositories.*;
import fr.makizart.common.database.table.Project;
import fr.makizart.common.storageservice.SimpleStorageService;
import jakarta.activation.DataSource;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Arrays;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@EnableAutoConfiguration
@EnableTransactionManagement
@EnableJpaRepositories("fr.makizart")
@EntityScan("fr.makizart")
@ComponentScan("fr.makizart")
@SpringBootTest(classes = IntegrationTest.config.class)
@Transactional
class IntegrationTest {


    @Configuration
    public static class config{

    }

    public final ProjectRepository projectRepository;

    public final ArResourceAssetRepository arResourceRepository;

    public final ImageAssetRepository imageAssetRepository;

    public final VideoAssetRepository videoAssetRepository;
    public final SoundAssetReposetory soundAssetReposetory;

    public final MarkerAssetRepository markerAssetRepository;

    public final SimpleStorageService simpleStorageService;

    public IntegrationTest(@Autowired ProjectRepository projectRepository,
                           @Autowired ArResourceAssetRepository arResourceRepository,
                           @Autowired MarkerAssetRepository markerAssetRepository,
                           @Autowired ImageAssetRepository imageAssetRepository,
                           @Autowired VideoAssetRepository videoAssetRepository,
                           @Autowired SoundAssetReposetory soundAssetReposetory,
                           @Autowired SimpleStorageService simpleStorageService) {
        this.projectRepository = projectRepository;
        this.arResourceRepository = arResourceRepository;
        this.imageAssetRepository = imageAssetRepository;
        this.videoAssetRepository = videoAssetRepository;
        this.soundAssetReposetory = soundAssetReposetory;
        this.markerAssetRepository = markerAssetRepository;
        this.simpleStorageService = simpleStorageService;
    }

    @Test
    void testGet() {
        int nbProjects = 10;
        Project[] projects = new Project[10];
        for (int i = 0; i < nbProjects ; i++) {
            projects[i] = new Project("p" + i);
            projects[i] = projectRepository.save(projects[i]);
        }

        var page = simpleStorageService.getProjects(0,1);
        Assertions.assertEquals(page.getSize(),1);
        Assertions.assertArrayEquals(new Object[]{Arrays.stream(projects).toArray()[0]},page.getContent().toArray());


    }
}