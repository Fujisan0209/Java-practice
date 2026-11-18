import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class PartitionLabTest {
    @Test
    void splitByAmount_getTrue_returnsEmptyList() {
        List<PartitionLab.Expense> list = List.of(
            new PartitionLab.Expense("lunch", "food", 800),
            new PartitionLab.Expense("book", "hobby", 1500),
            new PartitionLab.Expense("coffee", "food", 300),
            new PartitionLab.Expense("shampoo", "daily", 600),
            new PartitionLab.Expense("game", "hobby", 5000)
        );

        assertEquals(List.of(), PartitionLab.splitByAmount(list, 100000).get(true));
    }
}
