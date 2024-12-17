package fr.makizart.restserver;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/user") // Accessible par tout le monde
public class UserController {

    @GetMapping("/test")
    public String testAccess() {
        return "Access granted to resource `/api/user/test`";
    }
}
