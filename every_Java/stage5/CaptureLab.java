import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

public class CaptureLab {
    static Predicate<Integer> atLeast(int min) {
        return n -> n >= min;
    }
    
    static List<Integer> filterAll(List<Integer> list, Predicate<Integer> rule) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(rule == null) throw new IllegalArgumentException("rule must not be null");

        List<Integer> result = new ArrayList<>();
        for(int n : list) {
            if(rule.test(n)) {
                result.add(n);
            }
        }
        return result;
    }

    static int countMatches(List<Integer> list, Predicate<Integer> rule) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(rule == null) throw new IllegalArgumentException("rule must not be null");

        List<Integer> result = filterAll(list, rule);
        return result.size();
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(String.valueOf(expected), String.valueOf(actual))) {
            ok++; System.out.println("OK " + label + " : " + actual);
        } else {
            ng++; System.out.println("NG " + label + " : 期待=" + expected + " 実際=" + actual);
        }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + " : IAE"); }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A：自動照合 ===");
        List<Integer> scores = List.of(5, 1, 8, 3, 10, 3);

        Predicate<Integer> three = atLeast(3);
        check("A-1 three.test(3)", true, three.test(3));
        check("A-2 three.test(2)", false, three.test(2));
        check("A-3 filterAll 3以上", "[5, 8, 3, 10, 3]", filterAll(scores, three));
        check("A-4 filterAll 8以上", "[8, 10]", filterAll(scores, atLeast(8)));
        check("A-5 filterAll 偶数", "[8, 10]", filterAll(scores, n -> n % 2 == 0));
        check("A-6 filterAll 該当なし", "[]", filterAll(scores, atLeast(100)));
        check("A-7 元のリストは変わらない", "[5, 1, 8, 3, 10, 3]", scores);
        check("A-8 countMatches 3以上", 5, countMatches(scores, three));
        check("A-9 countMatches ちょうど3", 2, countMatches(scores, n -> n == 3));
        check("A-10 countMatches 空リスト", 0, countMatches(List.of(), three));
        checkThrows("A-11 filterAll(null, rule)", () -> filterAll(null, three));
        checkThrows("A-12 filterAll(list, null)", () -> filterAll(scores, null));
        checkThrows("A-13 countMatches(null, rule)", () -> countMatches(null, three));
        System.out.println("OK=" + ok + " / NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B：実行→理由1行 ===");
        // B-1：ラムダが「外の変数」を覚えている
        List<Integer> box = new ArrayList<>(List.of(1, 2));
        Predicate<Integer> inBox = n -> box.contains(n);
        System.out.println("B-1 前 : " + inBox.test(3));
        box.add(3);
        System.out.println("B-1 後 : " + inBox.test(3));
        // 理由（1行）：ラムダは box という「同じ箱」を指したまま覚えているので、あとから箱の中身が増えたのが見える

        // B-2：作ったラムダを2回使う
        Predicate<Integer> five = atLeast(5);
        System.out.println("B-2 : " + filterAll(scores, five) + " / " + filterAll(List.of(4, 6), five));
        // 理由（1行）：ラムダは作られた瞬間に min の値（5）を自分の中にコピーして持っているので、atLeast が終わっても消えない

        // 区画D：下の2行のコメントを外して javac → エラー文を読んだら、また戻す
        // int count = 0;
        // scores.forEach(n -> { if (n >= 3) count++; });
    }
}
