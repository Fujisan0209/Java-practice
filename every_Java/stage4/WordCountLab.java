import java.util.*;

public class WordCountLab {
    static Map<String, Integer> countWords(String txt) {
        if(txt == null) throw new IllegalArgumentException("txt must not be null");
        Map<String, Integer> map = new TreeMap<>();
        String s = txt.toLowerCase();
        String[] sa = s.strip().split("\\s+");
        for(String a : sa) {
            if(a.equals("")) continue;
            if(map.containsKey(a)) {
                map.put(a, map.get(a) + 1);
            } else {
                map.put(a, 1);
            }
        }
        return map;
    }

    static List<String> topWords(Map<String, Integer> counts, int n) {
        if(counts == null) throw new IllegalArgumentException("counts must not be null");
        if(n < 0) throw new IllegalArgumentException("n must be positive");

        List<String> result = new ArrayList<>();
        List<Map.Entry<String, Integer>> me = new ArrayList<>(counts.entrySet());
        Comparator<Map.Entry<String, Integer>> byCount = Comparator.comparingInt(e -> e.getValue());
        me.sort(byCount.reversed().thenComparing(e -> e.getKey()));
        for(int i = 0; i < n; i++) {
            if(i == me.size()) break;
            Map.Entry<String, Integer> e = me.get(i);
            result.add(e.getKey() + "=" + e.getValue());
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        Map<String, Integer> counts = countWords("the cat and the dog and the bird");
        System.out.println("A-1 期待: {and=2, bird=1, cat=1, dog=1, the=3}/ 実際: " + counts);
        System.out.println("A-2 期待: [the=3, and=2]/ 実際: " + topWords(counts, 2));
        System.out.println("A-3 期待: [the=3, and=2, bird=1, cat=1]/ 実際: " + topWords(counts, 4));
        System.out.println("A-4 期待: [the=3, and=2, bird=1, cat=1, dog=1]/ 実際: " + topWords(counts, 10));
        System.out.println("A-5 期待: []/ 実際: " + topWords(counts, 0));
        System.out.println("A-6 期待: {and=2, bird=1, cat=1, dog=1, the=3}/ 実際: " + counts);
        System.out.println("A-7 期待: {apple=3, banana=1}/ 実際: " + countWords("  Apple apple   APPLE banana "));
        System.out.println("A-8 期待: {}/ 実際: " + countWords("   "));
        System.out.println("A-9 期待: {}/ 実際: " + countWords(""));
        try {
            countWords(null);
        } catch(IllegalArgumentException e) {
            System.out.println("A-10 期待: txt must not be null/ 実際: " + e.getMessage());
        }
        try {
            topWords(null, 1);
        } catch(IllegalArgumentException e) {
            System.out.println("A-10 期待: counts must not be null/ 実際: " + e.getMessage());
        }
        try {
            topWords(counts, -1);
        } catch(IllegalArgumentException e) {
            System.out.println("A-10 期待: n must be positive/ 実際: " + e.getMessage());
        }

        System.out.println("--- B ---");
        System.out.println("".split("\\s+").length);
        // 分からない
        // 実際 1 "".split("\\s+") は空の配列 [] ではなく、空の文字列 "" が1個だけ入った配列 [""] を返します。
    }
}
