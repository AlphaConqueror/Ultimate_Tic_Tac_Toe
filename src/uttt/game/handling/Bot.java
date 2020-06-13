package uttt.game.handling;

import uttt.game.*;
import uttt.game.handling.ai.AIHandler;
import uttt.game.handling.ai.MoveValuation;
import uttt.utils.Move;
import uttt.utils.Symbol;

import java.sql.SQLOutput;

public class Bot implements PlayerInterface {

    private Symbol symbol;

    public Bot(Symbol symbol) {
        this.symbol = symbol;
    }

    public Symbol getSymbol() {
        return symbol;
    }

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

                System.out.println("Bot next possible move: " + nextMove.toString());
                System.out.println("Bot next possible move valuation: " + nextMoveValuation.toString());

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

        System.out.println("Next bot move: " + move.toString());
        System.out.println("Next bot move valuation: " + moveValuation.toString());

        return move;
    }

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
