package fr.makizart;

import fr.makizart.database.repositories.ArRessourceAssetRepository;
import fr.makizart.database.repositories.ImageAssetRepository;
import fr.makizart.database.repositories.VideoAssetRepository;
import fr.makizart.database.table.ArRessource;
import fr.makizart.database.table.ImageAsset;
import fr.makizart.database.table.Project;
import fr.makizart.database.repositories.ProjectRepository;
import fr.makizart.database.table.VideoAsset;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@SpringBootApplication
public class MakizappApplication {

	private final ArRessourceAssetRepository arRessourceAssetRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private ImageAssetRepository imageAssetRepository
			;
	@Autowired
	private VideoAssetRepository videoAssetRepository;

	public MakizappApplication(ArRessourceAssetRepository arRessourceAssetRepository) {
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
		ArRessource arRessource = new ArRessource();
		arRessource.setPathToMarkers(new URI("file://test"));
/*		arRessourceAssetRepository.save(arRessource);*/


		asset.setName("foo");
		asset.getArRessource().add(arRessource);
		arRessource.setImageAsset(imageAsset);
		arRessource.setVideoAsset(videoAsset);

		projectRepository.save(asset);
		System.out.println(projectRepository.findAll());

		var project = projectRepository.findAll().get(0);
		System.out.println(project.getArRessource().get(0).getVideoAsset().getVideoURL());

	}

}
