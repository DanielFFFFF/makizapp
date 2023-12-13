package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.ArResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArResourceAssetRepository extends JpaRepository<ArResource, java.util.UUID> {
}
