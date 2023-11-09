package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.SoundAsset;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundAssetReposetory extends JpaRepository<SoundAsset,Long> {
}
