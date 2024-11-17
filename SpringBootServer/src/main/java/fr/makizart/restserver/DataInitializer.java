package fr.makizart.restserver;
import fr.makizart.common.database.table.Utilisateur;
import fr.makizart.common.database.repositories.UserRepository;
import fr.makizart.common.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Autowired
    private UserRepository utilisateurRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Code de création de l'utilisateur admin commenté pour le désactiver
        /*
        if (utilisateurRepository.findByUsername("admin") == null) {
            Utilisateur admin = new Utilisateur();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole("ADMIN");
            utilisateurRepository.save(admin);
            System.out.println("Utilisateur admin créé avec succès.");
        }
        */
        };
    }

}

