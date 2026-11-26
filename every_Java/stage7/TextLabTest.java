import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class TextLabTest {
    @Test
    void report_oneLine_addsTotalRow() {
        assertEquals("""
            tea          120
            ----------------
            total        120""", TextLab.report("tea,120"));
    }
}
