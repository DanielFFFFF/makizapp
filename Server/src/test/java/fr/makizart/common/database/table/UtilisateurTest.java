package fr.makizart.common.database.table;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UtilisateurTest {

    @Test
    void testSetAndGetId() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Set the ID
        Long id = 1L;
        utilisateur.setId(id);

        // Verify the ID was set correctly
        assertEquals(id, utilisateur.getId(), "ID should match the set value");
    }

    @Test
    void testSetAndGetUsername() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Set the username
        String username = "testuser";
        utilisateur.setUsername(username);

        // Verify the username was set correctly
        assertEquals(username, utilisateur.getUsername(), "Username should match the set value");
    }

    @Test
    void testSetAndGetPassword() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Set the password
        String password = "password123";
        utilisateur.setPassword(password);

        // Verify the password was set correctly
        assertEquals(password, utilisateur.getPassword(), "Password should match the set value");
    }

    @Test
    void testSetAndGetRole() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Set the role
        String role = "ADMIN";
        utilisateur.setRole(role);

        // Verify the role was set correctly
        assertEquals(role, utilisateur.getRole(), "Role should match the set value");
    }

    @Test
    void testSetAndGetEnabled() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Set the enabled status
        boolean enabled = true;
        utilisateur.setEnabled(enabled);

        // Verify the enabled status was set correctly
        assertTrue(utilisateur.isEnabled(), "User should be enabled");
    }

    @Test
    void testGetAuthorities() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setRole("ADMIN");

        // Get the authorities
        Collection<? extends GrantedAuthority> authorities = utilisateur.getAuthorities();

        // Verify the authorities collection contains the expected role
        assertEquals(1, authorities.size(), "Authorities should contain exactly one role");
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN")), "Authorities should contain 'ADMIN' role");
    }

    @Test
    void testAccountNonExpired() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Verify account non-expired status
        assertTrue(utilisateur.isAccountNonExpired(), "Account should be non-expired");
    }

    @Test
    void testAccountNonLocked() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Verify account non-locked status
        assertTrue(utilisateur.isAccountNonLocked(), "Account should be non-locked");
    }

    @Test
    void testCredentialsNonExpired() {
        // Create an instance
        Utilisateur utilisateur = new Utilisateur();

        // Verify credentials non-expired status
        assertTrue(utilisateur.isCredentialsNonExpired(), "Credentials should be non-expired");
    }

}
