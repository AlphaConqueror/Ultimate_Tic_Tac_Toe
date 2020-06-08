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


    }
}
