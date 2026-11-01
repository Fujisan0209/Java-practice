import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ResizeLabTest {
    
    @Test
    void add_fourItems_bucketCountDoubles() {
        ResizeLab.ResizeHashSet set = new ResizeLab.ResizeHashSet(4);
        set.add("banana");
        set.add("cherry");
        set.add("melon");
        set.add("lemon");

        assertEquals(8, set.bucketCount());
    }
}

