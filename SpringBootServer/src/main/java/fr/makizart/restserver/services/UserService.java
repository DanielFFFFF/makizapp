package fr.makizart.restserver.services;

import fr.makizart.common.database.repositories.UserRepository;
import fr.makizart.common.database.table.Utilisateur;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Utilisateur findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    public Utilisateur findById(Long ID) {
        return userRepository.findById(ID).orElseThrow();
    }


    public void registerUser(Utilisateur utilisateur) {
        if (existsByUsername(utilisateur.getUsername()))
            throw new RuntimeException("User already exists");
        userRepository.save(utilisateur);
    }

    /**
     * Enable a user
     * @param ID the ID of the user
     */
    public void enableUser(Long ID) {
        var user = findById(ID);
        user.setEnabled(true);
        userRepository.save(user);
    }
    
    /**
     * Disable a user
     * @param ID the ID of the user
     */
    public void disableUser(Long ID) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var user = (Utilisateur) auth.getPrincipal();
        if (ID.equals(user.getId()))
            throw new RuntimeException("You cannot disable yourself");
        user = findById(ID);
        user.setEnabled(false);
        userRepository.save(user);
    }

    /**
     * Get all users from the database.
     * The current user is removed from the list in order to avoid self-deletion.
     * @return a list of all users
     */
    public List<Utilisateur> getAllUsers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var users = userRepository.findAll();
        var actualUser = (Utilisateur) auth.getPrincipal();
        users.removeIf(user -> user.getId().equals(actualUser.getId()));
        return users;
    }
}
