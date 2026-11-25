import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidateLabTest {
    @Test
    void newExpense_zeroAmount_throwsIAE() {
        assertThrows(IllegalArgumentException.class, () -> new ValidateLab.Expense("A", "a", 0));
    }
}
