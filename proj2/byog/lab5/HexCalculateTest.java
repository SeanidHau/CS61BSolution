package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

public class HexCalculateTest {
    @Test
    public void hexWidthCalculateTest() {
        assertEquals(HexWorld.hexWidthCalculate(2, 0), 2);
        assertEquals(HexWorld.hexWidthCalculate(2, 1), 4);
        assertEquals(HexWorld.hexWidthCalculate(2, 3), 2);
        assertEquals(HexWorld.hexWidthCalculate(3, 0), 3);
        assertEquals(HexWorld.hexWidthCalculate(3, 1), 5);
        assertEquals(HexWorld.hexWidthCalculate(5, 0), 5);
        assertEquals(HexWorld.hexWidthCalculate(5, 3), 11);
    }

    @Test
    public void hexhexLineBeginPosXTest() {
        assertEquals(HexWorld.hexLineBeginPosX(2, 0), 0);
        assertEquals(HexWorld.hexLineBeginPosX(2, 1), -1);
        assertEquals(HexWorld.hexLineBeginPosX(2, 3), 0);
        assertEquals(HexWorld.hexLineBeginPosX(3, 0), 0);
        assertEquals(HexWorld.hexLineBeginPosX(3, 2), -2);
        assertEquals(HexWorld.hexLineBeginPosX(5, 0), 0);
        assertEquals(HexWorld.hexLineBeginPosX(5, 9), 0);
    }
}
