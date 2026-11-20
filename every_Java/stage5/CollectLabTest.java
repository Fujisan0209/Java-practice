import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class CollectLabTest {
    @Test
    void amountByName_sameNameTwice_sumsAmounts() {
        List<CollectLab.Expense> list = List.of(
            new CollectLab.Expense("lunch",  "food", 800),
            new CollectLab.Expense("coffee", "food", 300),
            new CollectLab.Expense("lunch",  "food", 700)
        );

        assertEquals("{lunch=1500, coffee=300}", String.valueOf(CollectLab.amountByName(list)));
    }
}
