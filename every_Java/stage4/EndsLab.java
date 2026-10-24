import java.util.*;

public class EndsLab {

    // ★ここに ends と lastN を書く
    static <T> String ends(SequencedCollection<T> c) {
        if(c == null || c.isEmpty()) throw new IllegalArgumentException("c must not be empty");
        return c.getFirst() + ".." + c.getLast();
    }

    static <T> List<T> lastN(List<T> list, int n) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(n < 0) throw new IllegalArgumentException("n must not be negative");
        if(n > list.size()) throw new IllegalArgumentException("n must be smaller than list_size");
        List<T> cp = new ArrayList<>(list.reversed());
        List<T> result = new ArrayList<>();
        for(int i = 0; i < n; i++) {
            result.add(cp.get(i));
        }
        return result;
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label + " : " + actual); }
        else { ng++; System.out.println("NG " + label + " : expected=" + expected + " actual=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + " : IAE " + e.getMessage()); }
        catch (RuntimeException e) { ng++; System.out.println("NG " + label + " : 別の例外 " + e); }
    }

    public static void main(String[] args) {
        System.out.println("=== A 動作確認 ===");
        List<String> words = new ArrayList<>(List.of("red", "green", "blue"));
        check("A-1 List の両端", "red..blue", ends(words));
        Deque<Integer> dq = new ArrayDeque<>(List.of(5, 6, 7, 8));
        check("A-2 Deque の両端", "5..8", ends(dq));
        LinkedHashSet<String> set = new LinkedHashSet<>(List.of("x", "y"));
        check("A-3 LinkedHashSet の両端", "x..y", ends(set));
        check("A-4 1件だけ", "solo..solo", ends(List.of("solo")));
        List<Integer> nums = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        check("A-5 lastN 3件", List.of(5, 4, 3), lastN(nums, 3));
        check("A-6 lastN 0件", List.of(), lastN(nums, 0));
        List<Integer> got = lastN(nums, 2);
        nums.add(6);
        check("A-7 元を変えても結果は変わらない", List.of(5, 4), got);
        System.out.println("追加 " + ends(List.of("apple")));

        System.out.println("=== B 実行→理由1行 ===");
        List<String> base = new ArrayList<>(List.of("a", "b", "c"));
        base.reversed();
        System.out.println("B-1 " + base);
        // 元のを変更するわけではない、戻り値を受け取らないと消える
        List<String> rev = base.reversed();
        base.add("d");
        System.out.println("B-2 " + rev);
        // ビューなので元のリストを変えるとこっちも変わる

        System.out.println("=== C 例外 ===");
        checkThrows("C-1 ends に null", () -> ends(null));
        checkThrows("C-2 ends に空", () -> ends(new ArrayList<String>()));
        checkThrows("C-3 lastN に負", () -> lastN(nums, -1));
        checkThrows("C-4 lastN に多すぎ", () -> lastN(List.of(1, 2), 3));

        System.out.println("OK=" + ok + " NG=" + ng);
    }
}