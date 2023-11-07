export class RessourceSmall{
  private _name : string;
  private _tracked_image : any;

  constructor(name: string, tracked_image: any) {
    this._name = name;
    this._tracked_image = tracked_image;
  }

  get name(): string {
    return this._name;
  }

  get tracked_image(): any {
    return this._tracked_image;
  }
}
