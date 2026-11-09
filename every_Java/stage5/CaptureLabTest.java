import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

class CaptureLabTest {
    
    @Test
    void countMatches_atLiastFive_countsThree() {
        assertEquals(3, CaptureLab.countMatches(List.of(5, 1, 8, 3, 10), CaptureLab.atLeast(5)));
    }
}
