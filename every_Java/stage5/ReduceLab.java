import java.util.*;

public class ReduceLab {
    record Expense(String name, String category, int amount) {}

    static int total(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        return list.stream()
            .mapToInt(Expense::amount)
            .reduce(0, (a, b) -> a + b);
    }

    static Optional<Expense> mostExpensive(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        // if(list.isEmpty()) return Optional.empty();

        return list.stream()
            .reduce((a,b) -> a.amount() >= b.amount() ? a : b);
    }

    static String joinNames(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        return list.stream()
            .map(Expense::name)
            .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + " 期待=" + expected + " 実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label); }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("book", "hobby", 1500),
            new Expense("coffee", "food", 300),
            new Expense("game", "hobby", 1500));
        List<Expense> empty = List.of();

        // ===== 区画A =====
        check("A-1 total", 4100, total(list));
        check("A-2 total 空は0", 0, total(empty));
        check("A-3 total 1件", 300, total(List.of(new Expense("coffee", "food", 300))));
        check("A-4 mostExpensive 名前", "book", mostExpensive(list).map(Expense::name).orElse("なし"));
        check("A-5 mostExpensive 同額は先の方", "book", mostExpensive(list).get().name());
        check("A-6 mostExpensive 空は empty", Optional.empty(), mostExpensive(empty));
        check("A-7 joinNames", "lunch, book, coffee, game", joinNames(list));
        check("A-8 joinNames 1件は区切りなし", "lunch", joinNames(List.of(list.get(0))));
        check("A-9 joinNames 空は空文字", "", joinNames(empty));
        checkThrows("A-10 total(null)", () -> total(null));
        checkThrows("A-11 mostExpensive(null)", () -> mostExpensive(null));
        checkThrows("A-12 joinNames(null)", () -> joinNames(null));
        System.out.println("OK=" + ok + " NG=" + ng);

        // ===== 区画B（実行して、理由を1行で）=====
        int b1 = list.stream().map(Expense::amount).reduce(1, Integer::sum);
        System.out.println("B-1: " + b1);
        // 最初の値が1担っているので合計が0からじゃなくて1からになってるから

        Optional<Integer> b2 = empty.stream().map(Expense::amount).reduce(Integer::sum);
        System.out.println("B-2: " + b2);
        // 最初の値を渡していないので、空のときに返せる値が無い。だから「無いかもしれない」を表す Optional で返す
    }
}
