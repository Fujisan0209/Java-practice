import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class SetLabTest {
    
    @Test
    void check_duplicates() {
        List<String> words = List.of("a", "b", "a", "c", "a");
        assertEquals("[a]", String.valueOf(SetLab.duplicates(words)));
    }

   @Test
    void check_duplicates_null() {
        assertThrows(IllegalArgumentException.class, () -> SetLab.duplicates(null));
    }
}
