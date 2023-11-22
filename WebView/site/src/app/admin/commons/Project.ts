/**
 * @class Project Represent a project
 *
 * This class represents a project with a creation date, an ID, a name, and a list of resources.
 */
export class Project {
  /**
   * @property {string} createdOn - Project creation date.
   */
  private createdOn: string;

  /**
   * @property {string} id - Project ID.
   */
  private id: string;

  /**
   * @property {string} name - Project name.
   */
  private name: string;

  /**
   * @property {string[]} arResource - List of resource IDs associated with the project.
   */
  private arResource: string[];

  /**
   * Project class constructor.
   *
   * @param createdOn - Project creation date.
   * @param id - Project ID.
   * @param name - Project name.
   * @param arResource - List of resource IDs associated with the project.
   */
  constructor(createdOn: string, id: string, name: string, arResource: string[]) {
    this.createdOn = createdOn;
    this.id = id;
    this.name = name;
    this.arResource = arResource;
  }

  /**
   * Getter for the creation date.
   *
   * @returns The project's creation date.
   */
  public getCreatedOn(): string {
    return this.createdOn;
  }

  /**
   * Getter for the ID.
   *
   * @returns The project's ID.
   */
  public getId(): string {
    return this.id;
  }

  /**
   * Getter for the name.
   *
   * @returns The project's name.
   */
  public getName(): string {
    return this.name;
  }

  /**
   * Setter for the name.
   *
   * @param name - The new name for the project.
   */
  public setName(name: string): void {
    this.name = name;
  }
}
