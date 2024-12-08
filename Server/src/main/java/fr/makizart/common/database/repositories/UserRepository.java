package fr.makizart.common.database.repositories;
import fr.makizart.common.database.table.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByUsername(String username); // Select * from utilisateurs where username = ?
    boolean existsByUsername(String username); // Select count(*) from utilisateurs where username = ?
}
