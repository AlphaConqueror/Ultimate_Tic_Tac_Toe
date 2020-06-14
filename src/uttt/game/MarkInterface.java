package uttt.game;

import uttt.utils.Symbol;

public interface MarkInterface {

	/**
	 * Get the symbol of the mark.
	 * 
	 * @return The symbol of the mark.
	 */
    Symbol getSymbol();

	/**
	 * Get the position of the mark.
	 * 
	 * @return The position of the mark on its board.
	 */
    int getPosition();

	/**
	 * Set the symbol of the mark.
	 * 
	 * @param symbol The symbol to which the mark shall be assigned.
	 */
    void setSymbol(Symbol symbol) throws IllegalArgumentException;

	/**
	 * Overrides: hashCode() in Object
	 * 
	 * @return a hash code value for this object.
	 * 
	 *         Note: Bonus! Not relevant for testing, you do not need to implement
	 *         this nor to test this method!
	 */
    int hashCode();

	/**
	 * Overrides: equals(...) in Object
	 * 
	 * @param obj the reference object with which to compare.
	 * 
	 * @return true if this object is the same as the obj argument; false otherwise.
	 * 
	 *         Note: Bonus! Not relevant for testing, you do not need to implement
	 *         this nor to test this method!
	 */
    boolean equals(Object obj);
}
