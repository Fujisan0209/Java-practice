import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ExceptionLabTest {
    @Test
    void parseLine_twoColumns_throwBadLine() {
        assertThrows(ExceptionLab.BadLineException.class, () -> ExceptionLab.parseLine("lunch,food"));
    }
}
