package uttt.tests;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimulatorTest {

    private SimulatorInterface simulatorInterface;

    @Before
    public void aCreateSimulatorTest() {
        simulatorInterface = UTTTFactory.createSimulator();

        assertNotNull("CREATE SIMULATOR TEST FAILED: Simulator is null.", simulatorInterface);
    }

    @Test
    public void bBoardsTest() {
        BoardInterface[] boards = simulatorInterface.getBoards();

        assertNotNull("BOARDS TEST FAILED: Board array is null", boards);

        BoardInterface boardInterface0 = UTTTFactory.createBoard(),
                boardInterface1 = UTTTFactory.createBoard(),
                boardInterface2 = UTTTFactory.createBoard(),
                boardInterface3 = UTTTFactory.createBoard();

        boardInterface0.setMarkAt(Symbol.CROSS, 0);
        boardInterface1.setMarkAt(Symbol.CIRCLE, 2);
        boardInterface2.setMarkAt(Symbol.CROSS, 6);
        boardInterface3.setMarkAt(Symbol.CIRCLE, 8);

        simulatorInterface.setBoards(new BoardInterface[] {boardInterface0, UTTTFactory.createBoard(), boardInterface1,
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                boardInterface2, UTTTFactory.createBoard(), boardInterface3});

        boards = simulatorInterface.getBoards();

        assertTrue("BOARDS TEST FAILED: Board array length is not 9. Got a length of " + boards.length + ".", boards.length == 9);

        for(int i = 0; i < 9; i++)
            switch (i) {
                case 0:
                    assertTrue("BOARDS TEST FAILED: Boards are not in the right order. Right index = 0, got index " + i + ".",
                            boards[0].getMarks()[0].getSymbol() == Symbol.CROSS);
                    break;
                case 2:
                    assertTrue("BOARDS TEST FAILED: Boards are not in the right order. Right index = 2, got index " + i + ".",
                            boards[2].getMarks()[2].getSymbol() == Symbol.CIRCLE);
                    break;
                case 6:
                    assertTrue("BOARDS TEST FAILED: Boards are not in the right order. Right index = 6, got index " + i + ".",
                            boards[6].getMarks()[6].getSymbol() == Symbol.CROSS);
                    break;
                case 8:
                    assertTrue("BOARDS TEST FAILED: Boards are not in the right order. Right index = 8, got index " + i + ".",
                            boards[8].getMarks()[8].getSymbol() == Symbol.CIRCLE);
                    break;
                default:
                    break;
            }
    }

    @Test
    public void cCurrentPlayerTest() {
        simulatorInterface.setBoards(new BoardInterface[] {UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard()});

        simulatorInterface.setCurrentPlayerSymbol(Symbol.CIRCLE);

        assertTrue("CURRENT PLAYER TEST FAILED: Wrong symbol is now playing. Right symbol = " + Symbol.CIRCLE.toString()
                        + ", got " + simulatorInterface.getCurrentPlayerSymbol().toString() + ".",
                simulatorInterface.getCurrentPlayerSymbol() == Symbol.CIRCLE);

        simulatorInterface.setCurrentPlayerSymbol(Symbol.CROSS);

        assertTrue("CURRENT PLAYER TEST FAILED: Wrong symbol is now playing. Right symbol = " + Symbol.CROSS.toString()
                        + ", got " + simulatorInterface.getCurrentPlayerSymbol().toString() + ".",
                simulatorInterface.getCurrentPlayerSymbol() == Symbol.CROSS);
    }

    @Test
    public void dSetMarkTest() {
        simulatorInterface.setBoards(new BoardInterface[] {UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard()});

        simulatorInterface.setCurrentPlayerSymbol(Symbol.CROSS);

        Symbol currentSymbol = simulatorInterface.getCurrentPlayerSymbol();
        boolean bool = simulatorInterface.setMarkAt(currentSymbol, 4, 3);

        assertTrue("SET MARK TEST FAILED: #setMarkAt did not set the right symbol. Right symbol = " + currentSymbol.toString()
                        + ", got " + simulatorInterface.getBoards()[4].getMarks()[3].getSymbol().toString() + ".",
                (simulatorInterface.getBoards()[4].getMarks()[3].getSymbol() == currentSymbol));
        assertTrue("SET MARK TEST FAILED: #setMarkAt did not return the right boolean. Right boolean = true, got false.", bool);

        simulatorInterface.setIndexNextBoard(3);

        bool = simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol(), 4, 2);

        assertTrue("SET MARK TEST FAILED: #setMarkAt set the symbol even tho the board index =/= next index. Right index = "
                        + simulatorInterface.getIndexNextBoard() + ", got index 4.",
                (simulatorInterface.getBoards()[4].getMarks()[2].getSymbol() == Symbol.EMPTY));
        assertTrue("SET MARK TEST FAILED: #setMarkAt did not return the right boolean. Right boolean = false, got true.", !bool);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eMarksSymbolTest() {
        simulatorInterface.setMarkAt(null, 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eMarksNullTest() {
        simulatorInterface.setBoards(null);
        simulatorInterface.setCurrentPlayerSymbol(Symbol.CROSS);
        simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol(), 0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void eMarksWrongPlayerTest() {
        simulatorInterface.setCurrentPlayerSymbol(Symbol.CROSS);
        simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol().flip(), 0, 0);
    }

    @Test
    public void fNextIndexTest() {
        assertTrue("NEXT INDEX TEST FAILED: Next board index is not correct. Right index = -1, got " + simulatorInterface.getIndexNextBoard() + ".",
                simulatorInterface.getIndexNextBoard() == -1);

        BoardInterface[] boards = new BoardInterface[9];

        for(int k = 0; k < 9; k++)
            boards[k] = UTTTFactory.createBoard();

        simulatorInterface.setBoards(boards);

        simulatorInterface.setIndexNextBoard(1);
        assertTrue("NEXT INDEX TEST FAILED: Next board index is not correct. Right index = 1, got " + simulatorInterface.getIndexNextBoard() + ".",
                simulatorInterface.getIndexNextBoard() == 1);

        simulatorInterface.setIndexNextBoard(7);
        assertTrue("NEXT INDEX TEST FAILED: Next board index is not correct. Right index = 7, got " + simulatorInterface.getIndexNextBoard() + ".",
                simulatorInterface.getIndexNextBoard() == 7);

        simulatorInterface.setIndexNextBoard(2);
        assertTrue("NEXT INDEX TEST FAILED: Next board index is not correct. Right index = 2, got " + simulatorInterface.getIndexNextBoard() + ".",
                simulatorInterface.getIndexNextBoard() == 2);

        simulatorInterface.setIndexNextBoard(-1);
        assertTrue("NEXT INDEX TEST FAILED: Next board index is not correct. Right index = -1, got " + simulatorInterface.getIndexNextBoard() + ".",
                simulatorInterface.getIndexNextBoard() == -1);
    }

    @Test
    public void gMovePossibleTest() {
        BoardInterface[] boards = new BoardInterface[9];

        for(int k = 0; k < 9; k++)
            boards[k] = UTTTFactory.createBoard();

        simulatorInterface.setBoards(boards);
        simulatorInterface.setCurrentPlayerSymbol(Symbol.CROSS);
        simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol(), 0, 1);

        for(int i = 0; i < 9; i++)
            assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible(b,m): Move is not possible when it is. Set board index = 1, checked (1," + i + ")",
                    simulatorInterface.isMovePossible(1, i));

        simulatorInterface.setIndexNextBoard(1);

        boolean bool = simulatorInterface.isMovePossible(0);
        assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible(b) returned the wrong boolean.", !bool);

        bool = simulatorInterface.isMovePossible(0, 1);
        assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible(b,m) returned the wrong boolean.", !bool);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h1MovePossibleBLowerBoundsTest() {
        simulatorInterface.isMovePossible(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h2MovePossibleBUpperTest() {
        simulatorInterface.isMovePossible(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h3MovePossibleBLowerMLowerBoundsTest() {
        simulatorInterface.isMovePossible(-1, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h4MovePossibleBLowerMUpperBoundsTest() {
        simulatorInterface.isMovePossible(-1, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h5MovePossibleBUpperMLowerBoundsTest() {
        simulatorInterface.isMovePossible(9, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h6MovePossibleBUpperMUpperBoundsTest() {
        simulatorInterface.isMovePossible(9, 9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h7MovePossibleBMLowerTest() {
        simulatorInterface.isMovePossible(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void h8MovePossibleBMUpperTest() {
        simulatorInterface.isMovePossible(0, 9);
    }

    @Test
    public void iWinnerGameOverTest() {
        for(int i = 1; i < 2; i++) {
            Symbol symbol = (i == 1) ? Symbol.CROSS : Symbol.CIRCLE;

            //Vertical lines
            for (int j = 0; j < 3; j++) {
                simulatorInterface = UTTTFactory.createSimulator();
                BoardInterface[] boards = new BoardInterface[9];

                for(int k = 0; k < 9; k++)
                    boards[k] = UTTTFactory.createBoard();

                simulatorInterface.setBoards(boards);

                for(int k = 0; k < 3; k++) {
                    simulatorInterface.getBoards()[j].setMarkAt(symbol, k);
                    simulatorInterface.getBoards()[j + 3].setMarkAt(symbol, k);
                    simulatorInterface.getBoards()[j + 6].setMarkAt(symbol, k);
                }

                assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner "
                        + simulatorInterface.getWinner().toString() + ".", simulatorInterface.getWinner() == symbol);
                assertTrue("GAME OVER TEST FAILED: Game has not been identified as over.",
                        simulatorInterface.isGameOver());
            }

            //Horizontal lines
            for (int j = 0; j < 3; j++) {
                simulatorInterface = UTTTFactory.createSimulator();
                BoardInterface[] boards = new BoardInterface[9];

                for(int k = 0; k < 9; k++)
                    boards[k] = UTTTFactory.createBoard();

                simulatorInterface.setBoards(boards);

                for(int k = 0; k < 3; k++) {
                    simulatorInterface.getBoards()[j * 3].setMarkAt(symbol, k);
                    simulatorInterface.getBoards()[j * 3 + 1].setMarkAt(symbol, k);
                    simulatorInterface.getBoards()[j * 3 + 2].setMarkAt(symbol, k);
                }

                assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner "
                        + simulatorInterface.getWinner().toString() + ".", simulatorInterface.getWinner() == symbol);
                assertTrue("GAME OVER TEST FAILED: Game has not been identified as over.",
                        simulatorInterface.isGameOver());
            }

            //Diagonal line top left -> bottom right
            simulatorInterface = UTTTFactory.createSimulator();
            BoardInterface[] boards = new BoardInterface[9];

            for(int k = 0; k < 9; k++)
                boards[k] = UTTTFactory.createBoard();

            simulatorInterface.setBoards(boards);

            for(int k = 0; k < 3; k++) {
                simulatorInterface.getBoards()[0].setMarkAt(symbol, k);
                simulatorInterface.getBoards()[4].setMarkAt(symbol, k);
                simulatorInterface.getBoards()[8].setMarkAt(symbol, k);
            }

            assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner "
                    + simulatorInterface.getWinner().toString() + ".", simulatorInterface.getWinner() == symbol);
            assertTrue("GAME OVER TEST FAILED: Game has not been identified as over.",
                    simulatorInterface.isGameOver());

            //Diagonal line top right -> bottom left
            simulatorInterface = UTTTFactory.createSimulator();
            boards = new BoardInterface[9];

            for(int k = 0; k < 9; k++)
                boards[k] = UTTTFactory.createBoard();

            simulatorInterface.setBoards(boards);

            for(int k = 0; k < 3; k++) {
                simulatorInterface.getBoards()[2].setMarkAt(symbol, k);
                simulatorInterface.getBoards()[4].setMarkAt(symbol, k);
                simulatorInterface.getBoards()[6].setMarkAt(symbol, k);
            }

            assertTrue("WINNER TEST FAILED: Right winner = " + symbol.toString() + ", got winner "
                    + simulatorInterface.getWinner().toString() + ".", simulatorInterface.getWinner() == symbol);
            assertTrue("GAME OVER TEST FAILED: Game has not been identified as over.",
                    simulatorInterface.isGameOver());
        }

        simulatorInterface = UTTTFactory.createSimulator();

        assertTrue("WINNER TEST FAILED: No winner yet but got as winner symbol " + simulatorInterface.getWinner() + ".",
                simulatorInterface.getWinner() == Symbol.EMPTY);
        assertTrue("GAME OVER TEST FAILED: Game has been identified as over when it is not.",
                !simulatorInterface.isGameOver());

        simulatorInterface = UTTTFactory.createSimulator();
        BoardInterface[] boards = new BoardInterface[9];

        for(int k = 0; k < 9; k++)
            boards[k] = UTTTFactory.createBoard();

        simulatorInterface.setBoards(boards);

        assertTrue("WINNER TEST FAILED: No winner yet but got as winner symbol " + simulatorInterface.getWinner() + ".",
                simulatorInterface.getWinner() == Symbol.EMPTY);
        assertTrue("GAME OVER TEST FAILED: Game has been identified as over when it is not.",
                !simulatorInterface.isGameOver());

        simulatorInterface = UTTTFactory.createSimulator();
        boards = new BoardInterface[9];

        for(int k = 0; k < 9; k++)
            boards[k] = UTTTFactory.createBoard();
        simulatorInterface.setBoards(boards);

        for(int k = 0; k < 3; k++) {
            simulatorInterface.getBoards()[0].setMarkAt(Symbol.CROSS, k);
            simulatorInterface.getBoards()[1].setMarkAt(Symbol.CROSS, k);
            simulatorInterface.getBoards()[2].setMarkAt(Symbol.CIRCLE, k);
            simulatorInterface.getBoards()[3].setMarkAt(Symbol.CIRCLE, k);
            simulatorInterface.getBoards()[4].setMarkAt(Symbol.CROSS, k);
            simulatorInterface.getBoards()[5].setMarkAt(Symbol.CROSS, k);
            simulatorInterface.getBoards()[6].setMarkAt(Symbol.CROSS, k);
            simulatorInterface.getBoards()[7].setMarkAt(Symbol.CIRCLE, k);
            simulatorInterface.getBoards()[8].setMarkAt(Symbol.CIRCLE, k);
        }

        assertTrue("WINNER TEST FAILED: It is a tie but got as winner symbol " + simulatorInterface.getWinner() + ".",
                simulatorInterface.getWinner() == Symbol.EMPTY);
        assertTrue("GAME OVER TEST FAILED: Game has not been identified as over when it is a tie.",
                simulatorInterface.isGameOver());
    }
}
