package uttt.game;

import uttt.UTTTFactory;
import uttt.utils.Symbol;

public class Main {

	/**
	 * If set to true a human will be playing the game, otherwise a computer player
	 * as player one. However, command line arguments will override this choice.
	 */
	private static final boolean DEFAULT_USE_HUMAN_PLAYER_ONE = true;

	/**
	 * If set to true a human will be playing the game, otherwise a computer player
	 * as player two. However, command line arguments will override this choice.
	 */
	private static final boolean DEFAULT_USE_HUMAN_PLAYER_TWO = true;

	/**
	 * If set to true the user will be asked what player should be chosen.
	 *
	 * Note: This is a debug option only that allows you to easily show both human
	 * and computer players without any changes in the code.
	 */
	private static final boolean DEBUG_ASK_USER = false;

	/**
	 * @param str The string that should be converted to an boolean.
	 * @return True iff the lowercase version of str equals to "true" and false
	 *         otherwise.
	 */
	private static boolean string2boolean(String str) {
		return Boolean.parseBoolean(str);
	}

	/**
	 * The main function is the entry point of our Ultimate TicTacToe
	 * simulator/game. Once the game is started the arguments (if any) are read and
	 * a Simulator as well as the UserInterface and the two Player objects are
	 * created. The control is then transfered to the Simulator that should run
	 * until the game is over.
	 *
	 * @param args The command line arguments provided when the program was
	 *             executed. The command line arguments are expected to be in the
	 *             following order and only accepted this way. However, it is
	 *             possible to omit trailing arguments, hence to provide only the
	 *             first N instead of all.
	 *
	 *             No. Meaning Possible Values
	 *
	 *             (1) use-human-player-one ("true" for true otherwise false)
	 * 
	 *             (2) use-human-player-two ("true" for true otherwise false)
	 *
	 *             * See string2boolean for more information.
	 */
	public static void main(String[] args) {

		// Initialize the user interface choice.
		boolean useHumanPlayerOne = DEFAULT_USE_HUMAN_PLAYER_ONE;
		boolean useHumanPlayerTwo = DEFAULT_USE_HUMAN_PLAYER_TWO;

		// The fourth argument (if given) is the player choice.
		if (args.length > 1)
			useHumanPlayerOne = string2boolean(args[1]);
		if (args.length > 2)
			useHumanPlayerOne = string2boolean(args[2]);

		// Create an instance of the Ultimate TicTacToe simulator.
		SimulatorInterface game = UTTTFactory.createSimulator();

		// Create an instance of the user interface that is used to communicate
		// with the user.
		UserInterface ui = UTTTFactory.createUserInterface();

		// If requested, the user is asked what player should be used.
		if (DEBUG_ASK_USER) {
			String[] PossibleAnswers = { "hh", "hc", "cc" };
			String Answer = ui.getUserInput("Use two (hh)uman or a (h)uman and(c)omputer or two (cc)omputer player?",
					PossibleAnswers);
			useHumanPlayerOne = Answer.charAt(0) == 'h';
			useHumanPlayerTwo = Answer.charAt(1) != 'c';
		}

		PlayerInterface playerOne;
		PlayerInterface playerTwo;

		// Create an instance of player one.
		if (useHumanPlayerOne) {
			playerOne = UTTTFactory.createHumanPlayer(Symbol.CROSS);
		} else {
			playerOne = UTTTFactory.createBonusPlayer(Symbol.CROSS);
		}

		// Create an instance of player two.
		if (useHumanPlayerTwo) {
			playerTwo = UTTTFactory.createHumanPlayer(Symbol.CIRCLE);
		} else {
			playerTwo = UTTTFactory.createBonusPlayer(Symbol.CIRCLE);
		}

		// Simulate the game until it is over.
		game.run(playerOne, playerTwo, ui);
	}
}
