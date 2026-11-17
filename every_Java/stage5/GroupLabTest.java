import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

class GroupLabTest {
    @Test
    void byCategory_food_hasTwoItems() {
        List<GroupLab.Expense> data = List.of(
            new GroupLab.Expense("lunch", "food", 800),
            new GroupLab.Expense("train", "daily", 300),
            new GroupLab.Expense("cafe", "food", 450));
        
        assertEquals(2, GroupLab.byCategory(data).get("food").size());
    }
}
