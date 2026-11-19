import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class OptionalLabTest {
    @Test
    void remaining_noBudget_throwsISE() {
        List<OptionalLab.Expense> list = List.of(
            new OptionalLab.Expense("lunch", "food", 800),
            new OptionalLab.Expense("book", "hobby", 1500),
            new OptionalLab.Expense("coffee", "food", 300),
            new OptionalLab.Expense("lunch", "food", 650));
        Map<String, Integer> budgets = Map.of("food", 3000, "hobby", 1000);

        assertThrows(IllegalStateException.class, () -> OptionalLab.remaining(budgets, list, "travel"));
    }
}
