package fr.makizart.database.repositories;

import fr.makizart.database.table.VideoAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoAssetRepository extends JpaRepository<VideoAsset, Long> {
}