package uttt.game;

import uttt.utils.Symbol;

public interface Board {

	/**
	 * Gets copies of all marks on the board.
	 * 
	 * @return Copies of all marks on the board.
	 * 
	 *         Note: Empty cells have marks with symbol 'Empty'.
	 */
	public Mark[] getMarks();

	/**
	 * Sets all marks on the board.
	 * 
	 * 
	 * Note: Convenient for testing. Not allowed to use in the AI.
	 */
	public void setMarks(Mark[] marks) throws IllegalArgumentException;

	/**
	 * Sets the symbol of a mark at the given position (markIndex) on the board.
	 * Note: If the field is already occupied the mark does not change its symbol.
	 * 
	 * @param symbol    The symbol the mark shall use.
	 * @param markIndex the index of the field on the board.
	 * 
	 * @return If the mark was correctly added.
	 */
	public boolean setMarkAt(Symbol symbol, int markIndex) throws IllegalArgumentException;

	/**
	 * Tells if the board is already won by a player or already a tie.
	 * 
	 * @return True if a player has won the board or there is no valid move left on
	 *         the board, false otherwise.
	 */
	public boolean isClosed();

	/**
	 * Tells if its possible to add a mark at markIndex.
	 * 
	 * @param markIndex The location where to check if a move is possible.
	 * 
	 * @return If at position markIndex on this board is a move possible.
	 */
	public boolean isMovePossible(int markIndex) throws IllegalArgumentException;

	/**
	 * Gets the winner symbol of the board.
	 * 
	 * @return The symbol of the winner.
	 * 
	 *         Note: A board that is finished (full or won) gets the winning symbol
	 *         assigned or 'Empty' if it is a tie.
	 */
	public Symbol getWinner();
}
