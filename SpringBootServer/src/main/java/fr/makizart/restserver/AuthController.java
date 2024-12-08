package fr.makizart.restserver;

import fr.makizart.common.storageservice.dto.AuthRequestDTO;
import fr.makizart.common.storageservice.dto.AuthResponseDTO;
import fr.makizart.restserver.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO request) {
        var token = authService.login(request.getUsername(), request.getPassword());
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }


}