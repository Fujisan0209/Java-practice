import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookAnalyzer {
    public static void main(String[] args) {
        List<Book> books = List.of(
        new Book("Java Basics", "TECH", 2800, 5),
        new Book("Statistics", "TECH", 3200, 0),
        new Book("Behavioral Econ", "ARTS", 1800, 12),
        new Book("Algorithms", "TECH", 2500, 3),
        new Book("Game Theory", "ARTS", 2200, 0),
        new Book("Networking", "TECH", 3000, 8)
    );

    System.out.println(inStockTitles(books));
    System.out.println(totalInventoryValue(books));
    System.out.println(stockByCategory(books));

    }

    static List<String> inStockTitles(List<Book> books) {
        return books.stream()
            .filter(b -> b.getStock() >= 1)
            .sorted((a, b) -> a.getPrice() - b.getPrice())
            .map(b -> b.getTitle())
            .collect(Collectors.toList());
    }

    static int totalInventoryValue(List<Book> books) {
        return books.stream()
            .mapToInt(b -> b.getPrice() * b.getStock())
            .sum();
    }

    static Map<String, Integer> stockByCategory(List<Book> books) {
        return books.stream()
            .collect(Collectors.groupingBy(
                b -> b.getCategory(),
                Collectors.summingInt(b -> b.getStock())
            ));
    }
}