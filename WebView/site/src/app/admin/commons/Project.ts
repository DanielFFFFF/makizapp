/**
 * @class Project
 *
 * Represents a project with an id, name, and creation date.
 */
export class Project{

  /**
   * @property _id
   * Represents the id of the project.
   */
  private readonly _id : number;

  /**
   * @property _name
   * Represents the name of the project.
   */
  private _name : string;

  /**
   * @property _creationDate
   * Represents the creation date of the project.
   */
  private readonly _creationDate : string;

  /**
   * @constructor
   * Creates a new instance of the Project.
   */
  constructor(id: number, name: string, creationDate: string) {
    this._id = id;
    this._name = name;
    this._creationDate = creationDate;
  }

  /**
   * @method name
   * Sets the name of the project.
   */
  set name(value: string) {
    this._name = value;
  }

  /**
   * @method id
   * Gets the id of the project.
   */
  get id(): number {
    return this._id;
  }

  /**
   * @method name
   * Gets the name of the project.
   */
  get name(): string {
    return this._name;
  }
}
