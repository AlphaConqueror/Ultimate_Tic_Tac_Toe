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
        BoardInterface[] boards = simulatorInterface.getBoards();

        assertNotNull("BOARDS TEST FAILED: Board array is null", boards);

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

        assertTrue("CURRENT PLAYER TEST FAILED: Wrong player begins the game. Right symbol = "
                        + Symbol.CROSS.toString() + ", got symbol " + simulatorInterface.getCurrentPlayerSymbol().toString(),
                simulatorInterface.getCurrentPlayerSymbol() == Symbol.CROSS);


    }

    @Test
    public void cNextIndexTest() {

    }

    @Test
    public void eSetMarkTest() {
        simulatorInterface = UTTTFactory.createSimulator();
    }
}
