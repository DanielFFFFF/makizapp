export class Project{
  private _id : number;
  private _name : string;
  private _creationDate : string;

  constructor(id: number, name: string, creationDate: string) {
    this._id = id;
    this._name = name;
    this._creationDate = creationDate;
  }

  set name(value: string) {
    this._name = value;
  }

  get id(): number {
    return this._id;
  }

  get name(): string {
    return this._name;
  }

  get creationDate(): string {
    return this._creationDate;
  }
}
