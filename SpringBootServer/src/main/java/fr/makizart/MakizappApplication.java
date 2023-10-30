package fr.makizart;

import fr.makizart.data.ImageAsset;
import fr.makizart.data.Project;
import fr.makizart.data.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class MakizappApplication {

	@Autowired
	private ProjectRepository projectRepository;

	public static void main(String[] args) {
		SpringApplication.run(MakizappApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runAfterStartup() {
			Project asset = new Project();
			asset.setName("foo");
			projectRepository.save(asset);
			System.out.println(projectRepository.findAll().toString());

	}

}
