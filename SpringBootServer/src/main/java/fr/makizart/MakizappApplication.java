package fr.makizart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "fr.makizart.datastore")
public class MakizappApplication {


	public static void main(String[] args) {
		SpringApplication.run(MakizappApplication.class, args);
	}

}
