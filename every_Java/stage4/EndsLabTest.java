import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class EndsLabTest {    
    @Test
    void chaeckBasicEnds() {
        assertEquals("5..8", EndsLab.ends(new ArrayDeque<>(List.of(5, 6, 7, 8))));
    }

    @Test
    void checkEmptyEnds() {
        assertThrows(IllegalArgumentException.class, () -> EndsLab.ends(new ArrayList<>()));
    }
}
