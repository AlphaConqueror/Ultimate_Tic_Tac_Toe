package uttt;

import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.PlayerInterface;
import uttt.game.SimulatorInterface;
import uttt.game.UserInterface;
import uttt.ui.GUI;
import uttt.utils.Symbol;

public class UTTTFactory {

	/**
	 * Create a Ultimate TicTacToe mark on board index j.
	 *
	 * @param symbol The symbol for the new mark.
	 * @param j      The index of the piece in its board.
	 *
	 * @return A Ultimate TicTacToe mark.
	 *
	 *         Note: This method is for testing reasons only.
	 */
	public static MarkInterface createMark(Symbol symbol, int j) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Create a Ultimate TicTacToe board.
	 *
	 * @return A Ultimate TicTacToe board.
	 *
	 *         Note: This method is for testing reasons only.
	 *
	 */
	public static BoardInterface createBoard() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Create a Ultimate TicTacToe simulator.
	 *
	 * @return A Ultimate TicTacToe simulator.
	 */
	public static SimulatorInterface createSimulator() {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Create a user interface for a Ultimate TicTacToe simulator.
	 *
	 * @return A graphical user interface for a Ultimate TicTacToe simulator.
	 */
	public static UserInterface createUserInterface() {
		return new GUI();
	}

	/**
	 * Create a Human Ultimate TicTacToe player.
	 *
	 * @param symbol The symbol the player uses during the game.
	 *
	 * @return A Ultimate TicTacToe player that uses the user interface to
	 *         communicate with the human player to select moves.
	 */
	public static PlayerInterface createHumanPlayer(Symbol symbol) {
		throw new UnsupportedOperationException("Not yet implemented");
	}

	/**
	 * Create a Computer Ultimate TicTacToe player.
	 *
	 * @param symbol The symbol the player uses during the game.
	 *
	 * @return A Ultimate TicTacToe player that will play automatically.
	 *
	 *         Note: This player is evaluated on the bonus tests. It might be the
	 *         same as for the tournament.
	 */
	public static PlayerInterface createBonusPlayer(Symbol symbol) {
		throw new UnsupportedOperationException("Not yet implemented");
	}
}
