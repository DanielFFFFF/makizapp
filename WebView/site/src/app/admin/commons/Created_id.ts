/**
 * @class Created_id
 * This class encapsulates the response of creating a resource or a project,
 * providing a structured representation for the newly created identifier.
 */
export class Created_id {
  /**
   * The newly created identifier.
   * @type {number}
   */
  created_id: number;

  /**
   * Constructs a new instance of the Created_id class.
   * @param {number} created_id - The identifier generated upon resource or project creation.
   */
  constructor(created_id: number) {
    this.created_id = created_id;
  }
}
