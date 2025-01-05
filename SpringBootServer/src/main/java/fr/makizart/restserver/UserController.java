package fr.makizart.restserver;

import fr.makizart.common.database.table.Utilisateur;
import fr.makizart.common.storageservice.dto.SingleMessageDTO;
import fr.makizart.restserver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/enable/{ID}")
    public ResponseEntity<SingleMessageDTO> enableUser(@PathVariable("ID") Long ID) {
        userService.enableUser(ID);
        return ResponseEntity.ok(new SingleMessageDTO("User enabled successfully"));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/disable/{ID}")
    public ResponseEntity<SingleMessageDTO> disableUser(@PathVariable("ID") Long ID) {
        userService.disableUser(ID);
        return ResponseEntity.ok(new SingleMessageDTO("User disabled successfully"));
    }
    
    @GetMapping("/all")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    
}
