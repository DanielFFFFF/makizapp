package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.ImageAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageAssetRepository extends JpaRepository<ImageAsset, Long> {
}
