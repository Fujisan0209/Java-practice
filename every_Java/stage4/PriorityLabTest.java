import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

class PriorityLabTest {
    List<Task> task = List.of(new Task("b", 2), new Task("a", 2));
    PriorityQueue<Task> q = PriorityLab.byPriority(task);
    List<String> names = PriorityLab.drainInOrder(q);
    
    @Test
    void sameValueSortsByName() {
        assertEquals(List.of("a", "b"), names);
    }
}
