import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class FreezeLabTest {
    
    @Test
    void checkSnapshot() {
        Roster r = new Roster();
        r.add("佐藤");
        r.add("鈴木");
        List<String> snap = r.snapshot();
        r.add("高橋"); 
        assertEquals(2, snap.size());
    }

    @Test
    void viewNamesAddThrowsUOE() {
        Roster r = new Roster();
        r.add("佐藤");
        r.add("鈴木");
        List<String> view = r.viewNames();
        assertThrows(UnsupportedOperationException.class, () -> view.add("高橋"));
    }
}
