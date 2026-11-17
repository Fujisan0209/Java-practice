import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class SumLabTest {
    @Test
    void totalByCategory_twoFood_sumsAmounts() {
        List<SumLab.Expense> list = List.of(
            new SumLab.Expense("lunch",  "food",  800, 1),
            new SumLab.Expense("train",  "daily", 300, 1),
            new SumLab.Expense("dinner", "food",  700, 2));

        assertEquals(1500, SumLab.totalByCategory(list).get("food"));
    }
}
