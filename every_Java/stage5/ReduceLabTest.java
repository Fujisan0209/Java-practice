import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ReduceLabTest {
    @Test
    void mostExpensive_sameAmount_returnsFirst() {
        List<ReduceLab.Expense> list = List.of(
            new ReduceLab.Expense("lunch", "food", 800),
            new ReduceLab.Expense("book", "hobby", 1500),
            new ReduceLab.Expense("coffee", "food", 300),
            new ReduceLab.Expense("game", "hobby", 1500));

        assertEquals("book", ReduceLab.mostExpensive(list).map(ReduceLab.Expense::name).orElse("なし"));
    }
}
