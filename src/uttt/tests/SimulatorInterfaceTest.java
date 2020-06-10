package uttt.tests;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;

import org.junit.runners.MethodSorters;
import uttt.UTTTFactory;
import uttt.game.BoardInterface;
import uttt.game.SimulatorInterface;
import uttt.utils.Symbol;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimulatorInterfaceTest {

    private SimulatorInterface simulatorInterface;

    @Test
    public void aCreateSimulatorTest() {
        simulatorInterface = UTTTFactory.createSimulator();

        assertNotNull("CREATE SIMULATOR TEST FAILED: Simulator is null.", simulatorInterface);
    }

    @Test
    public void bBoardsTest() {
        assertNotNull("BOARDS TEST FAILED: Board array is null", simulatorInterface.getBoards());

        BoardInterface[] boards = simulatorInterface.getBoards();

        for(BoardInterface board : boards)
            assertNull("BOARDS TEST FAILED: Created simulator is not initialized null.", board);

        BoardInterface boardInterface0 = UTTTFactory.createBoard(),
                       boardInterface1 = UTTTFactory.createBoard(),
                       boardInterface2 = UTTTFactory.createBoard(),
                       boardInterface3 = UTTTFactory.createBoard();

        boardInterface0.setMarkAt(Symbol.CROSS, 0);
        boardInterface1.setMarkAt(Symbol.CIRCLE, 2);
        boardInterface2.setMarkAt(Symbol.CROSS, 6);
        boardInterface3.setMarkAt(Symbol.CIRCLE, 8);

        simulatorInterface.setBoards(new BoardInterface[] {boardInterface0, null, boardInterface1,
                                                           null, null, null,
                                                           boardInterface2, null, boardInterface3});

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
                    assertNull("BOARDS TEST FAILED: Boards are not in the right order. Board is not null where it should be. Index = " + i + ".",
                            boards[i]);
                    break;
            }
    }

    @Test
    public void cCurrentPlayerTest() {
        simulatorInterface = UTTTFactory.createSimulator();

        //No need to test if the right player starts bc it's the job of #run

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
        simulatorInterface = UTTTFactory.createSimulator();

        simulatorInterface.setBoards(new BoardInterface[] {UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard(),
                UTTTFactory.createBoard(), UTTTFactory.createBoard(), UTTTFactory.createBoard()});

        boolean bool = simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol().flip(), 4, 4);

        assertTrue("SET MARK TEST FAILED: #setMarkAt returns false but sets mark.",
                simulatorInterface.getBoards()[4].getMarks()[4].getSymbol() == Symbol.EMPTY && !bool);

        Symbol currentSymbol = simulatorInterface.getCurrentPlayerSymbol();
        bool = simulatorInterface.setMarkAt(currentSymbol, 4, 4);

        assertTrue("SET MARK TEST FAILED: #setMarkAt did not set the right symbol. Right symbol = " + currentSymbol.toString()
                        + ", got " + simulatorInterface.getBoards()[4].getMarks()[4].getSymbol().toString() + ".",
                simulatorInterface.getBoards()[4].getMarks()[4].getSymbol() == currentSymbol && bool);

        bool = simulatorInterface.setMarkAt(currentSymbol, -1, 4);
        assertTrue("SET MARK TEST FAILED: #setMarkAt boardIndex out of bounds. Bounds = [0, 8], got -1.", !bool);

        bool = simulatorInterface.setMarkAt(currentSymbol, 9, 4);
        assertTrue("SET MARK TEST FAILED: #setMarkAt boardIndex out of bounds. Bounds = [0, 8], got 9.", !bool);

        bool = simulatorInterface.setMarkAt(currentSymbol, 4, -1);
        assertTrue("SET MARK TEST FAILED: #setMarkAt marIndex out of bounds. Bounds = [0, 8], got -1.", !bool);

        bool = simulatorInterface.setMarkAt(currentSymbol, 4, 9);
        assertTrue("SET MARK TEST FAILED: #setMarkAt markIndex out of bounds. Bounds = [0, 8], got 9.", !bool);
    }

    @Test
    public void eNextIndexTest() {
        simulatorInterface = UTTTFactory.createSimulator();

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
    public void fMovePossibleTest() {
        simulatorInterface = UTTTFactory.createSimulator();
        BoardInterface[] boards = new BoardInterface[9];

        for(int k = 0; k < 9; k++)
            boards[k] = UTTTFactory.createBoard();

        simulatorInterface.setBoards(boards);

        simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol(), 0, 1);

        for(int i = 0; i < 9; i++)
            assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible(b,m): Move is not possible when it is. Set board index = 1, checked (1," + i + ")",
                simulatorInterface.isMovePossible(1, i));

        simulatorInterface.setMarkAt(simulatorInterface.getCurrentPlayerSymbol(), 1, 8);
        assertTrue("MOVE POSSIBLE TEST FAILED: #isMovePossible(b,m): Move is possible when it is not. Set board index = 1, checked (1,0)",
                !simulatorInterface.isMovePossible(1, 0));
    }

    @Test
    public void gWinnerGameOverTest() {
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
                    simulatorInterface.setMarkAt(symbol, j, k);
                    simulatorInterface.setMarkAt(symbol, j + 3, k);
                    simulatorInterface.setMarkAt(symbol, j + 6, k);
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
                    simulatorInterface.setMarkAt(symbol, j * 3, k);
                    simulatorInterface.setMarkAt(symbol, j * 3 + 1, k);
                    simulatorInterface.setMarkAt(symbol, j * 3 + 2, k);
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
                simulatorInterface.setMarkAt(symbol, 0, k);
                simulatorInterface.setMarkAt(symbol, 4, k);
                simulatorInterface.setMarkAt(symbol, 8, k);
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
                simulatorInterface.setMarkAt(symbol, 2, k);
                simulatorInterface.setMarkAt(symbol, 4, k);
                simulatorInterface.setMarkAt(symbol, 6, k);
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
            simulatorInterface.setMarkAt(Symbol.CROSS, 0, k);
            simulatorInterface.setMarkAt(Symbol.CROSS, 1, k);
            simulatorInterface.setMarkAt(Symbol.CIRCLE, 2, k);
            simulatorInterface.setMarkAt(Symbol.CROSS, 3, k);
            simulatorInterface.setMarkAt(Symbol.CROSS, 4, k);
            simulatorInterface.setMarkAt(Symbol.CIRCLE, 5, k);
            simulatorInterface.setMarkAt(Symbol.CIRCLE, 6, k);
            simulatorInterface.setMarkAt(Symbol.CIRCLE, 7, k);
            simulatorInterface.setMarkAt(Symbol.CROSS, 8, k);
        }

        assertTrue("WINNER TEST FAILED: It is a tie but got as winner symbol " + simulatorInterface.getWinner() + ".",
                simulatorInterface.getWinner() == Symbol.EMPTY);
        assertTrue("GAME OVER TEST FAILED: Game has not been identified as over when it is a tie.",
                simulatorInterface.isGameOver());
    }

    @Test
    public void hGameOverTest() {

    }
}
