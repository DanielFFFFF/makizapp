package fr.makizart.common.storageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data // Getters, setters, equals, hashcode, toString
@AllArgsConstructor // Constructeur avec tous les arguments
public class AuthRequestDTO {
    private String username;
    private String password;
}

 /*
   {
   "username": "admin",
    "password": "admin"
    }
     */
