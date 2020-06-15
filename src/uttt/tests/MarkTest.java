package uttt.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import uttt.UTTTFactory;
import uttt.game.MarkInterface;
import uttt.utils.Symbol;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MarkTest {

    @Test
    public void aCreateMarkTest() {
        for(int i = 0; i < 9; i++) {
            Symbol symbol = i % 2 == 0 ? Symbol.CROSS : Symbol.CIRCLE;
            MarkInterface markInterface = UTTTFactory.createMark(symbol, i);

            assertNotNull("CREATE MARK TEST FAILED: MarkInterface is null.", markInterface);
            assertTrue("CREATE MARK TEST FAILED: Mark at the wrong position. Right position = " + i + ", got position " + markInterface.getPosition(),
                    markInterface.getPosition() == i);
            assertTrue("CREATE MARK TEST FAILED: Wrong symbol. Right symbol = " + symbol.toString() + ", got symbol " + markInterface.getSymbol(),
                    markInterface.getSymbol() == symbol);
        }

        //Got IllegalArgumentsException after testing #createMark(symbol, [-1,9]).
    }

    @Test
    public void bSymbolTest() {
        MarkInterface markInterface = UTTTFactory.createMark(Symbol.EMPTY, 0);

        for(int i = 0; i < 20; i++) {
            Symbol symbol = i % 2 == 0 ? Symbol.CROSS : Symbol.CIRCLE;

            markInterface.setSymbol(symbol);
            assertTrue("SYMBOL TEST FAILED: Wrong symbol. Right symbol = " + symbol.toString() + ", got symbol " + markInterface.getSymbol(),
                    markInterface.getSymbol() == symbol);
        }
    }
}
