package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;
import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BoardTest {

    private BoardInterface boardInterface;

    @Before
    public void aCreateBoardTest() {
        boardInterface = UTTTFactory.createBoard();

        assertNotNull("CREATE BOARD TEST FAILED: Board is null.", boardInterface);
    }

    @Test
    public void bMarksTest() {
        MarkInterface[] boardMarks = boardInterface.getMarks();

        assertNotNull("MARKS TEST FAILED: #getMarks returns null.", boardMarks);

        assertTrue("MARKS TEST FAILED: Board marks length not 9. Got a length of " + boardMarks.length + ".", boardMarks.length == 9);

        for(MarkInterface mark : boardMarks)
            assertTrue("MARKS TEST FAILED: Created board marks are not initialized empty.", mark.getSymbol() == Symbol.EMPTY);

        MarkInterface[] marks = {UTTTFactory.createMark(Symbol.CIRCLE, 0), UTTTFactory.createMark(Symbol.EMPTY, 1), UTTTFactory.createMark(Symbol.EMPTY, 2),
                                 UTTTFactory.createMark(Symbol.EMPTY, 3), UTTTFactory.createMark(Symbol.CROSS, 4), UTTTFactory.createMark(Symbol.EMPTY, 5),
                                 UTTTFactory.createMark(Symbol.EMPTY, 6), UTTTFactory.createMark(Symbol.EMPTY, 7), UTTTFactory.createMark(Symbol.CIRCLE, 8)};

        boardInterface.setMarks(marks);

        boardMarks = boardInterface.getMarks();

        assertNotNull("MARKS TEST FAILED: Board mark array is null after setting marks.", boardMarks);

        for(int i = 0; i < 9; i++)
            assertTrue("MARKS TEST FAILED: Board marks are not in the right order. Index = " + i + ", got pos = " + boardMarks[i].getPosition(),
                    i == boardMarks[i].getPosition());

        for(int i = 0; i < 9; i++) {
            assertNotNull("MARKS TEST FAILED: Board marks at index " + i + " returns null.", boardMarks[i]);
            assertTrue("MARKS TEST FAILED: mark (Symbol = " + marks[i].getSymbol() + ", pos = " + marks[i].getPosition() + ") does not equal "
                            + "mark (Symbol = " + boardMarks[i].getSymbol() + ", pos = " + boardMarks[i].getPosition() + ").",
                    marks[i].equals(boardMarks[i]));
        }

        boardInterface = UTTTFactory.createBoard();
        boolean bool = boardInterface.setMarkAt(null, 0);

        assertTrue("MARK TEST FAILED: #setMarkAt has null as 1st argument and does not return false.", !bool);

        boardInterface = UTTTFactory.createBoard();

        for(int i = 0; i < 9; i++) {
            Symbol symbol = boardMarks[i].getSymbol(),
                   newSymbol = i % 2 == 0 ? Symbol.CROSS : Symbol.CIRCLE;

            bool = boardInterface.setMarkAt(newSymbol, i);

            Symbol markSymbol = boardInterface.getMarks()[i].getSymbol();

            if(symbol == Symbol.EMPTY)
                assertTrue("MARKS TEST FAILED: New mark has not been set successfully. Right symbol = " + newSymbol.toString()
                                + ", got symbol " + markSymbol.toString(),
                        markSymbol == newSymbol);
            else
                assertTrue("MARKS TEST FAILED: Occupied mark has been changed.", symbol != markSymbol);

            if(symbol != newSymbol && newSymbol == markSymbol)
                assertTrue("MARKS TEST FAILED: #setMarkAt returned the wrong boolean. Right boolean is true, got false.", bool);
            else
                assertTrue("MARKS TEST FAILED: #setMarkAt returned the wrong boolean. Right boolean is false, got true.", !bool);
        }

        boardInterface = UTTTFactory.createBoard();

        bool = boardInterface.setMarkAt(Symbol.CROSS, -1);
        assertTrue("MARKS TEST FAILED: #setMarkAt index out of bounds. Bounds = [0, 8], got -1.", !bool);

        bool = boardInterface.setMarkAt(Symbol.CROSS, 9);
        assertTrue("MARKS TEST FAILED: #setMarkAt index out of bounds. Bounds = [0, 8], got 9.", !bool);
    }

    @Test
    public void cMovePossibleTest() {
        boardInterface.setMarks(
                new MarkInterface[] {UTTTFactory.createMark(Symbol.CROSS, 0), UTTTFactory.createMark(Symbol.EMPTY, 1), UTTTFactory.createMark(Symbol.EMPTY, 2),
                        UTTTFactory.createMark(Symbol.CROSS, 3), UTTTFactory.createMark(Symbol.EMPTY, 4), UTTTFactory.createMark(Symbol.CIRCLE, 5),
                        UTTTFactory.createMark(Symbol.EMPTY, 6), UTTTFactory.createMark(Symbol.EMPTY, 7), UTTTFactory.createMark(Symbol.CIRCLE, 8)});

        for(MarkInterface mark : boardInterface.getMarks()) {
            if(mark.getSymbol() == Symbol.EMPTY)
                assertTrue("MOVE POSSIBLE TEST FAILED: Move is possible but got move is not possible.",
                        boardInterface.isMovePossible(mark.getPosition()));
            else
                assertTrue("MOVE POSSIBLE TEST FAILED: Move is not possible but got move is possible.",
                        !boardInterface.isMovePossible(mark.getPosition()));
        }

        assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible does not return false when arguments out of bounds.",
                boardInterface.isMovePossible(-1));
        assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible does not return false when arguments out of bounds.",
                boardInterface.isMovePossible(9));
    }

    @Test
    public void dWinnerClosedTest() {
        for(int i = 1; i < 2; i++) {
            Symbol symbol = (i == 1) ? Symbol.CROSS : Symbol.CIRCLE;

            //Vertical lines
            for (int j = 0; j < 3; j++) {
                boardInterface = UTTTFactory.createBoard();

                boardInterface.setMarkAt(symbol, j);
                boardInterface.setMarkAt(symbol, j + 3);
                boardInterface.setMarkAt(symbol, j + 6);

                assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                        boardInterface.getWinner() == symbol);
                assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", boardInterface.isClosed());
            }

            //Horizontal lines
            for (int j = 0; j < 3; j++) {
                boardInterface = UTTTFactory.createBoard();

                boardInterface.setMarkAt(symbol, j * 3);
                boardInterface.setMarkAt(symbol, j * 3 + 1);
                boardInterface.setMarkAt(symbol, j * 3 + 2);

                assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                        boardInterface.getWinner() == symbol);
                assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", boardInterface.isClosed());
            }

            //Diagonal line top left -> bottom right
            boardInterface = UTTTFactory.createBoard();

            boardInterface.setMarkAt(symbol, 0);
            boardInterface.setMarkAt(symbol, 4);
            boardInterface.setMarkAt(symbol, 8);

            assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                    boardInterface.getWinner() == symbol);
            assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", boardInterface.isClosed());

            //Diagonal line top right -> bottom left
            boardInterface = UTTTFactory.createBoard();

            boardInterface.setMarkAt(symbol, 2);
            boardInterface.setMarkAt(symbol, 4);
            boardInterface.setMarkAt(symbol, 6);

            assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                    boardInterface.getWinner() == symbol);
            assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", boardInterface.isClosed());
        }

        boardInterface = UTTTFactory.createBoard();

        assertTrue("WINNER TEST FAILED: No winner yet but got as winner symbol " + boardInterface.getWinner() + ".",
                boardInterface.getWinner() == Symbol.EMPTY);
        assertTrue("CLOSED TEST FAILED: Initialized board has been identified as closed.", !boardInterface.isClosed());

        boardInterface.setMarkAt(Symbol.CROSS, 0);
        boardInterface.setMarkAt(Symbol.CROSS, 1);
        boardInterface.setMarkAt(Symbol.CIRCLE, 2);
        boardInterface.setMarkAt(Symbol.CIRCLE, 3);
        boardInterface.setMarkAt(Symbol.CROSS, 4);
        boardInterface.setMarkAt(Symbol.CROSS, 5);
        boardInterface.setMarkAt(Symbol.CROSS, 6);
        boardInterface.setMarkAt(Symbol.CIRCLE, 7);
        boardInterface.setMarkAt(Symbol.CIRCLE, 8);

        assertTrue("WINNER TEST FAILED: It is a tie but got as winner symbol " + boardInterface.getWinner() + ".",
                boardInterface.getWinner() == Symbol.EMPTY);
        assertTrue("CLOSED TEST FAILED: Board is closed but got that it is not.", boardInterface.isClosed());
    }
}
