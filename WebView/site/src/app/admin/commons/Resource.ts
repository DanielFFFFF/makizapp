/**
 * @class Resource
 * Represents an resource with various media attributes.
 */
export class Resource {

  /**
   * @property id
   * Represents the id of the resource.
   */
  public readonly id: string;

  /**
   * @property accessCount
   * Represents the number of times the resource has been accessed.
   */
  public readonly accessCount: number = 0;

  /**
   * @property name
   * Represents the name of the resource.
   */
  public readonly name: string;

  /**
   * @property creationDate
   * Represents the creation date of the resource.
   */
  public readonly creationDate: string;

  /**
   * @property _thumbnail
   * Represents the id of the media where stocked tracked image of the resource.
   */
  public thumbnail_id: string;

  /**
   * @property video
   * The video media ID of this resource.
   */
  public video_id: string;

  /**
   * @property image
   * The image media id of this resource.
   */
  public image_id: string;

  /**
   * @property audio
   * The audio media id of this resource.
   */
  public audio_id: string;

  /**
   * @property thumbnail
   * Represents the tracked image of the resource in base string.
   */
  public thumbnail: string;

  /**
   * @property videoAsset
   * Represents the video in url form.
   */
  public videoAsset: string;

  /**
   * @property imageAsset
   * Represents the image to display, it is in base64 form.
   */
  public imageAsset: string;

  /**
   * @property audioAsset
   * Represents the audio to listen to, it is in base64 form.
   */
  public audioAsset: string;

  /**
   * @property thumbnail_size
   * Represents the size of the tracked image of the ressource.
   */
  public thumbnail_size: number = 0;

  /**
   * @property image_size
   * Represents the size of the image of the ressource.
   */
  public image_size: number = 0;

  /**
   * @property audio_size
   * Represents the size of the audio of the ressource.
   */
  public audio_size: number = 0;

  /**
   * @property markers_size
   * Represents the size of the markers of the ressource.
   */
  public markers_size: number = 0;

  /**
   * @constructor
   * Creates a new instance of the Resource.
   */

  constructor(id: string, accessCount: number, name: string, creationDate: string, thumbnail_id: string, video_id: string, image_id: string, audio_id: string, thumbnail: string, videoAsset: string, imageAsset: string, audioAsset: string, thumbnail_size: number, image_size: number, audio_size: number, markers_size: number) {
    this.id = id;
    this.accessCount = accessCount;
    this.name = name;
    this.creationDate = creationDate;
    this.thumbnail_id = thumbnail_id;
    this.video_id = video_id;
    this.image_id = image_id;
    this.audio_id = audio_id;
    this.thumbnail = thumbnail;
    this.videoAsset = videoAsset;
    this.imageAsset = imageAsset;
    this.audioAsset = audioAsset;
    this.thumbnail_size = thumbnail_size;
    this.image_size = image_size;
    this.audio_size = audio_size;
    this.markers_size = markers_size;
  }

}
