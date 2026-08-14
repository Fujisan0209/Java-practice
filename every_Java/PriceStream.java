import java.util.ArrayList;
import java.util.List;

public class PriceStream {
    public static void main(String[] args) {
        List<Integer> prices = new ArrayList<>();
        prices.add(1200);
        prices.add(3500);
        prices.add(800);
        prices.add(2600);
        prices.add(4100);
        prices.add(1500);

        // 1
        prices.stream()
            .filter(p -> p >= 2000)
            .forEach(p -> System.out.println(p));

        // 2
        int s = prices.stream()
            .mapToInt(p -> p)
            .sum();
        System.out.println("sum: " + s);

        // 3
        int max = prices.stream()
            .mapToInt(p -> p)
            .max()
            .orElse(0);
        System.out.println("max: " + max);
    }
    
}