package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.ARjsMarker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerAssetRepository extends JpaRepository<ARjsMarker, Long> {
}
