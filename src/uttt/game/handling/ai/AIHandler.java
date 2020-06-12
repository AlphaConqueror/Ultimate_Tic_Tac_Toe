package uttt.game.handling.ai;

import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

public class AIHandler {

    private SimulatorInterface simulator;

    public AIHandler(SimulatorInterface simulator) {
        this.simulator = simulator;
    }

    public MoveValuation getNextMove(BoardInterface board) {
        for(MarkInterface mark : board.getMarks()) {
            if(mark.getSymbol() == Symbol.EMPTY) {

            }
        }

        return null;
    }
}
