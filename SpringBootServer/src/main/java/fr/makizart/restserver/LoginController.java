package fr.makizart.restserver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@CrossOrigin(origins = "http://172.18.0.1:8080") // Autoriser les requêtes de cette origine
@RequestMapping("/api")
public class LoginController {



    @PostMapping("/login")
    public void login() {
        // This endpoint is handled by JwtAuthenticationFilter
       System.out.println("Requête reçue au serveur : /api/login");


    }


}