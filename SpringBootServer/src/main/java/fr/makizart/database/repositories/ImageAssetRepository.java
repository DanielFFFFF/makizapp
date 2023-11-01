package fr.makizart.database.repositories;

import fr.makizart.database.table.ImageAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageAssetRepository extends JpaRepository<ImageAsset, Long> {
}
