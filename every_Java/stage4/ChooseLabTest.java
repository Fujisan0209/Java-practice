import java.util.List;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ChooseLabTest {
    
    @Test
    void countInOrder_keepsFirstAppearanceOrder() {
        assertEquals("{food=2, daily=1}", String.valueOf(ChooseLab.countInOrder(List.of("food", "daily", "food"))));
    }
}
