package fr.makizart.restserver.services;

import fr.makizart.common.database.repositories.UserRepository;
import fr.makizart.common.database.table.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Utilisateur findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public void compact(Utilisateur user) {
        user.setPassword(null);
    }

}
