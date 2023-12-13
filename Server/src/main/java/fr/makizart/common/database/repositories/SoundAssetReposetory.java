package fr.makizart.common.database.repositories;

import fr.makizart.common.database.table.SoundAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoundAssetReposetory extends JpaRepository<SoundAsset, java.util.UUID> {
}
