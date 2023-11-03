export class EntitySmall{
  private _name : string;
  private _tracked_picture : any;

  constructor(name: string, tracked_picture: any) {
    this._name = name;
    this._tracked_picture = tracked_picture;
  }

  get name(): string {
    return this._name;
  }

  get tracked_picture(): any {
    return this._tracked_picture;
  }
}
