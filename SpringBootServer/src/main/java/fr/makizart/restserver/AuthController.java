package fr.makizart.restserver;

import fr.makizart.common.storageservice.dto.AuthRequestDTO;
import fr.makizart.common.storageservice.dto.AuthResponseDTO;
import fr.makizart.restserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        var resp = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(resp);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequestDTO request) {
        var response = authService.register(request.getUsername(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        var auth = SecurityContextHolder.getContext().getAuthentication(); // returns null

        var response = Map.of(
                "authenticated", auth.isAuthenticated(),
                "principal", auth.getPrincipal(),
                "authorities", auth.getAuthorities(),
                "name", auth.getName(),
                "details", auth.getDetails()
        );

        return ResponseEntity.ok(response);
    }

}