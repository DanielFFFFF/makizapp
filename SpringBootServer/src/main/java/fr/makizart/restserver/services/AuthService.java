package fr.makizart.restserver.services;

import fr.makizart.common.database.table.Utilisateur;
import fr.makizart.common.storageservice.dto.AuthResponseDTO;
import fr.makizart.common.storageservice.dto.SingleMessageDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Return a JWT token if the user exists and the password is correct
    public AuthResponseDTO login(String username, String passwordFromRequest) {
        Utilisateur userInDatabase = userService.findUserByUsername(username);

        if (!passwordEncoder.matches(passwordFromRequest, userInDatabase.getPassword()))
            throw new RuntimeException("Invalid credentials");

        String token = jwtService.generateToken(userInDatabase);
        return new AuthResponseDTO(token, userInDatabase.isEnabled());
    }

    public SingleMessageDTO register(String username, String password) {
        Utilisateur user = new Utilisateur();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("ADMIN");
        // user.setEnabled(true);
        userService.registerUser(user);
        return new SingleMessageDTO("User registration sent to the admin for approval");
    }
}
