package uttt.game.handling;

import uttt.game.*;
import uttt.game.handling.ai.AIHandler;
import uttt.game.handling.ai.MoveValuation;
import uttt.utils.Move;
import uttt.utils.Symbol;

public class Bot implements PlayerInterface {

    private final Symbol symbol;

    public Bot(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    /**
     * Gets the next best move to win the game.
     *
     * @param game The current UltimateTicTacToe game instance.
     * @param ui   The user interface allows for communication.
     *
     * @return The next best move to win the game.
     */
    public Move getPlayerMove(SimulatorInterface game, UserInterface ui) {
        Move move = null;
        MoveValuation moveValuation = null;
        int nextBoardIndex = getNextBoardIndex(game);

        for(MarkInterface mark : game.getBoards()[nextBoardIndex].getMarks()) {
            if(mark.getSymbol() == Symbol.EMPTY) {
                BoardInterface clone = Board.cloneBoard(game.getBoards()[nextBoardIndex]);

                clone.setMarkAt(symbol, mark.getPosition());

                Move nextMove = new Move(nextBoardIndex, mark.getPosition());
                MoveValuation nextMoveValuation = AIHandler.getNextMove(clone, 0, symbol, symbol.flip());

                if(move == null) {
                    move = nextMove;
                    moveValuation = nextMoveValuation;
                } else if(nextMoveValuation.getScore() > moveValuation.getScore()) {
                    move = nextMove;
                    moveValuation = nextMoveValuation;
                } else if(nextMoveValuation.getScore() == moveValuation.getScore() && nextMoveValuation.getDepth() < moveValuation.getDepth()) {
                    move = nextMove;
                    moveValuation = nextMoveValuation;
                }
            }
        }

        return move;
    }

    /**
     * Used to get the next free board index.
     *
     * @param game The current UltimateTicTacToe game instance.
     *
     * @return The next free board index.
     */
    private int getNextBoardIndex(SimulatorInterface game) {
        int nextIndex = game.getIndexNextBoard();
        BoardInterface[] boards = game.getBoards();

        if(nextIndex != -1 && !boards[nextIndex].isClosed())
            return nextIndex;

        for(int i = 0; i < 9; i++)
            if(!boards[i].isClosed())
                return i;

        return -1;
    }
}
