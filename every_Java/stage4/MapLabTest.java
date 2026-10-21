import static org.junit.jupiter.api.Assertions.assertEquals;   // ← assertEquals を使えるようにする
import org.junit.jupiter.api.Test;                             // ← @Test を使えるようにする
import java.util.*;

class MapLabTest {
    List<String> words = new ArrayList<>(List.of("pear", "apple", "kiwi"));
    @Test
    void firstKey_treeMap_returnsAlphabeticallyFirst() {
        Map<String, Integer> map = new TreeMap<>();
        MapLab.countInto(words, map);
        assertEquals("apple", MapLab.firstKey(map));
    }
}