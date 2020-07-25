package test.filters;

import filters.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test the parser.
 */
public class TestParser {
    @Test
    public void testBasic() throws IncorrectSyntaxException {
        Filter filter = new Parser("trump").parse();
        assertTrue(filter instanceof SimpleFilter);
        assertEquals("trump", ((SimpleFilter) filter).getWord());
    }

    @Test
    public void testNot() throws IncorrectSyntaxException {
        Filter f = new Parser("not trump").parse();
        assertTrue(f instanceof NotFilter);
        //   assertTrue(((SimpleFilter)f).getWord().equals("not trump"));
    }

    @Test
    public void testHairy() throws IncorrectSyntaxException {
        Filter x = new Parser("trump and (evil or blue) and red or green and not not purple").parse();
        assertEquals("(((trump and (evil or blue)) and red) or (green and not not purple))", x.toString());
    }
}
