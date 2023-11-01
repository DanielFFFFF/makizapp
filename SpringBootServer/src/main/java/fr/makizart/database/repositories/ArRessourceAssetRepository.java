package fr.makizart.database.repositories;

import fr.makizart.database.table.ArRessource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArRessourceAssetRepository extends JpaRepository<ArRessource, Long> {
}
