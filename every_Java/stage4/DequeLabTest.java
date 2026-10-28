import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class DequeLabTest {
    
    @Test
    void forward_afterVisit_throws() {
        DequeLab d = new DequeLab("a");
        d.visit("b");
        assertEquals("a", d.back());

        d.visit("c");
        assertThrows(IllegalStateException.class, () -> d.forward());
    }
}
