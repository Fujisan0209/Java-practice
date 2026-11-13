import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ConsumerLabTest {
    @Test
    void applyAll_collectInto_collectsNamesInOrder() {
        List<ConsumerLab.Expense> list = List.of(
            new ConsumerLab.Expense("lunch", "food", 800),
            new ConsumerLab.Expense("book", "hobby", 1500)
        );
        List<String> out = new ArrayList<>();
        ConsumerLab.applyAll(list, ConsumerLab.collectInto(out));
        assertEquals(List.of("lunch", "book"), out);
    }
}
