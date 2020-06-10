package uttt.game.handling;

import uttt.game.PlayerInterface;
import uttt.game.SimulatorInterface;
import uttt.game.UserInterface;
import uttt.utils.Move;
import uttt.utils.Symbol;

public class Player implements PlayerInterface {

    private Symbol symbol;

    public Player(Symbol symbol) {
        this.symbol = symbol;
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public Move getPlayerMove(SimulatorInterface game, UserInterface ui) throws IllegalArgumentException {
        Move move = ui.getUserMove();

        if(game.isMovePossible(move.getBoardIndex(), move.getMarkIndex()))
            return move;

        return getPlayerMove(game, ui);
    }
}
