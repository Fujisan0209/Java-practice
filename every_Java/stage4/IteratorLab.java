import java.util.*;

public class IteratorLab {
    public static int removeShort(List<String> words, int minLength) {
        if(words == null) throw new IllegalArgumentException("words must not be null");
        if(minLength < 0) throw new IllegalArgumentException("minLength must not be negative");

        Iterator<String> it = words.iterator();
        int count = 0;
        while(it.hasNext()) {
            String w = it.next();
            if(w.length() < minLength) {
                it.remove();
                count++;
            }
        }
        return count;
    }

    // ===== ここから下は用意済み（触らなくてよい） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + expected + "  実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + "  -> " + e.getMessage()); }
    }

    public static void main(String[] args) {
        System.out.println("== A 動作確認 ==");
        List<String> a = new ArrayList<>(List.of("a", "bb", "ccc", "d", "ee"));
        int removed = removeShort(a, 2);
        check("A-1 消した数", 2, removed);
        check("A-2 残った中身", List.of("bb", "ccc", "ee"), a);
        List<String> a2 = new ArrayList<>(List.of("x", "y"));
        check("A-3 全部消える", 2, removeShort(a2, 5));
        check("A-4 全部消えた後", List.of(), a2);
        List<String> a3 = new ArrayList<>(List.of("abc", "de"));
        check("A-5 0なら何も消えない", 0, removeShort(a3, 0));
        check("A-6 空リスト", 0, removeShort(new ArrayList<>(), 3));

        System.out.println("== B 実行して、出力の理由を1行で ==");
        List<String> b1 = new ArrayList<>(List.of("a", "bb", "ccc", "dd"));
        try {
            for (String w : b1) {
                if (w.length() < 2) b1.remove(w);
            }
            System.out.println("B-1 例外なし: " + b1);
        } catch (ConcurrentModificationException e) {
            System.out.println("B-1 例外: " + e.getClass().getSimpleName());
        }
        // さっきの例で出た例外と同じ。実行中に勝手に変えられた

        List<String> b2 = new ArrayList<>(List.of("aa", "b", "c"));
        try {
            for (String w : b2) {
                if (w.length() < 2) b2.remove(w);
            }
            System.out.println("B-2 例外なし: " + b2);
        } catch (ConcurrentModificationException e) {
            System.out.println("B-2 例外: " + e.getClass().getSimpleName());
        }
        // 分からない
        // next() が呼ばれる前にループが終わったので、検査が一度も走りません。
        // だから例外が出ず、c は一度も調べられないまま残ります。2文字未満なのに消えていません。

        System.out.println("== C 例外（2行出るはず） ==");
        checkThrows("C-1 リスト null", () -> removeShort(null, 2));
        checkThrows("C-2 長さが負", () -> removeShort(new ArrayList<>(), -1));

        System.out.println("OK=" + ok + " NG=" + ng);
    }
}
