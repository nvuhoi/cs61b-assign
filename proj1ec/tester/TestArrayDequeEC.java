package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;
public class TestArrayDequeEC {
    @Test
    public void randomizedTest() {
        ArrayDequeSolution<Integer> L = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> M = new StudentArrayDeque<>();
        String failList = "";

        int N = 1000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 6);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                M.addLast(randVal);
                failList += "addLast(" + randVal + ")\n";
            } else if (operationNumber == 1) {
                // size
                Integer size = L.size();
                Integer sizeM = M.size();
                failList += "size()\n";
                assertEquals(failList, size, sizeM);
            } else if (operationNumber == 2) {
                // removeLast
                if (L.size() == 0) {
                    continue;
                }
                Integer remove_last = L.removeLast();
                Integer remove_lastM = M.removeLast();
                failList += "removeLast()\n";
                assertEquals(failList, remove_last, remove_lastM);
            } else if (operationNumber == 3) {
                // removeFirst
                if (L.size() == 0) {
                    continue;
                }
                Integer remove_first = L.removeFirst();
                Integer remove_firstM = M.removeFirst();
                failList += "removeFirst()\n";
                assertEquals(failList, remove_first, remove_firstM);
            } else if (operationNumber == 4) {
                // addFirst
                int randVal = StdRandom.uniform(0, 100);
                L.addFirst(randVal);
                M.addFirst(randVal);
                failList += "addFirst(" + randVal + ")\n";
            } else if (operationNumber == 5) {
                // get
                if (L.size() == 0) {
                    continue;
                }
                int randVal = StdRandom.uniform(0, L.size());
                failList += "get(" + randVal + ")\n";
                assertEquals(failList, L.get(randVal), M.get(randVal));
            }
        }
    }
}
