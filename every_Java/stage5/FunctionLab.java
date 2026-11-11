import java.util.*;
import java.util.function.*;

public class FunctionLab {
    record Expense(String name, String category, int amount) { }

    static Function<Integer, Integer> withTax(int percent) {
        if(percent < 0) throw new IllegalArgumentException("percent must not be negative");
        return n -> n * (100 + percent) / 100;
    }

    static <T, R> List<R> mapAll(List<T> list, Function<T, R> f) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(f == null) throw new IllegalArgumentException("f must not be null");

        List<R> result = new ArrayList<>();
        for(T t : list) {
            result.add(f.apply(t));
        }
        return result;
    }

    static List<String> labels(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        List<String> result = new ArrayList<>();
        for(Expense e : list) {
            result.add(e.name() + ":" + e.amount() + "円");
        }
        return result;
    }

    static int totalWith(List<Expense> list, Function<Integer, Integer> f) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(f == null) throw new IllegalArgumentException("f must not be null");

        int total = 0;
        for(Expense e : list) {
            total += f.apply(e.amount());
        }
        return total;
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        String e = String.valueOf(expected), a = String.valueOf(actual);
        if (e.equals(a)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + e + "  実際=" + a); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (IllegalArgumentException ex) { ok++; System.out.println("OK " + label); }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 500),
            new Expense("book", "hobby", 1200),
            new Expense("snack", "food", 99));

        System.out.println("=== 区画A ===");
        Function<Integer, Integer> tax10 = withTax(10);
        check("A-1 withTax(10).apply(1000)", 1100, tax10.apply(1000));
        check("A-2 withTax(0).apply(500)", 500, withTax(0).apply(500));
        check("A-3 withTax(8).apply(99) 切り捨て", 106, withTax(8).apply(99));
        checkThrows("A-4 withTax(-1) は IAE", () -> withTax(-1));

        check("A-5 mapAll 文字数", "[5, 4, 5]", mapAll(List.of("lunch", "book", "snack"), s -> s.length()));
        check("A-6 mapAll 2倍", "[2, 4, 6]", mapAll(List.of(1, 2, 3), n -> n * 2));
        check("A-7 mapAll 空リスト", "[]", mapAll(new ArrayList<Integer>(), n -> n * 2));
        List<Integer> src = new ArrayList<>(List.of(1, 2, 3));
        mapAll(src, n -> n * 10);
        check("A-8 mapAll は元のリストを変えない", "[1, 2, 3]", src);
        checkThrows("A-9 mapAll(null, f) は IAE", () -> mapAll(null, n -> n));
        checkThrows("A-10 mapAll(list, null) は IAE", () -> mapAll(List.of(1), null));

        check("A-11 labels", "[lunch:500円, book:1200円, snack:99円]", labels(list));
        check("A-12 labels 空", "[]", labels(List.of()));

        check("A-13 totalWith そのまま", 1799, totalWith(list, n -> n));
        check("A-14 totalWith 税10%", 1978, totalWith(list, withTax(10)));
        checkThrows("A-15 totalWith(list, null) は IAE", () -> totalWith(list, null));

        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("=== 区画B（実行→理由を1行） ===");
        int price = 1000;
        withTax(10).apply(price);
        System.out.println("B-1 price = " + price);
        // B-1：apply は計算した 1100 を「返す」だけ。戻り値を受け取っていないので捨てられ、price 自体は 1000 のまま
        System.out.println("B-2 withTax(8).apply(99) = " + withTax(8).apply(99)
            + "   99 * 1.08 = " + (99 * 1.08));
        // B-2：中の計算が int 同士（99 * 108 / 100 = 10692 / 100）なので、割り算で小数が切り捨てられて 106 になる
    }
}