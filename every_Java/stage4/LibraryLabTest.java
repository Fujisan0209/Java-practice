import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryLabTest {
    @Test
    void borrow_sameBookTwice_returnsFalse() {
        LibraryLab.Library lib = new LibraryLab.Library();
        lib.addBook("Java", 1);
        boolean amy = lib.borrow("amy", "Java");
        boolean amy1 = lib.borrow("amy", "Java");
        assertEquals(false, amy1);
        assertEquals(0, lib.available("Java"));
    }

}
