import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class MiniHashSetTest {
    @Test
    void collisionKeepBoth() {
        MiniHashSet a = new MiniHashSet(8);
        a.add("Aa"); a.add("BB");
        assertEquals(a.bucketIndex("Aa"), a.bucketIndex("BB"));
        assertEquals(2, a.size());
    }

    @Test
    void zeroBucketsThrows() {
        assertThrows(IllegalArgumentException.class, () -> new MiniHashSet(0));
    }
}
