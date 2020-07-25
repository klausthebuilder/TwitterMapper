package test.filters;

import filters.*;
import org.junit.jupiter.api.Test;
import twitter4j.Status;

import static org.junit.jupiter.api.Assertions.*;

public class TestFilters {
    @Test
    public void testSimpleFilter() {
        Filter filter = new SimpleFilter("fred");
        assertFilterOR(filter);
    }

    @Test
    public void testFilterNOT() {
        Filter filter = new NotFilter(new SimpleFilter("fred"));
        assertFalse(filter.matches(getMockStatus("Fred Flintstone")));
        assertFalse(filter.matches(getMockStatus("fred Flintstone")));
        assertTrue(filter.matches(getMockStatus("Red Skelton")));
        assertTrue(filter.matches(getMockStatus("red Skelton")));
    }

    @Test
    public void testFilterAND() {
        Filter filter1 = new AndFilter(new SimpleFilter("fre"), new SimpleFilter("fre"));
        assertFilterOR(filter1);

        Filter filter2 = new AndFilter(new SimpleFilter("Fred"), new SimpleFilter("Marc"));
        assertFalse(filter2.matches(getMockStatus("Fred Flintstone")));
        assertFalse(filter2.matches(getMockStatus("fred Flintstone")));
        assertFalse(filter2.matches(getMockStatus("Red Skelton")));
        assertFalse(filter2.matches(getMockStatus("red Skelton")));

        Filter filter3 = new AndFilter(new SimpleFilter("red"), new SimpleFilter("red"));
        assertTrue(filter3.matches(getMockStatus("Fred Flintstone")));
        assertTrue(filter3.matches(getMockStatus("fred Flintstone")));
        assertTrue(filter3.matches(getMockStatus("Red Skelton")));
        assertTrue(filter3.matches(getMockStatus("red Skelton")));

        Filter filter4 = new AndFilter(new SimpleFilter("douni"), new SimpleFilter("matis"));
        assertEquals("(douni and matis)", filter4.toString());
    }

    @Test
    public void testFilterOR() {
        Filter filter1 = new OrFilter(new SimpleFilter("zzzz"), new SimpleFilter("Fred"));
        assertFilterOR(filter1);
        Filter filter2 = new OrFilter(new SimpleFilter("fred"), new SimpleFilter("zzzz"));
        assertFilterOR(filter2);
    }

    private void assertFilterOR(Filter filter) {
        assertTrue(filter.matches(getMockStatus("Fred Flintstone")));
        assertTrue(filter.matches(getMockStatus("fred Flintstone")));
        assertFalse(filter.matches(getMockStatus("Red Skelton")));
        assertFalse(filter.matches(getMockStatus("red Skelton")));
    }

    private Status getMockStatus(String text) {
        return new MockStatus(text);
    }
}
