package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.Markers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarkerAssetRepository extends JpaRepository<Markers, Long> {
}
