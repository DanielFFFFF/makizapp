export class Entity{
  private id : number;

  private _accessCount : number;
  private _author : string;
  private _name : string;
  private _video_url : string;
  private _video_size : number;
  private _picture_url : string;
  private _picture_size : number;
  private _audio_url : string;
  private _audio_size : number;
  private _marker_state : number;
  private _marker_size : number;
  private _creationDate : string;

  constructor(id: number, name: string, video_url: string, video_size: number, picture_url: string, picture_size: number, audio_url: string, audio_size: number, marker_state: number, marker_size: number, creationDate: string, accessCount : number, author : string) {
    this.id = id;
    this._name = name;
    this._video_url = video_url;
    this._video_size = video_size;
    this._picture_url = picture_url;
    this._picture_size = picture_size;
    this._audio_url = audio_url;
    this._audio_size = audio_size;
    this._marker_state = marker_state;
    this._marker_size = marker_size;
    this._creationDate = creationDate;
    this._accessCount = accessCount;
    this._author = author;
  }

  get name(): string {
    return this._name;
  }

  get video_url(): string {
    return this._video_url;
  }

  get video_size(): number {
    return this._video_size;
  }

  get picture_url(): string {
    return this._picture_url;
  }

  get picture_size(): number {
    return this._picture_size;
  }

  get audio_url(): string {
    return this._audio_url;
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

  get creationDate(): string{
    return this._creationDate;
  }

  get accessCount(): number {
    return this._accessCount;
  }

  get author(): string {
    return this._author;
  }
}
