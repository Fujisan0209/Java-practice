import java.util.ArrayList;
import java.util.List;

public class ListPractice {
    public static void main(String[] args) {
        List<Integer> scores = new ArrayList<>();
        scores.add(85);
        scores.add(42); 
        scores.add(91);
        scores.add(68);
        scores.add(73);
        scores.add(55);

        System.out.println("=== 70+ ===");
        scores.stream()
            .filter(s -> s >= 70)
            .forEach(s -> System.out.println(s));
        
        System.out.println("=== Average ===");
        double ave = scores.stream()
            .mapToInt(s -> s)
            .average()
            .orElse(0.0);
        System.out.println(ave);
        
        System.out.println("=== Max ===");
        int max = scores.stream()
            .mapToInt(s -> s)
            .max()
            .orElse(0);
        System.out.println(max);
    }
}