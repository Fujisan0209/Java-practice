import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class WeekOneLabTest {
    @Test
    void select_foodAndAtLeast500_returnsTwoItems() {
        List<WeekOneLab.Expense> list = List.of(
            new WeekOneLab.Expense("lunch", "food", 800),
            new WeekOneLab.Expense("pen", "daily", 150),
            new WeekOneLab.Expense("cake", "food", 400),
            new WeekOneLab.Expense("book", "hobby", 1500),
            new WeekOneLab.Expense("dinner", "food", 1200));
        assertEquals(2, WeekOneLab.select(list, WeekOneLab.inCategory("food").and(WeekOneLab.atLeast(500))).size());
    }
}
