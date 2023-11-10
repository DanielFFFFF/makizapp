
/**
 * @class Resource
 *
 * Represents an resource with various media attributes.
 */
export class Resource{

  /**
   * @property id
   * Represents the id of the resource.
   */
  private id : number;

  /**
   * @property accessCount
   * Represents the number of times the resource has been accessed.
   */
  private accessCount : number = 0;

  /**
   * @property name
   * Represents the name of the resource.
   */
  private name : string;

  /**
   * @property creationDate
   * Represents the creation date of the resource.
   */
  private creationDate : string;

  /**
   * @property thumbnail
   * Represents the id of the media where stocked tracked image of the resource.
   */
  private thumbnail_id : any;

  /**
   * @property video
   * @todo
   */
  private video_id : any;

  /**
   * @property image
   * @todo
   */
  private image_id : any;

  /**
   * @property audio
   * @todo
   */
  private audio_id : any;

  /**
   * @property thumbnail
   * Represents the tracked image of the resource in base string.
   */
  private thumbnail : any;

  /**
   * @property videoAsset
   * @todo
   */
  private videoAsset : any;

  /**
   * @property imageAsset
   * @todo
   */
  private imageAsset : any;

  /**
   * @property audioAsset
   * @todo
   */
  private audioAsset : any;

  /**
   * @property thumbnail_size
   * Represents the size of the tracked image of the ressource.
   */
  private thumbnail_size : number = 0;

  /**
   * @property image_size
   * Represents the size of the image of the ressource.
   */
  private image_size : number = 0;

  /**
   * @property audio_size
   * Represents the size of the audio of the ressource.
   */
  private audio_size : number = 0;

  /**
   * @property markers_size
   * Represents the size of the markers of the ressource.
   */
  private markers_size : number = 0;

  /**
   * @constructor
   * Creates a new instance of the Resource.
   */
  constructor(id: number, name: string,creationDate: string) {
    this.id = id;
    this.name = name;
    this.creationDate = creationDate;
  }

  /**
   * @method accessCount
   * Gets the access count of the resource.
   */
   get _accessCount(): number {
    return this.accessCount;
  }

  /**
   * @method name
   * Gets the name of the resource.
   */
   get _name(): string {
    return this.name;
  }

  /**
   * @method creationDate
   * Gets the creation date of the resource.
   */
   get _creationDate(): string {
    return this.creationDate;
  }

  /**
   * @method thumbnail
   * Gets the tracked image of the resource.
   */
   get _thumbnail (): any {
    return this.thumbnail;
  }

  /**
   * @method video
   * Gets the video of the resource.
   */
   get _video(): any {
    return this.videoAsset;
  }

  /**
   * @method image
   * Gets the image of the resource.
   */
   get _image(): any {
    return this.imageAsset;
  }

  /**
   * @method audio
   * Gets the audio of the resource.
   */
   get _audio(): any {
    return this.audioAsset;
  }

   set _accessCount(value: number) {
    this.accessCount = value;
  }

   set _name(value: string) {
    this.name = value;
  }

   set _creationDate(value: string) {
    this.creationDate = value;
  }

   set _thumbnail(value: any) {
    this.thumbnail = value;
  }

   set _video(value: any) {
    this.videoAsset = value;
  }

   set _image(value: any) {
    this.imageAsset = value;
  }

   set _audio(value: any) {
    this.audioAsset = value;
  }

   set _thumbnail_id(value: any) {
    this.thumbnail_id = value;
  }

   set _video_id(value: any) {
    this.video_id = value;
  }

   set _image_id(value: any) {
    this.image_id = value;
  }

   set _audio_id(value: any) {
    this.audio_id = value;
  }

   get _id(): number {
    return this.id;
  }

   get _thumbnail_size(): number {
    return this.thumbnail_size;
  }

   set _thumbnail_size(value: number) {
    this.thumbnail_size = value;
  }

   get _image_size(): number {
    return this.image_size;
  }

   set _image_size(value: number) {
    this.image_size = value;
  }

   get _audio_size(): number {
    return this.audio_size;
  }

   set _audio_size(value: number) {
    this.audio_size = value;
  }

   get _markers_size(): number {
    return this.markers_size;
  }

   set _markers_size(value: number) {
    this.markers_size = value;
  }
}
