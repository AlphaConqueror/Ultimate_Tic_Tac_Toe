package uttt.game.handling;
import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class Board implements BoardInterface {

    private MarkInterface[] marks;

    public Board() {
        marks = new MarkInterface[9];

        for(int i = 0; i < 9; i++)
            marks[i] = UTTTFactory.createMark(Symbol.EMPTY, i);
    }

    public MarkInterface[] getMarks() {
        return marks;
    }

    public void setMarks(MarkInterface[] marks) {
        this.marks = marks;
    }

    public boolean setMarkAt(Symbol symbol, int markIndex) {
        if(marks[markIndex].getSymbol() == Symbol.EMPTY) {
            marks[markIndex].setSymbol(symbol);
            return true;
        }

        return false;
    }

    public boolean isClosed() {
        if(getWinner() != Symbol.EMPTY)
            return true;

        for(int i = 0; i < 9; i++)
            if(marks[i].getSymbol() == Symbol.EMPTY)
                return false;

        return true;
    }

    public boolean isMovePossible(int markIndex) {
        if(markIndex < 0 || markIndex > 8)
            return false;

        if(isClosed())
            return false;

        return marks[markIndex].getSymbol() == Symbol.EMPTY ? true : false;
    }

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
        BoardInterface clone = UTTTFactory.createBoard();
        MarkInterface[] boardMarks = board.getMarks(),
                cloneMarks = new MarkInterface[9];

        for(int i = 0; i < 9; i++)
            cloneMarks[i] = UTTTFactory.createMark(boardMarks[i].getSymbol(), boardMarks[i].getPosition());

        clone.setMarks(cloneMarks);

        return clone;
    }
}
