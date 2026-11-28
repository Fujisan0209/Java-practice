import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

class FileLabTest {
    @Test
    void countNonBlank_blankLines_areNotCounted() throws IOException {
        Path file = Files.createTempFile("filelab", ".txt");
        FileLab.saveLines(file, List.of("食費", "", "   ", "500"));
        assertEquals(2, FileLab.countNonBlank(file));
    }
}
