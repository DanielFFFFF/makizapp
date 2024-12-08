package fr.makizart.restserver;

import fr.makizart.common.database.table.Utilisateur;
import fr.makizart.common.database.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findByUsername(username).orElseThrow();

        if (utilisateur.getRole() == null)
            throw new UsernameNotFoundException("User not found");

        var user =  new Utilisateur();
        user.setUsername(utilisateur.getUsername());
        user.setPassword(utilisateur.getPassword());
        user.setRole(utilisateur.getRole());
        return user;
    }
}