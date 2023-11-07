/**
 * @class Ressource
 *
 * Represents an ressource with various media attributes.
 */
export class Ressource{

  /**
   * @property _accessCount
   * Represents the number of times the ressource has been accessed.
   */
  private readonly _accessCount : number;

  /**
   * @property _author
   * Represents the author of the ressource.
   */
  private readonly _author : string;

  /**
   * @property _name
   * Represents the name of the ressource.
   */
  private readonly _name : string;

  /**
   * @property _creationDate
   * Represents the creation date of the ressource.
   */
  private readonly _creationDate : string;

  /**
   * @property _tracked_image
   * Represents the tracked image of the ressource.
   */
  private readonly _tracked_image : any;

  /**
   * @property _tracked_image_size
   * Represents the size of the tracked image of the ressource.
   */
  private readonly _tracked_image_size : number;

  /**
   * @property _video
   * Represents the video of the ressource.
   */
  private readonly _video : any;

  /**
   * @property _video_size
   * Represents the size of the video of the ressource.
   */
  private readonly _video_size : number;

  /**
   * @property _image
   * Represents the image of the ressource.
   */
  private readonly _image : any;

  /**
   * @property _image_size
   * Represents the size of the image of the ressource.
   */
  private readonly _image_size : number;

  /**
   * @property _audio
   * Represents the audio of the ressource.
   */
  private readonly _audio : any;

  /**
   * @property _audio_size
   * Represents the size of the audio of the ressource.
   */
  private readonly _audio_size : number;

  /**
   * @property _marker_state
   * Represents the marker state of the ressource.
   */
  private readonly _marker_state : number;

  /**
   * @property _marker_size
   * Represents the size of the marker of the ressource.
   */
  private readonly _marker_size : number;

  /**
   * @constructor
   * Creates a new instance of the Ressource.
   */
  constructor(accessCount: number, author: string, name: string, tracked_image: any, tracked_image_size: number, video: any, video_size: number, image: any, image_size: number, audio: any, audio_size: number, marker_state: number, marker_size: number, creationDate: string) {
    this._accessCount = accessCount;
    this._author = author;
    this._name = name;
    this._tracked_image = tracked_image;
    this._tracked_image_size = tracked_image_size;
    this._video = video;
    this._video_size = video_size;
    this._image = image;
    this._image_size = image_size;
    this._audio = audio;
    this._audio_size = audio_size;
    this._marker_state = marker_state;
    this._marker_size = marker_size;
    this._creationDate = creationDate;
  }

  /**
   * @method accessCount
   * Gets the access count of the ressource.
   */
  get accessCount(): number {
    return this._accessCount;
  }

  /**
   * @method author
   * Gets the author of the ressource.
   */
  get author(): string {
    return this._author;
  }

  /**
   * @method name
   * Gets the name of the ressource.
   */
  get name(): string {
    return this._name;
  }

  /**
   * @method creationDate
   * Gets the creation date of the ressource.
   */
  get creationDate(): string {
    return this._creationDate;
  }

  /**
   * @method tracked_image
   * Gets the tracked image of the ressource.
   */
  get tracked_image(): any {
    return this._tracked_image;
  }

  /**
   * @method tracked_image_size
   * Gets the size of the tracked image of the ressource.
   */
  get tracked_image_size(): number {
    return this._tracked_image_size;
  }

  /**
   * @method video
   * Gets the video of the ressource.
   */
  get video(): any {
    return this._video;
  }

  /**
   * @method video_size
   * Gets the size of the video of the ressource.
   */
  get video_size(): number {
    return this._video_size;
  }

  /**
   * @method image
   * Gets the image of the ressource.
   */
  get image(): any {
    return this._image;
  }

  /**
   * @method image_size
   * Gets the size of the image of the ressource.
   */
  get image_size(): number {
    return this._image_size;
  }

  /**
   * @method audio
   * Gets the audio of the ressource.
   */
  get audio(): any {
    return this._audio;
  }

  /**
   * @method audio_size
   * Gets the size of the audio of the ressource.
   */
  get audio_size(): number {
    return this._audio_size;
  }

  /**
   * @method marker_state
   * Gets the marker state of the ressource.
   */
  get marker_state(): number {
    return this._marker_state;
  }

  /**
   * @method marker_size
   * Gets the size of the marker of the ressource.
   */
  get marker_size(): number {
    return this._marker_size;
  }
}
