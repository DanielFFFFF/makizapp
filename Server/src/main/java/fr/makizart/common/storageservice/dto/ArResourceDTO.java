package fr.makizart.common.storageservice.dto;

import fr.makizart.common.database.table.ArResource;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

public record ArResourceDTO(
        String name,
        UUID id,
        LocalDateTime createdOn,
        UUID markersId,
        UUID thumbnailId,
        UUID imageAssetId,
        UUID videoAssetId,
        UUID soundAssetId
) {

    // Copy constructor
    public ArResourceDTO(ArResource arResource) {
        this(
                arResource.getName(),
                arResource.getId(),
                arResource.getCreatedOn(),
                arResource.getMarkers().getId(),
                arResource.getThumbnail().getId(),
                arResource.getImageAsset() != null ? arResource.getImageAsset().getId() : null,
                arResource.getVideoAsset() != null ? arResource.getVideoAsset().getId() : null,
                arResource.getSoundAsset() != null ? arResource.getSoundAsset().getId() : null
        );
    }


}