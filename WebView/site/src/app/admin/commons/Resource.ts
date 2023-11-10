/**
 * @class Resource
 * Represents an resource with various media attributes.
 */
export class Resource {

  /**
   * @property id
   * Represents the id of the resource.
   */
  private readonly id: string;

  /**
   * @property accessCount
   * Represents the number of times the resource has been accessed.
   */
  private readonly accessCount: number = 0;

  /**
   * @property name
   * Represents the name of the resource.
   */
  private readonly name: string;

  /**
   * @property creationDate
   * Represents the creation date of the resource.
   */
  private readonly creationDate: string;

  /**
   * @property _thumbnail
   * Represents the id of the media where stocked tracked image of the resource.
   */
  private thumbnail_id: string;

  /**
   * @property video
   * The video media ID of this resource.
   */
  private video_id: string;

  /**
   * @property image
   * The image media id of this resource.
   */
  private image_id: string;

  /**
   * @property audio
   * The audio media id of this resource.
   */
  private audio_id: string;

  /**
   * @property thumbnail
   * Represents the tracked image of the resource in base string.
   */
  private thumbnail: string;

  /**
   * @property videoAsset
   * Represents the video in url form.
   */
  private videoAsset: string;

  /**
   * @property imageAsset
   * Represents the image to display, it is in base64 form.
   */
  private imageAsset: string;

  /**
   * @property audioAsset
   * Represents the audio to listen to, it is in base64 form.
   */
  private audioAsset: string;

  /**
   * @property thumbnail_size
   * Represents the size of the tracked image of the ressource.
   */
  private thumbnail_size: number = 0;

  /**
   * @property image_size
   * Represents the size of the image of the ressource.
   */
  private image_size: number = 0;

  /**
   * @property audio_size
   * Represents the size of the audio of the ressource.
   */
  private audio_size: number = 0;

  /**
   * @property markers_size
   * Represents the size of the markers of the ressource.
   */
  private markers_size: number = 0;

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

  /**
   * @method _accessCount()
   * Gets the access count of the resource.
   * @returns {number}
   */
  get _accessCount(): number {
    return this.accessCount;
  }

  /**
   * @method _name()
   * Gets the name of the resource.
   * @returns {string}
   */
  get _name(): string {
    return this.name;
  }

    /**
     * @method _thumbnail_id()
     * Gets the id of thumbnail of the resource.
     * @returns {string}
     */
    get _thumbnail_id(): string {
        return this._thumbnail_id;
    }

  /**
   * @method _creationDate()
   * Gets the creation date of the resource.
   * @returns {string}
   */
  get _creationDate(): string {
    return this.creationDate;
  }

  /**
   * @method _thumbnail()
   * Gets the tracked image of the resource.
   * @returns {string}
   */
  get _thumbnail(): string {
    return this._thumbnail;
  }

  /**
   * @method _video()
   * Gets the video of the resource.
   * @returns {string}
   */
  get _video(): string {
    return this._videoAsset;
  }

  /**
   * @method _image()
   * Gets the image of the resource.
   * @returns {string}
   */
  get _image(): string {
    return this._imageAsset;
  }

  /**
   * @method _audio()
   * Gets the audio of the resource.
   * @returns {string}
   */
  get _audio(): string {
    return this._audioAsset;
  }

  /**
   * @method _id()
   * Gets the id of the resource.
   * @returns {string}
   */
  get _id(): string {
    return this.id;
  }

  /**
   * Gets the size of the thumbnail associated with the resource.
   * @returns {number}
   */
  get _thumbnail_size(): number {
    return this.thumbnail_size;
  }

  /**
   * Sets the size of the thumbnail associated with the resource.
   * @param {number} value - The new size for the thumbnail.
   */
  set _thumbnail_size(value: number) {
    this.thumbnail_size = value;
  }

  /**
   * Gets the size of the image associated with the resource.
   * @returns {number}
   */
  get _image_size(): number {
    return this.image_size;
  }

  /**
   * Sets the size of the image associated with the resource.
   * @param {number} value - The new size for the image.
   */
  set _image_size(value: number) {
    this.image_size = value;
  }

  /**
   * Gets the size of the audio file associated with the resource.
   * @returns {number}
   */
  get _audio_size(): number {
    return this.audio_size;
  }

  /**
   * Sets the size of the audio file associated with the resource.
   * @param {number} value - The new size for the audio file.
   */
  set _audio_size(value: number) {
    this.audio_size = value;
  }

  /**
   * Gets the size of the markers associated with the resource.
   * @returns {number}
   */
  get _markers_size(): number {
    return this.markers_size;
  }

  /**
   * Sets the size of the markers associated with the resource.
   * @param {number} value - The new size for the markers.
   */
  set _markers_size(value: number) {
    this.markers_size = value;
  }


  /**
   * Sets the thumbnail associated with the resource.
   * @param {string} value - The new thumbnail value.
   */
  set _thumbnail(value: string) {
    this.thumbnail = value;
  }

  /**
   * Sets the video asset associated with the resource.
   * @param {string} value - The new video asset value.
   */
  set _videoAsset(value: string) {
    this.videoAsset = value;
  }

  /**
   * Sets the image asset associated with the resource.
   * @param {string} value - The new image asset value.
   */
  set _imageAsset(value: string) {
    this.imageAsset = value;
  }

  /**
   * Sets the audio asset associated with the resource.
   * @param {string} value - The new audio asset value.
   */
  set _audioAsset(value: string) {
    this.audioAsset = value;
  }
}
