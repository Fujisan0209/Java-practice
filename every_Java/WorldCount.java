import java.util.LinkedHashMap;
import java.util.Map;

public class WorldCount {
    public static void main(String[] args) {
        Map<String, Integer> count = new LinkedHashMap<>();
        String text = "apple,banana,apple,cherry,banana,apple";
        String texts[] = text.split(",");

        for(String t : texts) {
            if(count.containsKey(t)) {
                count.put(t, count.get(t) + 1);
            } else {
                count.put(t, 1);
            }
        }

        for(String t : count.keySet()) {
            System.out.println(t + ": " + count.get(t));
        }
    }
} 