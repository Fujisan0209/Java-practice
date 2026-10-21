import static org.junit.jupiter.api.Assertions.assertEquals;   // ← assertEquals を使えるようにする
import org.junit.jupiter.api.Test;                             // ← @Test を使えるようにする
import java.util.*;

class ListLabTest {
    @Test
    void fillFrontAddsThree() {
        List<String> small = new ArrayList<>();   // ← A-1 の準備
        ListLab.fillFront(small, "x", 3);                 // ← A-1 の動かす
        assertEquals(3, small.size());   // ← A-1 の確かめる
    }
}