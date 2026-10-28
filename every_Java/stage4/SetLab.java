import java.util.*;

public class SetLab {
    static Set<String> uniqueInOrder(List<String> words) {
        if(words == null) throw new IllegalArgumentException("words must not be null");
        if(words.isEmpty()) return new HashSet<>();
        Set<String> result = new LinkedHashSet<>();
        for(String w : words) {
            result.add(w);
        }
        return result;
    }

    static Set<String> uniqueSorted(List<String> words) {
        if(words == null) throw new IllegalArgumentException("words must not be null");
        if(words.isEmpty()) return new HashSet<>();
        Set<String> result = new TreeSet<>();
        for(String w : words) {
            result.add(w);
        }
        return result;
    }

    static List<String> duplicates(List<String> words) {
        if(words == null) throw new IllegalArgumentException("words must not be null");
        if(words.isEmpty()) return new ArrayList<>();
        Set<String> s1 = new LinkedHashSet<>();
        Set<String> s2 = new LinkedHashSet<>();
        List<String> result = new ArrayList<>();
        for(String w : words) {
            if(s1.add(w) == false) {
                s2.add(w);
            }
        }
        for(String w : s2) {
            result.add(w);
        }
        return result;
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        String actualText = String.valueOf(actual);
        if (Objects.equals(expected, actualText)) { ok++; System.out.println("OK " + label + " : " + actual); }
        else { ng++; System.out.println("NG " + label + " : 期待=" + expected + " 実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + " : IAE " + e.getMessage()); }
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        List<String> words = List.of("banana", "apple", "cherry", "apple", "banana", "apple");
        check("A-1 uniqueInOrder", "[banana, apple, cherry]", uniqueInOrder(words));
        check("A-2 uniqueSorted", "[apple, banana, cherry]", uniqueSorted(words));
        check("A-3 duplicates", "[apple, banana]", duplicates(words));
        check("A-4 uniqueInOrder 引数が空", "[]", uniqueInOrder(List.of()));
        check("A-5 duplicates 重複なし", "[]", duplicates(List.of("a", "b", "c")));
        checkThrows("A-6 duplicates 引数がnull", () -> duplicates(null));
        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("--- B ---");
        System.out.println(uniqueSorted(List.of("b", "B", "a")));
        // 使っているSetが文字コードで判断するので大文字の方が先に来る
        System.out.println(new HashSet<>(words));
        // 使う機器によって並び順が変わるから
        Set<String> h = new HashSet<>(List.of("banana", "apple", "cherry"));
        System.out.println(h);
        for (int i = 0; i < 13; i++) h.add("x" + i);     // 一度たくさん入れて
        for (int i = 0; i < 13; i++) h.remove("x" + i);  // 全部消す
        System.out.println(h);                            // 中身は同じ3つのはず
    }
}