export class Entity{

  private _accessCount : number;
  private _author : string;
  private _name : string;
  private _creationDate : string;
  private _tracked_picture : any;
  private _tracked_picture_size : number;

  //Picture or video
  private _video : any;
  private _video_size : number;
  private _picture : any;
  private _picture_size : number;

  private _audio : any;
  private _audio_size : number;
  private _marker_state : number;
  private _marker_size : number;


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


  get accessCount(): number {
    return this._accessCount;
  }

  get author(): string {
    return this._author;
  }

  get name(): string {
    return this._name;
  }

  get creationDate(): string {
    return this._creationDate;
  }

  get tracked_picture(): any {
    return this._tracked_picture;
  }

  get tracked_picture_size(): number {
    return this._tracked_picture_size;
  }

  get video(): any {
    return this._video;
  }

  get video_size(): number {
    return this._video_size;
  }

  get picture(): any {
    return this._picture;
  }

  get picture_size(): number {
    return this._picture_size;
  }

  get audio(): any {
    return this._audio;
  }

  get audio_size(): number {
    return this._audio_size;
  }

  get marker_state(): number {
    return this._marker_state;
  }

  get marker_size(): number {
    return this._marker_size;
  }
}
