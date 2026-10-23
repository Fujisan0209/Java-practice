import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

class IteratorLabTest {
    @Test
    void ErrorThrow() {
        assertThrows(IllegalArgumentException.class, () -> IteratorLab.removeShort(null, 3));
    }
}
