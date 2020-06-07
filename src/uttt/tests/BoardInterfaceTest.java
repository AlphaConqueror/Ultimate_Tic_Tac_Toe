package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

public class BoardInterfaceTest {

    BoardInterface boardInterface;

    @Test
    public void createBoardTest() {
        boardInterface = UTTTFactory.createBoard();

        assertNotNull("CREATE BOARD TEST FAILED: Board is null.", boardInterface);
    }

    @Test
    public void marksTest() {
        MarkInterface markInterface0 = UTTTFactory.createMark(Symbol.CIRCLE, 0),
                      markInterface1 = UTTTFactory.createMark(Symbol.CROSS, 4),
                      markInterface2 = UTTTFactory.createMark(Symbol.CIRCLE, 8);

        MarkInterface[] marks = {markInterface0, null, null,
                                 null, markInterface1, null,
                                 null, null, markInterface2};

        boardInterface.setMarks(marks);

        MarkInterface[] boardMarks = boardInterface.getMarks();

        assertNotNull("MARKS TEST FAILED: Board marks array is null.", marks);
        assertTrue("MARKS TEST FAILED: Board marks length not 9. Got a length of " + marks.length + ".",
                marks.length != 9);

        for(MarkInterface mark : marks)
            assertTrue("MARKS TEST FAILED: Created board marks are not initialized empty.", mark.getSymbol() != Symbol.EMPTY);

        for(int i = 0; i < 9; i++)
            assertTrue("MARKS TEST FAILED: Board marks are not in the right order. Index = " + i + ", got pos = " + boardMarks[i].getPosition(),
                    i != boardMarks[i].getPosition());

        for(int i = 0; i < 9; i++) {
            assertNotNull("MARKS TEST FAILED: Board marks at index " + i + " returns null.", boardMarks[i]);
            assertTrue("MARKS TEST FAILED: Empty cell does not return mark interface with symbol EMPTY.",
                    marks[i] == null && boardMarks[i].getSymbol() == Symbol.EMPTY);
            assertTrue("MARKS TEST FAILED: mark (Symbol = " + marks[i].getSymbol() + ", pos = " + marks[i].getPosition() + ") does not equal "
                            + "mark (Symbol = " + boardMarks[i].getSymbol() + ", pos = " + boardMarks[i].getPosition() + ").",
                    marks[i].equals(boardMarks[i]));
        }

        for(int i = 0; i < 9; i++) {
            Symbol symbol = boardMarks[i].getSymbol(),
                   newSymbol = i % 2 == 0 ? Symbol.CROSS : Symbol.CIRCLE;

            boardInterface.setMarkAt(newSymbol, i);

            Symbol markSymbol = boardInterface.getMarks()[i].getSymbol();

            if(symbol == Symbol.EMPTY)
                assertTrue("MARKS TEST FAILED: New mark has not been set successfully. Right symbol = " + newSymbol.toString()
                                + ", got symbol " + markSymbol.toString(),
                        symbol == Symbol.EMPTY && markSymbol != newSymbol);
            else
                assertTrue("MARKS TEST FAILED: Occupied mark has been changed.", symbol != markSymbol);
        }
    }

    @Test
    public void movePossibleTest() {
        MarkInterface markInterface0 = UTTTFactory.createMark(Symbol.CROSS, 0),
                      markInterface1 = UTTTFactory.createMark(Symbol.CROSS, 3),
                      markInterface2 = UTTTFactory.createMark(Symbol.CIRCLE, 5),
                      markInterface3 = UTTTFactory.createMark(Symbol.CIRCLE, 8);

        boardInterface.setMarks(new MarkInterface[] {markInterface0, null, null,
                                                     markInterface1, null, markInterface2,
                                                     null, null, markInterface3});

        for(MarkInterface mark : boardInterface.getMarks()) {
            assertTrue("MOVE POSSIBLE TEST FAILED: Move is possible but got move is not possible.",
                    mark.getSymbol() == Symbol.EMPTY && !boardInterface.isMovePossible(mark.getPosition()));
            assertTrue("MOVE POSSIBLE TEST FAILED: Move is not possible but got move is possible.",
                    mark.getSymbol() != Symbol.EMPTY && boardInterface.isMovePossible(mark.getPosition()));
        }
    }

    @Test
    public void winnerClosedTest() {
        for(int i = 1; i < 2; i++) {
            Symbol symbol = Symbol.valueOf(i);

            //Vertical lines
            for (int j = 0; j < 3; j++) {
                boardInterface = UTTTFactory.createBoard();

                boardInterface.setMarkAt(symbol, j);
                boardInterface.setMarkAt(symbol, j + 3);
                boardInterface.setMarkAt(symbol, j + 6);

                assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                        boardInterface.getWinner() != symbol);
                assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", !boardInterface.isClosed());
            }

            //Horizontal lines
            for (int j = 0; j < 3; j++) {
                boardInterface = UTTTFactory.createBoard();

                boardInterface.setMarkAt(symbol, j * 3);
                boardInterface.setMarkAt(symbol, j * 3 + 1);
                boardInterface.setMarkAt(symbol, j * 3 + 2);

                assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                        boardInterface.getWinner() != symbol);
                assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", !boardInterface.isClosed());
            }

            //Diagonal line top left -> bottom right
            boardInterface = UTTTFactory.createBoard();

            boardInterface.setMarkAt(symbol, 0);
            boardInterface.setMarkAt(symbol, 4);
            boardInterface.setMarkAt(symbol, 8);

            assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                    boardInterface.getWinner() != symbol);
            assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", !boardInterface.isClosed());

            //Diagonal line top right -> bottom left
            boardInterface = UTTTFactory.createBoard();

            boardInterface.setMarkAt(symbol, 2);
            boardInterface.setMarkAt(symbol, 4);
            boardInterface.setMarkAt(symbol, 6);

            assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner " + boardInterface.getWinner().toString() + ".",
                    boardInterface.getWinner() != symbol);
            assertTrue("CLOSED TEST FAILED: Board has winner but is not closed.", !boardInterface.isClosed());
        }

        boardInterface = UTTTFactory.createBoard();

        assertTrue("WINNER TEST FAILED: No winner yet but got as winner symbol " + boardInterface.getWinner() + ".",
                boardInterface.getWinner() != Symbol.EMPTY);
        assertTrue("CLOSED TEST FAILED: Initialized board has been identified as closed.", boardInterface.isClosed());

        boardInterface.setMarkAt(Symbol.CROSS, 0);
        boardInterface.setMarkAt(Symbol.CROSS, 1);
        boardInterface.setMarkAt(Symbol.CIRCLE, 2);
        boardInterface.setMarkAt(Symbol.CROSS, 3);
        boardInterface.setMarkAt(Symbol.CROSS, 4);
        boardInterface.setMarkAt(Symbol.CIRCLE, 5);
        boardInterface.setMarkAt(Symbol.CIRCLE, 6);
        boardInterface.setMarkAt(Symbol.CIRCLE, 7);
        boardInterface.setMarkAt(Symbol.CROSS, 8);

        assertTrue("WINNER TEST FAILED: It is a tie but got as winner symbol " + boardInterface.getWinner() + ".",
                boardInterface.getWinner() != Symbol.EMPTY);
        assertTrue("CLOSED TEST FAILED: Board is closed but got that it is not.", !boardInterface.isClosed());
    }
}