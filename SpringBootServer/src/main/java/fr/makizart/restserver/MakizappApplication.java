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

	private final ArResourceAssetRepository arRessourceAssetRepository;

	@Autowired
	public ProjectRepository projectRepository;
	@Autowired
	public ImageAssetRepository imageAssetRepository;
	@Autowired
	public VideoAssetRepository videoAssetRepository;

	public MakizappApplication(ArResourceAssetRepository arRessourceAssetRepository) {
		this.arRessourceAssetRepository = arRessourceAssetRepository;
	}


	public static void main(String[] args) {
		SpringApplication.run(MakizappApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	@Transactional
	public void runAfterStartup() throws URISyntaxException, MalformedURLException {


		VideoAsset videoAsset = new VideoAsset();
		videoAsset.setVideoURL(new URI("file://test").toURL());
		ImageAsset imageAsset = new ImageAsset();
		imageAsset.setPathToRessource(new URI("file://test"));

/*		videoAssetRepository.save(videoAsset);
		imageAssetRepository.save(imageAsset);*/

		Project asset = new Project();
		ArResource arResource = new ArResource();
/*		arRessourceAssetRepository.save(arRessource);*/


		asset.setName("foo");
		asset.getArResource().add(arResource);
		arResource.setVideoAsset(videoAsset);

		projectRepository.save(asset);
		System.out.println(projectRepository.findAll());

		var project = projectRepository.findAll().get(0);
		System.out.println(project.getArResource().get(0).getVideoAsset().getVideoURL());

	}

}
