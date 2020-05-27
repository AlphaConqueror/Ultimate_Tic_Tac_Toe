package uttt.game;

import uttt.utils.Move;
import uttt.utils.Symbol;

public interface PlayerInterface {

	/**
	 * Gets the symbol of the player.
	 * 
	 * @return The symbol the player has assigned.
	 * 
	 *         Note: Player one uses 'X' and player two uses 'O'.
	 */
	public Symbol getSymbol();

	/**
	 * Allows the player (human or computer) to choose the next move direction.
	 * 
	 * @param game The current UltimateTicTacToe game instance.
	 * @param ui   The user interface allows for communication. Hint: ui might be
	 *             null if it is meant for the AI Player.
	 * 
	 * @return The coordinate chosen by the user to set their mark.
	 * 
	 *         Hint: If it is a human player, one gets the move of the human via
	 *         'ui.getUserMove()'
	 */
	Move getPlayerMove(SimulatorInterface game, UserInterface ui) throws IllegalArgumentException;

}
