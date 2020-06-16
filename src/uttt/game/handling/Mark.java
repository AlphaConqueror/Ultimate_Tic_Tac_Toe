package uttt.game.handling;

import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class Mark implements MarkInterface {

    private Symbol symbol;
    private final int position;

    public Mark(Symbol symbol, int position) throws IllegalArgumentException {
        if(position < 0 || position > 8)
            throw new IllegalArgumentException();

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
    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }
}
