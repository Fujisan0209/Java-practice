import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BracketLabTest {
    @Test
    void firstErrorIndex_crossedPair_returnsIndexOfClose() {
        assertEquals(2, BracketLab.firstErrorIndex("([)]"));
    }
}
