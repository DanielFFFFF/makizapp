package fr.makizart.restserver;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

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
