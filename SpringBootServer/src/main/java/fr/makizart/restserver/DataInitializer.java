package fr.makizart.restserver;

import fr.makizart.common.database.repositories.UserRepository;
import fr.makizart.common.database.table.Utilisateur;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private final UserRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        this.utilisateurRepository = utilisateurRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Code de création de l'utilisateur admin commenté pour le désactiver

            if (utilisateurRepository.findByUsername("admin") == null) {
                Utilisateur admin = new Utilisateur();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("pass"));
                admin.setRole("ADMIN");
                utilisateurRepository.save(admin);
                System.out.println("Utilisateur admin créé avec succès.");
            }

        };
    }
}
