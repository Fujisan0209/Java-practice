import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class RegexLabTest {
    @Test
    void firstQuoted_twoQuotes_returnsFirstOnly() {
        assertEquals(Optional.of("A"), RegexLab.firstQuoted("\"A\"と\"B\""));
    }
}
