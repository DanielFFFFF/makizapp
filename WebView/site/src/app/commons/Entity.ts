/**
 * @class Entity
 *
 * Represents an entity with various media attributes.
 */
export class Entity{

  /**
   * @property _accessCount
   * Represents the number of times the entity has been accessed.
   */
  private readonly _accessCount : number;

  /**
   * @property _author
   * Represents the author of the entity.
   */
  private readonly _author : string;

  /**
   * @property _name
   * Represents the name of the entity.
   */
  private readonly _name : string;

  /**
   * @property _creationDate
   * Represents the creation date of the entity.
   */
  private readonly _creationDate : string;

  /**
   * @property _tracked_picture
   * Represents the tracked picture of the entity.
   */
  private readonly _tracked_picture : any;

  /**
   * @property _tracked_picture_size
   * Represents the size of the tracked picture of the entity.
   */
  private readonly _tracked_picture_size : number;

  /**
   * @property _video
   * Represents the video of the entity.
   */
  private readonly _video : any;

  /**
   * @property _video_size
   * Represents the size of the video of the entity.
   */
  private readonly _video_size : number;

  /**
   * @property _picture
   * Represents the picture of the entity.
   */
  private readonly _picture : any;

  /**
   * @property _picture_size
   * Represents the size of the picture of the entity.
   */
  private readonly _picture_size : number;

  /**
   * @property _audio
   * Represents the audio of the entity.
   */
  private readonly _audio : any;

  /**
   * @property _audio_size
   * Represents the size of the audio of the entity.
   */
  private readonly _audio_size : number;

  /**
   * @property _marker_state
   * Represents the marker state of the entity.
   */
  private readonly _marker_state : number;

  /**
   * @property _marker_size
   * Represents the size of the marker of the entity.
   */
  private readonly _marker_size : number;

  /**
   * @constructor
   * Creates a new instance of the Entity.
   */
  constructor(accessCount: number, author: string, name: string, tracked_picture: any, tracked_picture_size: number, video: any, video_size: number, picture: any, picture_size: number, audio: any, audio_size: number, marker_state: number, marker_size: number, creationDate: string) {
    this._accessCount = accessCount;
    this._author = author;
    this._name = name;
    this._tracked_picture = tracked_picture;
    this._tracked_picture_size = tracked_picture_size;
    this._video = video;
    this._video_size = video_size;
    this._picture = picture;
    this._picture_size = picture_size;
    this._audio = audio;
    this._audio_size = audio_size;
    this._marker_state = marker_state;
    this._marker_size = marker_size;
    this._creationDate = creationDate;
  }

  /**
   * @method accessCount
   * Gets the access count of the entity.
   */
  get accessCount(): number {
    return this._accessCount;
  }

  /**
   * @method author
   * Gets the author of the entity.
   */
  get author(): string {
    return this._author;
  }

  /**
   * @method name
   * Gets the name of the entity.
   */
  get name(): string {
    return this._name;
  }

  /**
   * @method creationDate
   * Gets the creation date of the entity.
   */
  get creationDate(): string {
    return this._creationDate;
  }

  /**
   * @method tracked_picture
   * Gets the tracked picture of the entity.
   */
  get tracked_picture(): any {
    return this._tracked_picture;
  }

  /**
   * @method tracked_picture_size
   * Gets the size of the tracked picture of the entity.
   */
  get tracked_picture_size(): number {
    return this._tracked_picture_size;
  }

  /**
   * @method video
   * Gets the video of the entity.
   */
  get video(): any {
    return this._video;
  }

  /**
   * @method video_size
   * Gets the size of the video of the entity.
   */
  get video_size(): number {
    return this._video_size;
  }

  /**
   * @method picture
   * Gets the picture of the entity.
   */
  get picture(): any {
    return this._picture;
  }

  /**
   * @method picture_size
   * Gets the size of the picture of the entity.
   */
  get picture_size(): number {
    return this._picture_size;
  }

  /**
   * @method audio
   * Gets the audio of the entity.
   */
  get audio(): any {
    return this._audio;
  }

  /**
   * @method audio_size
   * Gets the size of the audio of the entity.
   */
  get audio_size(): number {
    return this._audio_size;
  }

  /**
   * @method marker_state
   * Gets the marker state of the entity.
   */
  get marker_state(): number {
    return this._marker_state;
  }

  /**
   * @method marker_size
   * Gets the size of the marker of the entity.
   */
  get marker_size(): number {
    return this._marker_size;
  }
}
