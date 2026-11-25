import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class ResourceLabTest {
    @Test
    void runAndReport_bothFail_keepsWorkError() {
        assertEquals("work failed / suppressed=1", ResourceLab.runAndReport(new ArrayList<>(), true, true));
    }
}
