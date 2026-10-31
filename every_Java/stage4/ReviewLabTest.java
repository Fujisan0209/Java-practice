import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ReviewLabTest {
    
    @Test
    void maxOf_budgets_returnsLargestLimit() {
        List<ReviewLab.Budget> budgets = List.of(
                new ReviewLab.Budget("food", 30000),
                new ReviewLab.Budget("train", 10000),
                new ReviewLab.Budget("fun", 30000));

        assertEquals(new ReviewLab.Budget("fun", 30000), ReviewLab.maxOf(budgets));
    }
}
