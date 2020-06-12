package uttt.game.handling;

import uttt.game.PlayerInterface;
import uttt.game.SimulatorInterface;
import uttt.game.UserInterface;
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


        return null;
    }
}
