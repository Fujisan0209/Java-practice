import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class SortLabTest {
    
    @Test
    void byAmountDesc_sameAmount_sortedByName() {
        List<Expense> list = List.of(new Expense("zoo", "fun", 1000, 1), 
                                     new Expense("art", "fun", 1000, 2));

        assertEquals("art", SortLab.byAmountDesc(list).get(0).name());                         
    }

}
