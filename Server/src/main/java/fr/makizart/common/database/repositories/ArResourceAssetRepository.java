package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.ArResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArResourceAssetRepository extends JpaRepository<ArResource, Long> {
}
