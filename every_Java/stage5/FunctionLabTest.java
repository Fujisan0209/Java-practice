import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FunctionLabTest {
    @Test
    void totalWith_tax10_returns1978() {
        List<FunctionLab.Expense> list = List.of(
            new FunctionLab.Expense("lunch", "food", 500),
            new FunctionLab.Expense("book", "hobby", 1200),
            new FunctionLab.Expense("snack", "food", 99));
        assertEquals(1978, FunctionLab.totalWith(list, FunctionLab.withTax(10)));
    }
}
