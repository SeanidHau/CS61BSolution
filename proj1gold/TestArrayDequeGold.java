import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDeque() {
        StudentArrayDeque<Integer> testArray = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> stdArray = new ArrayDequeSolution<>();
        String info = "";

        for (int i = 0; i < 100; i++) {
            if (testArray.isEmpty()) {
                int number = StdRandom.uniform(100);
                int operation = StdRandom.uniform(2);

                if (operation == 0) {
                    testArray.addFirst(number);
                    stdArray.addFirst(number);
                    info = info + "addFirst(" + number + ")\n";
                }
                else {
                    testArray.addLast(number);
                    stdArray.addLast(number);
                    info = info + "addLast(" + number + ")\n";
                }
            }
            else {
                int number = StdRandom.uniform(100);
                int operation = StdRandom.uniform(4);
                Integer testRemoveNumber = 0;
                Integer stdRemoveNumber = 0;

                switch (operation) {
                    case 0:
                        testArray.addFirst(number);
                        stdArray.addFirst(number);
                        info = info + "addFirst(" + number + ")\n";
                        break;
                    case 1:
                        testArray.addLast(number);
                        stdArray.addLast(number);
                        info = info + "addLast(" + number + ")\n";
                        break;
                    case 2:
                        testRemoveNumber = testArray.removeFirst();
                        stdRemoveNumber = stdArray.removeFirst();
                        info = info + "removeFirst()\n";
                        break;
                    case 3:
                        testRemoveNumber = testArray.removeLast();
                        stdRemoveNumber = stdArray.removeLast();
                        info = info + "removeLast()\n";
                        break;
                    default:
                }
                assertEquals(info, testRemoveNumber, stdRemoveNumber);
            }
        }
    }
}
