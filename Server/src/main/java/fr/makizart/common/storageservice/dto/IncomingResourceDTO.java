package fr.makizart.common.storageservice.dto;

import fr.makizart.common.database.table.ArResource;

import java.time.LocalDateTime;

public record IncomingResourceDTO(Long id,
                                  LocalDateTime createdOn,
                                  Long pathToMarker1Id,
                                  Long pathToMarker2Id,
                                  Long pathToMarker3Id,
                                  Long thumbnailId,
                                  Long imageAssetId,
                                  Long videoAssetId,
                                  Long soundAssetId
) {

// Copy constructor
public IncomingResourceDTO(ArResource arResource) {
        this(
        arResource.getId(),
        arResource.getCreatedOn(),
        arResource.getMarker1().getId(),
        arResource.getMarker2().getId(),
        arResource.getMarker3().getId(),
        arResource.getThumbnail().getId(),
        arResource.getImageAsset() != null ? arResource.getImageAsset().getId() : null,
        arResource.getVideoAsset() != null ? arResource.getVideoAsset().getId() : null,
        arResource.getSoundAsset() != null ? arResource.getSoundAsset().getId() : null
        );
}
}
