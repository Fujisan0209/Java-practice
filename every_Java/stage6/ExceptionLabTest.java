import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionLabTest {
    @Test
    void parseLine_twoColumns_throwsBadLine() {
        assertThrows(ExceptionLab.BadLineException.class, () -> ExceptionLab.parseLine("lunch,food"));
    }
}
