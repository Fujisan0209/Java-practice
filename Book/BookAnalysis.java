import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookAnalysis {
    public static void main(String[] args) {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Java Programming", "Tanaka", 2800));
        books.add(new Book("Python Basics", "Suzuki", 2200));
        books.add(new Book("Spring Boot Guide", "Tanaka", 3500));
        books.add(new Book("Data Structures", "Sato", 1800));
        books.add(new Book("Web Development", "Suzuki", 2600));
        books.add(new Book("Algorithm Design", "Tanaka", 3200));

        System.out.println("=== Top 3 by Price ===");
        books.stream()
            .sorted((a, b) -> b.getPrice() - a.getPrice())
            .limit(3)
            .forEach(b -> System.out.println(b.getTitle() + ": " + b.getPrice()));

        System.out.println("=== Authors ===");
        books.stream()
            .map(b -> b.getAuthor())
            .distinct()
            .forEach(name -> System.out.println(name));

        System.out.println("=== Expensive Count ===");
        long c = books.stream()
            .filter(b -> b.getPrice() >= 3000)
            .count();
        System.out.println(c);

        System.out.println("=== First Cheap Book ===");
        Optional<Book> first = books.stream()
            .filter(b -> b.getPrice() < 2500)
            .findFirst();
        first.ifPresent(b -> System.out.println(b.getTitle() + ": " + b.getPrice()));

        System.out.println("=== All over 1000? ===");
        boolean b = books.stream()
            .allMatch(a -> a.getPrice() >= 1000);
        System.out.println(b);
    }
}