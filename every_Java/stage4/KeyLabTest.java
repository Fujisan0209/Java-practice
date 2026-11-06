import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class KeyLabTest {
    @Test
    void countVisits_samePointTwice_countsTwo() {
        List<KeyLab.Point> points = new ArrayList<>();
        points.add(new KeyLab.Point(3, 4));
        points.add(new KeyLab.Point(3, 4));
        
        assertEquals(2, KeyLab.countVisits(points).get(new KeyLab.Point(3, 4)));

    }
}
