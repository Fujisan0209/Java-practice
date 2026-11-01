import java.util.*;

public class ResizeLab {
    static class ResizeHashSet {
        private List<List<String>> buckets = new ArrayList<>();
        private int size = 0;

        ResizeHashSet(int initialBuckets) {
            if(initialBuckets < 1) throw new IllegalArgumentException("initialBuckets must be positive");
            for(int i = 0; i < initialBuckets; i++) {
                buckets.add(new ArrayList<>());
            }
        }

        int bucketOf(String s) {
            return Math.floorMod(s.hashCode(), buckets.size());
        }

        boolean contains(String s) {
            if(s == null) throw new IllegalArgumentException("s must not be null");
            List<String> a = buckets.get(bucketOf(s));
            if(a == null) throw new IllegalArgumentException("null");
            for(String t : a) {
                if(t.equals(s)) {
                    return true;
                }
            }
            return false;
        }

        boolean add(String s) {
            if(s == null) throw new IllegalArgumentException("s must not be null");
             if(contains(s) == true) {
                return false;
            } else {
                List<String> a = buckets.get(bucketOf(s));
                if(a == null) throw new IllegalArgumentException("null");
                a.add(s);
                size++;
                if(size * 4 > buckets.size() * 3) { 
                    resize(); 
                }
                return true;
            }
        }

        private void resize() {
            List<List<String>> sub = new ArrayList<>();
            for(int i = 0; i < buckets.size() * 2; i++) {
                sub.add(new ArrayList<>());
            }
            for(List<String> sl : buckets) {
                for(String s : sl) {
                    List<String> tmp = sub.get(Math.floorMod(s.hashCode(), sub.size()));
                    tmp.add(s);
                }
            }
            buckets = sub;
        }

        int size() { return size; }

        int bucketCount() { return buckets.size(); }
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + expected + "  実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + "  (" + e.getMessage() + ")"); }
        catch (RuntimeException e) { ng++; System.out.println("NG " + label + "  違う例外: " + e); }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A ===");
        ResizeHashSet set = new ResizeHashSet(4);
        check("A-1 最初の棚の数", 4, set.bucketCount());
        check("A-2 最初の要素数", 0, set.size());
        check("A-3 add(apple) は true", true, set.add("apple"));
        check("A-4 2回目の add(apple) は false", false, set.add("apple"));
        check("A-5 重複は数えない", 1, set.size());
        set.add("banana");
        set.add("cherry");
        check("A-6 3個ではまだ棚は4", 4, set.bucketCount());
        set.add("melon");
        check("A-7 4個目で棚が8に倍増", 8, set.bucketCount());
        boolean all4 = set.contains("apple") && set.contains("banana")
                    && set.contains("cherry") && set.contains("melon");
        check("A-8 倍増後も4つとも見つかる", true, all4);
        check("A-9 入れていない grape は false", false, set.contains("grape"));
        set.add("lemon");
        set.add("peach");
        set.add("kiwi");
        check("A-10 7個目で棚が16に倍増", 16, set.bucketCount());
        check("A-11 要素数は7", 7, set.size());
        int b = set.bucketOf("banana");
        check("A-12 hashCode が負の banana も棚番号は 0〜15", true, 0 <= b && b < 16);
        checkThrows("A-13 add(null) は IAE", () -> set.add(null));
        checkThrows("A-14 contains(null) は IAE", () -> set.contains(null));
        checkThrows("A-15 棚0個は IAE", () -> new ResizeHashSet(0));
        System.out.println("区画A: OK=" + ok + " NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B（実行して理由を1行） ===");
        ResizeHashSet small = new ResizeHashSet(4);
        System.out.println("B-1 banana の棚番号（棚4のとき）: " + small.bucketOf("banana"));
        small.add("apple"); small.add("banana"); small.add("cherry"); small.add("melon");
        System.out.println("B-1 banana の棚番号（棚8のとき）: " + small.bucketOf("banana"));
        // 文字列のHashcodeをbucketsのsizeでfloorModメソッドを使って計算した結果
        // 棚の数が変わったから

        ResizeHashSet big = new ResizeHashSet(1);
        for (int i = 0; i < 1000; i++) big.add("item" + i);
        System.out.println("B-2 1000個入れたあとの棚の数: " + big.bucketCount());
        // 
        // 棚1024個だと、1000個入れたときの混み具合が 0.75 を超えてしまうので、もう1回2倍にして2048になる。
    }

}
