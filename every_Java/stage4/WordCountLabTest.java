import java.util.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class WordCountLabTest {
    @Test
    void topWords_sameCount_sortedByWord() {
        assertEquals("[apple=1, banana=1]", String.valueOf(WordCountLab.topWords(WordCountLab.countWords("banana apple"), 2)));
    }    
}
