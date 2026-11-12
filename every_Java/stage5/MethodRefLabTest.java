import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class MethodRefLabTest {
    @Test
    void withPrefix_hash_addsPrefixToEachName() {
        List<MethodRefLab.Expense> list = List.of(
            new MethodRefLab.Expense("lunch", "food", 800),
            new MethodRefLab.Expense("book", "hobby", 1500)
        );
        assertEquals(List.of("#lunch", "#book"), MethodRefLab.withPrefix(list, "#"));
    }
}
