package uttt.game.handling;

import uttt.game.*;
import uttt.game.handling.ai.AIHandler;
import uttt.game.handling.ai.MoveValuation;
import uttt.utils.Move;
import uttt.utils.Symbol;

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

        for(MarkInterface mark : game.getBoards()[game.getIndexNextBoard()].getMarks()) {
            if(mark.getSymbol() == Symbol.EMPTY) {
                BoardInterface clone = Board.cloneBoard(game.getBoards()[game.getIndexNextBoard()]);

                clone.setMarkAt(symbol, mark.getPosition());

                Move nextMove = new Move(game.getIndexNextBoard(), mark.getPosition());
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
}
