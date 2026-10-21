
import java.util.*;

public class MapLab {
    static void countInto(List<String> words, Map<String, Integer> map) {
        if(words == null) throw new IllegalArgumentException("words must not be null");
        if(map == null) throw new IllegalArgumentException("map must not be null");
        if(!map.isEmpty()) throw new IllegalArgumentException("map must be empty");

        for(String w : words) {
            if(map.containsKey(w)) {
                map.put(w, map.get(w) + 1);
            } else {
                map.put(w, 1);
            }
        }
    }

    static String firstKey(Map<String, Integer> map) {
        if(map == null || map.isEmpty()) throw new IllegalArgumentException("map must not be empty");
        String firstkey = "";
        for(String key : map.keySet()) {
            firstkey = key;
            break;
        }
        return firstkey;
    }

    // ===== ここから下は main（こちらで用意・変更しない） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label + " : " + actual); }
        else { ng++; System.out.println("NG " + label + " : 期待=" + expected + " 実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + " : IAE " + e.getMessage()); }
    }

    public static void main(String[] args) {
        List<String> words = List.of("pear", "apple", "kiwi", "apple", "pear", "apple");

        System.out.println("=== A 動作確認（自動照合） ===");
        Map<String, Integer> linked = new LinkedHashMap<>();
        countInto(words, linked);
        check("A-1 LinkedHashMap", "{pear=2, apple=3, kiwi=1}", linked.toString());
        check("A-2 LinkedHashMap の先頭", "pear", firstKey(linked));
        Map<String, Integer> tree = new TreeMap<>();
        countInto(words, tree);
        check("A-3 TreeMap", "{apple=3, kiwi=1, pear=2}", tree.toString());
        check("A-4 TreeMap の先頭", "apple", firstKey(tree));
        check("A-5 apple の個数は Linked と Tree で同じ", linked.get("apple"), tree.get("apple"));

        System.out.println("=== B 実行して、理由を1行 ===");
        Map<String, Integer> hash = new HashMap<>();
        countInto(words, hash);
        System.out.println("B-1 HashMap : " + hash);
        // 数はあっていて、順番はPCなどによってバラバラ
        Integer before = tree.put("kiwi", 10);
        Integer fresh  = tree.put("lemon", 1);
        System.out.println("B-2 put の戻り値 : " + before + " / " + fresh);
        // 上書きすると戻り値として以前の値を返す、新しく追加ならnull

        System.out.println("=== C 例外（3行出るはず） ===");
        checkThrows("C-1 words が null", () -> countInto(null, new HashMap<>()));
        checkThrows("C-2 map が空でない", () -> countInto(words, linked));
        checkThrows("C-3 空の map の先頭", () -> firstKey(new TreeMap<>()));
        System.out.println("合計 OK=" + ok + " NG=" + ng);
    }
}