package uttt.game.handling;

import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class Mark implements MarkInterface {

    private Symbol symbol;
    private int position;

    public Mark(Symbol symbol, int position) {
        this.symbol = symbol;
        this.position = position;
    }

    @Override
    public Symbol getSymbol() {
        return symbol;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setSymbol(Symbol symbol) throws IllegalArgumentException {
        this.symbol = symbol;
    }
}
