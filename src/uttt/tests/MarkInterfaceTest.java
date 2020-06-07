package uttt.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import uttt.game.MarkInterface;
import uttt.UTTTFactory;
import uttt.utils.Symbol;

public class MarkInterfaceTest {

    @Test
    public void createMarkTest() {
        for(int i = 0; i < 9; i++) {
            Symbol symbol = i % 2 == 0 ? Symbol.CROSS : Symbol.CIRCLE;
            MarkInterface markInterface = UTTTFactory.createMark(symbol, i);

            assertNotNull("CREATE MARK TEST FAILED: MarkInterface is null.", markInterface);
            assertTrue("CREATE MARK TEST FAILED: Mark at the wrong position. Right position = " + i + ", got position " + markInterface.getPosition(),
                    markInterface.getPosition() == i);
            assertTrue("CREATE MARK TEST FAILED: Wrong symbol. Right symbol = " + symbol.toString() + ", got symbol " + markInterface.getSymbol(),
                    markInterface.getSymbol() == symbol);
        }
    }

    @Test
    public void symbolTest() {
        MarkInterface markInterface = UTTTFactory.createMark(Symbol.EMPTY, 0);

        for(int i = 0; i < 20; i++) {
            Symbol symbol = i % 2 == 0 ? Symbol.CROSS : Symbol.CIRCLE;

            markInterface.setSymbol(symbol);
            assertTrue("SYMBOL TEST FAILED: Wrong symbol. Right symbol = " + symbol.toString() + ", got symbol " + markInterface.getSymbol(),
                    markInterface.getSymbol() == symbol);
        }
    }
}
