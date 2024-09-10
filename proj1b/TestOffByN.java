import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {

    static CharacterComparator offBy5 = new OffByN(5);

    @Test
    public void testOffByN() {
        offBy5.equalChars('a', 'f');
        offBy5.equalChars('f', 'a');
        offBy5.equalChars('f', 'h');
    }
}
