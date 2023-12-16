package fr.makizart.restserver;

import fr.makizart.common.storageservice.FileSystemManager;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("fr.makizart")
@EntityScan("fr.makizart")
@EnableAutoConfiguration
@ComponentScan("fr.makizart")
public class Config implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("IMAGE/**", "MARKER/**", "SOUND/**")
                    .addResourceLocations("file:" +FileSystemManager.PATH);
    }
}
