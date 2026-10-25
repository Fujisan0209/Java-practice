import java.util.*;

public class MiniHashSet {

    private final List<List<String>> buckets;
    private int size = 0;

    public MiniHashSet(int bucketCount) {
        if(bucketCount < 1) throw new IllegalArgumentException("bucketCount must not be smaller than 1");
        buckets = new ArrayList<List<String>>();
        for(int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<String>());
        }
    }

    public int bucketIndex(String s) {
        if(s == null) throw new IllegalArgumentException("s must not be null");
        return Math.floorMod(s.hashCode(), buckets.size());
    }

    public boolean add(String s) {
        List<String> shelf = buckets.get(bucketIndex(s));
        for(String sh : shelf) {
            if(sh.equals(s)) { return false; }
        }
        shelf.add(s);
        size++;
        return true;
    }

    public boolean contains(String s) {
        List<String> shelf = buckets.get(bucketIndex(s));
        for(String sh : shelf) {
            if(sh.equals(s)) { return true; }
        }
        return false;
    }

    public int size() { return size;}

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label + " : " + actual); }
        else { ng++; System.out.println("NG " + label + " : 期待=" + expected + " 実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + " : IAE " + e.getMessage()); }
        catch (RuntimeException e) { ng++; System.out.println("NG " + label + " : 別の例外 " + e); }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A：動作確認 ===");
        MiniHashSet set = new MiniHashSet(8);
        check("A-1 1回目の add", true, set.add("apple"));
        check("A-2 同じ値の add", false, set.add("apple"));
        check("A-3 size は重複を数えない", 1, set.size());
        check("A-4 contains(入れた)", true, set.contains("apple"));
        check("A-5 contains(入れてない)", false, set.contains("kiwi"));
        check("A-6 new String でも見つかる", true, set.contains(new String("apple")));
        check("A-7 bucketIndex は 0〜7", true, set.bucketIndex("apple") >= 0 && set.bucketIndex("apple") < 8);

        System.out.println("=== 区画B：実行して理由を1行 ===");
        MiniHashSet b = new MiniHashSet(8);
        System.out.println("B-1 " + "Aa".hashCode() + " " + "BB".hashCode()
            + " / 棚 " + b.bucketIndex("Aa") + " " + b.bucketIndex("BB")
            + " / add " + b.add("Aa") + " " + b.add("BB") + " / size " + b.size());
        // hashCode で棚を決めて、equals で中身を区別する
        String neg = "banana";
        System.out.println("B-2 " + neg.hashCode() + " % 8 = " + (neg.hashCode() % 8)
            + " / floorMod = " + b.bucketIndex(neg));
        // 前者は普通の割り算、後者は答えが必ず0~n-1に納まる計算

        System.out.println("=== 区画C：例外 ===");
        checkThrows("C-1 棚の数 0", () -> new MiniHashSet(0));
        checkThrows("C-2 add(null)", () -> set.add(null));
        checkThrows("C-3 contains(null)", () -> set.contains(null));

        System.out.println("OK=" + ok + " NG=" + ng);
    }
}