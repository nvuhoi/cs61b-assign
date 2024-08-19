package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> a = new AListNoResizing<>();
        BuggyAList<Integer> b = new BuggyAList<>();
        for (int i = 4; i < 7; i++) {
            a.addLast(i);
            b.addLast(i);
        }
        int testTimes = a.size();
        for (int i = 0; i < testTimes; i++) {
            assertEquals(a.removeLast(), b.removeLast());
        }
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> M = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int sizeM = M.size();
                assertEquals(size, sizeM);
            } else if (operationNumber == 2) {
                if (L.size() == 0) {
                    continue;
                }
                int last = L.getLast();
                int lastM = M.getLast();
                assertEquals(last, lastM);
            } else if (operationNumber == 3) {
                if (L.size() == 0) {
                    continue;
                }
                int removeLast = L.removeLast();
                int removeLastM = M.removeLast();
                assertEquals(removeLast, removeLastM);
            }
        }
    }
}
