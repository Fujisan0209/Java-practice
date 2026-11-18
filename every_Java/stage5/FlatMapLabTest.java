import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class FlatMapLabTest {
    @Test
    void allItems_withEmptyReceipt_countOnlyItems() {
        List<FlatMapLab.Receipt> rs = List.of(
            new FlatMapLab.Receipt("conbini", List.of(
                new FlatMapLab.Expense("onigiri", "food", 150),
                new FlatMapLab.Expense("tea", "food", 120))),
            new FlatMapLab.Receipt("drugstore", List.of(
                new FlatMapLab.Expense("shampoo", "daily", 600),
                new FlatMapLab.Expense("candy", "food", 100))),
            new FlatMapLab.Receipt("bookstore", List.of(
                new FlatMapLab.Expense("manga", "hobby", 500))),
            new FlatMapLab.Receipt("cafe", List.of()));

        assertEquals(5, FlatMapLab.allItems(rs).size());
    }
}
