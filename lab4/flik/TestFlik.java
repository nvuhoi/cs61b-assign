package flik;
import org.junit.Test;
import static org.junit.Assert.*;
public class TestFlik {
    @Test
    public void testInteger() {
        Integer j = 10;
        for (Integer i = 10; i < 129; i++, j++) {
            assertTrue(i == j);
        }
    }
}
