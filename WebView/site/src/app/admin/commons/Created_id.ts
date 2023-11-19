/**
 * @class Created_id
 * This class encapsulates the response of creating a resource or a project,
 * providing a structured representation for the newly created identifier.
 */
export class Created_id {
  /**
   * The newly created identifier.
   * @type {string}
   */
  created_id: string;

  /**
   * Constructs a new instance of the Created_id class.
   * @param {string} created_id - The identifier generated upon resource or project creation.
   */
  constructor(created_id: string) {
    this.created_id = created_id;
  }
}
