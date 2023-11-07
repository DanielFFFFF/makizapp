package fr.makizart.database.repositories;

import fr.makizart.database.table.Marker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerAssetRepository extends JpaRepository<Marker, Long> {
}
