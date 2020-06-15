package uttt.game;

import uttt.utils.Symbol;

public interface BoardInterface {

	/**
	 * Gets copies of all marks on the board.
	 *
	 * @return Copies of all marks on the board.
	 *
	 *         Note: Empty cells have marks with symbol 'Empty'.
	 */
    MarkInterface[] getMarks();

	/**
	 * Sets all marks on the board.
	 *
	 *
	 * Note: Convenient for testing. Not allowed to use in the AI.
	 */
    void setMarks(MarkInterface[] marks) throws IllegalArgumentException;

	/**
	 * Sets the symbol of a mark at the given position (markIndex) on the board.
	 * Note: If the field is already occupied the mark does not change its symbol.
	 *
	 * @param symbol    The symbol the mark shall use.
	 * @param markIndex The index of the field on the board.
	 *
	 * @return If the mark was correctly added.
	 */
    boolean setMarkAt(Symbol symbol, int markIndex) throws IllegalArgumentException;

	/**
	 * Tells if the board is already won by a player or already a tie.
	 *
	 * @return True if a player has won the board or there is no valid move left on
	 *         the board, false otherwise.
	 */
    boolean isClosed();

	/**
	 * Tells if its possible to add a mark at markIndex.
	 *
	 * @param markIndex The location where to check if a move is possible.
	 *
	 * @return If at position markIndex on this board is a move possible.
	 */
    boolean isMovePossible(int markIndex) throws IllegalArgumentException;

	/**
	 * Gets the winner symbol of the board.
	 *
	 * @return The symbol of the winner.
	 *
	 *         Note: A board that is finished (full or won) gets the winning symbol
	 *         assigned or 'Empty' if it is a tie. An unfinished board also returns
	 *         'Empty'.
	 */
    Symbol getWinner();

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
