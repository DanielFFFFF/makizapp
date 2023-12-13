package fr.makizart.common.storageservice.dto;

/**
 * Represent what the client is sending to the server in order to create an ArResource
 * Everything need to be base64 encoded which slow the request but is fine since this request is not called often
 * Maybe in the future there is a way to do this using a multipart request?
 * @param name the name of the created resource
 * @param marker1 arJs FSET file base64 encoded
 * @param marker2 arJs FSET3 file base64 encoded
 * @param marker3 arJs ISET file base64 encoded
 * @param thumbnail an image for the resource thumbnail, base64 encoded
 * @param imageAsset base64 image to display
 * @param videoAsset video URL to display
 * @param soundAsset base64 sound to play when the tracked image is detected
 */
public record IncomingResourceDTO(String name,
                                  String marker1,
                                  String marker2,
                                  String marker3,
                                  String thumbnail,
                                  String imageAsset,
                                  String videoAsset,
                                  String soundAsset
) {

}
