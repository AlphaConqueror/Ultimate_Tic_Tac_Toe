package uttt.game.handling;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class Board implements BoardInterface {

    private MarkInterface[] marks;

    public Board() {
        marks = new MarkInterface[9];

        for(int i = 0; i < 9; i++)
            marks[i] = new Mark(Symbol.EMPTY, i);
    }

    @Override
    public MarkInterface[] getMarks() {
        return marks;
    }

    @Override
    public void setMarks(MarkInterface[] marks) {
        this.marks = marks;
    }

    @Override
    public boolean setMarkAt(Symbol symbol, int markIndex) throws IllegalArgumentException {
        if(symbol == null)
            throw new IllegalArgumentException("Symbol is null.");

        if(markIndex < 0 || markIndex > 8)
            throw new IllegalArgumentException();

        if(marks == null || marks[markIndex] == null)
            throw new IllegalArgumentException("Mark is null.");

        if(marks[markIndex].getSymbol() == Symbol.EMPTY) {
            marks[markIndex].setSymbol(symbol);
            return true;
        }

        return false;
    }

    @Override
    public boolean isClosed() {
        if(getWinner() != Symbol.EMPTY)
            return true;

        for(int i = 0; i < 9; i++)
            if(marks[i].getSymbol() == Symbol.EMPTY)
                return false;

        return true;
    }

    @Override
    public boolean isMovePossible(int markIndex) throws IllegalArgumentException {
        if(markIndex < 0 || markIndex > 8)
            throw new IllegalArgumentException();

        if(isClosed())
            return false;

        return marks[markIndex].getSymbol() == Symbol.EMPTY;
    }

    @Override
    public Symbol getWinner() {
        for(int i = 0; i < 2; i++) {
            Symbol symbol = (i == 0) ? Symbol.CROSS : Symbol.CIRCLE;

            for(int j = 0; j < 3; j++) {
                //Vertical lines
                if(marks[j].getSymbol() == symbol && marks[j + 3].getSymbol() == symbol && marks[j + 6].getSymbol() == symbol)
                    return symbol;
                //Horizontal lines
                if(marks[j * 3].getSymbol() == symbol && marks[j * 3 + 1].getSymbol() == symbol && marks[j * 3 + 2].getSymbol() == symbol)
                    return symbol;
            }

            //Diagonal line: Top left -> Bottom right
            if(marks[0].getSymbol() == symbol && marks[4].getSymbol() == symbol && marks[8].getSymbol() == symbol)
                return symbol;
            //Diagonal line: Top right -> Bottom left
            if(marks[2].getSymbol() == symbol && marks[4].getSymbol() == symbol && marks[6].getSymbol() == symbol)
                return symbol;
        }

        return Symbol.EMPTY;
    }

    public static BoardInterface cloneBoard(BoardInterface board) {
        BoardInterface clone = new Board();
        MarkInterface[] boardMarks = board.getMarks(),
                cloneMarks = new MarkInterface[9];

        for(int i = 0; i < 9; i++)
            cloneMarks[i] = new Mark(boardMarks[i].getSymbol(), boardMarks[i].getPosition());

        clone.setMarks(cloneMarks);

        return clone;
    }
}
