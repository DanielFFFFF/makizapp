package fr.makizart.restserver;

import fr.makizart.common.database.repositories.ArResourceAssetRepository;
import fr.makizart.common.database.repositories.ImageAssetRepository;
import fr.makizart.common.database.repositories.VideoAssetRepository;
import fr.makizart.common.database.table.ArResource;
import fr.makizart.common.database.table.ImageAsset;
import fr.makizart.common.database.table.Project;
import fr.makizart.common.database.repositories.ProjectRepository;
import fr.makizart.common.database.table.VideoAsset;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class MakizappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MakizappApplication.class, args);
	}


}
