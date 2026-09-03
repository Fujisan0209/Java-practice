import java.util.Comparator;
import java.util.List;

public class WordStats {
    private final List<String> words;

    public WordStats(List<String> words) {
        this.words = words;
    }

    public List<String> longerThan(int n) {
        List<String> result = words.stream()
            .filter(s -> s.length() > n)
            .toList();
        return result;
    }

    public List<String> toUpperCaseAll() {
        List<String> result = words.stream()
            .map(s -> s.toUpperCase())
            .toList();
        return result;
    }

    public long countStartingWith(String prefix) {
        long count = words.stream()
            .filter(s -> s.startsWith(prefix))
            .count();
        return count;
    }

    public double averageLength() {
        double ave = words.stream()
            .mapToInt(s -> s.length())
            .average()
            .orElse(0.0);
        return ave;
    }

    public String longest() {
        return words.stream()
            .max(Comparator.comparingInt(s -> s.length()))
            .orElse("");
    }

    public static void main(String[] args) {
        List<String> w1 = List.of("apple", "banana" , "kiwi", "avocado", "fig");
        List<String> w2 = List.of();

        WordStats s1 = new WordStats(w1);
        WordStats s2 = new WordStats(w2);

        System.out.println(s1.longerThan(4));
        System.out.println(s1.toUpperCaseAll());
        System.out.println(s1.countStartingWith("a"));
        System.out.println(s1.averageLength());

        System.out.println(w1);

        System.out.println(s2.averageLength());

        System.out.println(s1.longest());      // 期待 avocado
        System.out.println(s2.longest());      // 期待 （空文字列 → 何も出ない行）
        System.out.println(new WordStats(List.of("ab", "cd")).longest());   // 期待 ab 
    }
}