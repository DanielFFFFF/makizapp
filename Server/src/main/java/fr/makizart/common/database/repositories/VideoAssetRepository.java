package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.VideoAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoAssetRepository extends JpaRepository<VideoAsset, Long> {
}