import java.util.ArrayList;
import java.util.List;

public class Stage3Review {

    // ===== ここから下に4つのメソッドを書く =====
    static <T> T firstOrDefault(List<T> list, T defaultValue) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(list.isEmpty()) return defaultValue;
        return list.get(0);
    }

    static <T extends Comparable<T>> T max(List<T> list) {
        if(list == null || list.isEmpty()) throw new IllegalArgumentException("list must not be empty");
        T max = list.get(0);
        for(T t : list) {
            if(max.compareTo(t) < 0) {
                max = t;
            }
        }
        return max;
    }

    static double sum(List<? extends Number> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        double sum = 0.0;
        for(Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }

    static <T> void copyAll(List<? super T> dst, List<? extends T> src) {
        if(dst == null) throw new IllegalArgumentException("dst must not be null");
        if(src == null) throw new IllegalArgumentException("src must not be null");
        for(T t : src) {
            dst.add(t);
        }
    }

    // ===== ここまで =====

    // ---- 照合用の道具（触らなくてよい）----
    static void check(String label, Object expected, Object actual) {
        String mark = String.valueOf(expected).equals(String.valueOf(actual)) ? "OK" : "NG";
        System.out.println(mark + " " + label + "  期待=" + expected + "  実際=" + actual);
    }

    static void checkThrows(String label, String expectedMessage, Runnable action) {
        try {
            action.run();
            System.out.println("NG " + label + "  例外が出なかった");
        } catch (IllegalArgumentException e) {
            String mark = expectedMessage.equals(e.getMessage()) ? "OK" : "NG";
            System.out.println(mark + " " + label + "  期待=" + expectedMessage + "  実際=" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 区画A ---");
        check("A-1", "a",      firstOrDefault(List.of("a", "b"), "none"));
        check("A-2", "none",   firstOrDefault(new ArrayList<String>(), "none"));
        check("A-3", 9,        max(List.of(3, 9, 4)));
        check("A-4", "cherry", max(List.of("banana", "apple", "cherry")));
        check("A-5", 6.0,      sum(List.of(1, 2, 3)));
        check("A-6", 4.0,      sum(List.of(1.5, 2.5)));

        List<Number> nums = new ArrayList<>();
        copyAll(nums, List.of(1, 2));
        check("A-7", "[1, 2]", nums);

        List<Object> objs = new ArrayList<>(List.of("x"));
        copyAll(objs, List.of(7));
        check("A-8", "[x, 7]", objs);

        System.out.println("--- 区画B（実行して、なぜそうなるかを1行コメントで）---");
        System.out.println("B-1 " + max(List.of("apple", "Zebra")));
        // B-1 の理由：String は文字コードで比べる。大文字 Z(90) より小文字 a(97) のほうが大きい
        System.out.println("B-2 " + sum(List.of(1, 2.5)));
        // B-2 の理由：もともとNumberですべてdoubleとしてとるから足せる

        System.out.println("--- 区画C（5行とも OK になるはず）---");
        checkThrows("C-1", "list must not be null",  () -> firstOrDefault(null, "none"));
        checkThrows("C-2", "list must not be empty", () -> max(new ArrayList<Integer>()));
        checkThrows("C-3", "list must not be null",  () -> sum(null));
        checkThrows("C-4", "dst must not be null",   () -> copyAll(null, List.of(1)));
        checkThrows("C-5", "src must not be null",   () -> copyAll(new ArrayList<Object>(), null));
    }
}
