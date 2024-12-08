package fr.makizart.restserver.services;

import fr.makizart.common.database.table.Utilisateur;
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
    public String login(String username, String passwordFromRequest) {
        Utilisateur userInDatabase = userService.findByUsername(username);

        if (!passwordEncoder.matches(passwordFromRequest, userInDatabase.getPassword()))
            throw new RuntimeException("Invalid credentials");

        return jwtService.generateToken(userInDatabase);
    }

}
