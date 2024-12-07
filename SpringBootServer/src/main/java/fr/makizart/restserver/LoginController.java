package fr.makizart.restserver;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


@RestController
@RequestMapping("/api")
public class LoginController {



    @PostMapping("/login")
    public void login() {
        // This endpoint is handled by JwtAuthenticationFilter
       System.out.println("Requête reçue au serveur : /api/login");


    }


}