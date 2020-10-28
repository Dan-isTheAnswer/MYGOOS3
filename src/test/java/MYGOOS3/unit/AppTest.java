package MYGOOS3.unit;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


import static org.hamcrest.core.StringContains.containsString;

import org.junit.Test;

/**
 * Unit test for simple App.
 * This class for unit test is supposed to figure out 
 * the difference between junit only and junit with hamcrest. 
 * 
 * The result is much significant with respect to seeking problems. 
 */
public class AppTest {
    /**
     * Rigorous Test.
     */
    @Test
    public void testApp() {
        assertTrue("this is a test ", 1 == 2);
    }

    @Test
    public void testAppWithHamcrest() {
        String a = "I want some banana";
        assertThat("This is a test with Hamcrest", a, containsString("banna"));
    }
}
