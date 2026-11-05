import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class BudgetBookLabTest {
    @Test
    void totalByCategory_sumsEachCategory() {
        List<BudgetBookLab.Expense> book = new ArrayList<>(List.of(
            new BudgetBookLab.Expense("lunch",  "food",  800, 1),
            new BudgetBookLab.Expense("soap",   "daily", 300, 2),
            new BudgetBookLab.Expense("dinner", "food", 1200, 3)));
        assertEquals("{food=2000, daily=300}", String.valueOf(BudgetBookLab.totalByCategory(book)));
    }
}
