
/**
 * @class Resource
 *
 * Represents an resource with various media attributes.
 */
export class Resource{

  /**
   * @property _id
   * Represents the id of the resource.
   */
  private _id : number;

  /**
   * @property _accessCount
   * Represents the number of times the resource has been accessed.
   */
  private _accessCount : number = 0;

  /**
   * @property _name
   * Represents the name of the resource.
   */
  private _name : string;


  /**
   * @property _creationDate
   * Represents the creation date of the resource.
   */
  private _creationDate : string;

  /**
   * @property _thumbnail
   * Represents the tracked image of the resource.
   */
  private _thumbnail : any;
  /**
   * @property _video
   * Represents the video of the resource.
   */
  private _video : any;

  /**
   * @property _image
   * Represents the image of the resource.
   */
  private _image : any;

  /**
   * @property _audio
   * Represents the audio of the resource.
   */
  private _audio : any;

  /**
   * @property _thumbnail_size
   * Represents the size of the tracked image of the ressource.
   */
  private _thumbnail_size : number = 0;

  /**
   * @property _image_size
   * Represents the size of the image of the ressource.
   */
  private _image_size : number = 0;

  /**
   * @property _audio_size
   * Represents the size of the audio of the ressource.
   */
  private _audio_size : number = 0;

  /**
   * @property _markers_size
   * Represents the size of the markers of the ressource.
   */
  private _markers_size : number = 0;

  /**
   * @constructor
   * Creates a new instance of the Resource.
   */
  constructor(id: number, name: string, thumbnail: any,creationDate: string) {
    this._id = id;
    this._name = name;
    this._creationDate = creationDate;
    this._thumbnail = thumbnail;
  }

  /**
   * @method accessCount
   * Gets the access count of the resource.
   */
  get accessCount(): number {
    return this._accessCount;
  }

  /**
   * @method name
   * Gets the name of the resource.
   */
  get name(): string {
    return this._name;
  }

  /**
   * @method creationDate
   * Gets the creation date of the resource.
   */
  get creationDate(): string {
    return this._creationDate;
  }

  /**
   * @method thumbnail
   * Gets the tracked image of the resource.
   */
  get thumbnail (): any {
    return this._thumbnail;
  }

  /**
   * @method video
   * Gets the video of the resource.
   */
  get video(): any {
    return this._video;
  }

  /**
   * @method image
   * Gets the image of the resource.
   */
  get image(): any {
    return this._image;
  }

  /**
   * @method audio
   * Gets the audio of the resource.
   */
  get audio(): any {
    return this._audio;
  }

  set accessCount(value: number) {
    this._accessCount = value;
  }

  set name(value: string) {
    this._name = value;
  }

  set creationDate(value: string) {
    this._creationDate = value;
  }

  set thumbnail(value: any) {
    this._thumbnail = value;
  }

  set video(value: any) {
    this._video = value;
  }

  set image(value: any) {
    this._image = value;
  }

  set audio(value: any) {
    this._audio = value;
  }

  get id(): number {
    return this._id;
  }

  get thumbnail_size(): number {
    return this._thumbnail_size;
  }

  set thumbnail_size(value: number) {
    this._thumbnail_size = value;
  }

  get image_size(): number {
    return this._image_size;
  }

  set image_size(value: number) {
    this._image_size = value;
  }

  get audio_size(): number {
    return this._audio_size;
  }

  set audio_size(value: number) {
    this._audio_size = value;
  }

  get markers_size(): number {
    return this._markers_size;
  }

  set markers_size(value: number) {
    this._markers_size = value;
  }
}
