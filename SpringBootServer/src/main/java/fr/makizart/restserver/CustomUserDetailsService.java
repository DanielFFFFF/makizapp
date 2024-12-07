package fr.makizart.restserver;

import fr.makizart.common.database.table.Utilisateur;
import fr.makizart.common.database.repositories.UserRepository;
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
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = userRepository.findByUsername(username);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // Initialize the list of authorities
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        // Add the ROLE_ADMIN authority if the user has the ADMIN role
        if ("ADMIN".equalsIgnoreCase(utilisateur.getRole())) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        // Convert existing authorities to SimpleGrantedAuthority, if they exist
        if (utilisateur.getAuthorities() != null) {
            authorities.addAll(utilisateur.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
                    .collect(Collectors.toList()));
        }

        return new org.springframework.security.core.userdetails.User(
                utilisateur.getUsername(),
                utilisateur.getPassword(),
                authorities
        );
    }
}