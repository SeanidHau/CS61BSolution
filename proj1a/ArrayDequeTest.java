import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeTest {

    @Test
    public void emptyAddRemoveTest() {
        ArrayDeque<String> dq = new ArrayDeque<>();
        assertTrue(dq.isEmpty());

        dq.addFirst("firstitem");
        assertEquals(1, dq.size());

        dq.addLast("lastitem");
        assertEquals(2, dq.size());

        dq.printDeque();

        String firstItem = dq.removeFirst();
        assertEquals("firstitem", firstItem);

        String lastItem = dq.removeLast();
        assertEquals("lastitem", lastItem);

        assertEquals(0, dq.size());
    }

    @Test
    public void expandTest() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < 8; i++) {
            dq.addLast(i);
        }

        assertEquals(8, dq.size());
        dq.addLast(8);
        assertEquals(9, dq.size());

        for (int i = 0; i < 9; i++) {
            assertEquals(Integer.valueOf(i), dq.removeFirst());
        }
    }

    @Test
    public void shrinkTest() {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            dq.addLast(i);
        }

        for (int i = 0; i < 12; i++) {
            dq.removeFirst();
        }

        assertEquals(4, dq.size());
        dq.shrink();

        for (int i = 12; i < 16; i++) {
            assertEquals(Integer.valueOf(i), dq.removeFirst());
        }
    }

}
