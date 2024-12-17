package fr.makizart.restserver;

import fr.makizart.common.storageservice.FileSystemManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("fr.makizart")
@EntityScan("fr.makizart")
@EnableAutoConfiguration
@ComponentScan("fr.makizart")
public class Config implements WebMvcConfigurer {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files from the 'mind-markers' folder at the same level as the 'src' directory
        registry.addResourceHandler("/markers/**")
                .addResourceLocations("file:./SpringBootServer/mind-markers/markers/") // Use relative path to the 'mind-markers' folder
                .setCachePeriod(0)
                .resourceChain(true)
                .addResolver(new PathResourceResolver()); // Resolver for serving files from the file system
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
