import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class StreamLabTest {
    @Test
    void countIn_emptyList_returnZero() {
        assertEquals(0, StreamLab.countIn(new ArrayList<>(), "food"));
    }
}
