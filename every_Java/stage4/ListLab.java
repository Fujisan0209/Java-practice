
import java.util.*;

public class ListLab {
    static <T> long fillFront(List<T> list, T value, int count) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(count < 0) throw new IllegalArgumentException("count must be positive");
        long start = System.nanoTime();
        for(int i = 0; i < count; i++) {
            list.add(0, value);
        }
        long last = System.nanoTime();
        return last - start;
    }

    static long readMiddle(List<?> list, int times) {
        if(list == null || list.isEmpty()) throw new IllegalArgumentException("list must not be null");
        if(times < 0) throw new IllegalArgumentException("count must be positive");
        long start = System.nanoTime();
        for(int i = 0; i < times; i++) {
            list.get(list.size() / 2);
        }
        long last = System.nanoTime();
        return last - start;
    }

    // ===== ここから下は main（変更しない）=====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (expected.equals(actual)) { ok++; System.out.println("OK " + label + " : " + actual); }
        else { ng++; System.out.println("NG " + label + " : 期待 " + expected + " / 実際 " + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + " : IAE " + e.getMessage()); }
    }

    public static void main(String[] args) {
        final int N = 100_000;

        System.out.println("=== A 動作確認 ===");
        List<String> small = new ArrayList<>();
        fillFront(small, "x", 3);
        check("A-1 3個入った", 3, small.size());
        check("A-2 0個なら何も入らない", 0, sizeAfterFill(new ArrayList<>(), 0));

        List<Integer> array = new ArrayList<>();
        List<Integer> linked = new LinkedList<>();
        long arrayFront = fillFront(array, 0, N);
        long linkedFront = fillFront(linked, 0, N);
        System.out.println("   先頭追加  Array=" + arrayFront / 1_000_000 + "ms  Linked=" + linkedFront / 1_000_000 + "ms");
        check("A-3 先頭追加は LinkedList が速い", true, linkedFront < arrayFront);

        long arrayMid = readMiddle(array, 10_000);
        long linkedMid = readMiddle(linked, 10_000);
        System.out.println("   真ん中get Array=" + arrayMid / 1_000_000 + "ms  Linked=" + linkedMid / 1_000_000 + "ms");
        check("A-4 真ん中 get は ArrayList が速い", true, arrayMid < linkedMid);

        System.out.println("=== B 実行→理由を1行 ===");
        List<Integer> b1 = new ArrayList<>(List.of(1, 2, 3));
        System.out.println("B-1 " + b1.remove(1) + " " + b1);
        // int なので remove(int index) が呼ばれ、位置1の「2」を消して、消した値 2 を返す
        List<Integer> b2 = new ArrayList<>(List.of(1, 2, 3));
        System.out.println("B-2 " + b2.remove(Integer.valueOf(1)) + " " + b2);
        // Integer なので remove(Object o) が呼ばれ、値が1の要素を探して消し、消せたので true を返す

        System.out.println("=== C 例外 ===");
        checkThrows("C-1 fillFront に null", () -> fillFront(null, "x", 1));
        checkThrows("C-2 fillFront の count が負", () -> fillFront(new ArrayList<String>(), "x", -1));
        checkThrows("C-3 readMiddle に空リスト", () -> readMiddle(new ArrayList<String>(), 1));

        System.out.println("OK=" + ok + " NG=" + ng);
    }
    static int sizeAfterFill(List<String> list, int count) {
        fillFront(list, "x", count);
        return list.size();
    }
}
 