package fr.makizart.common.storageservice.dto;

import fr.makizart.common.database.table.ArResource;
import fr.makizart.common.database.table.ImageAsset;

import java.time.LocalDateTime;

public record IncomingResourceDTO(String name,
                                  String Marker1,
                                  String Marker2,
                                  String Marker3,
                                  String thumbnail,
                                  String imageAsset,
                                  String videoAsset,
                                  String soundAsset
) {

}
