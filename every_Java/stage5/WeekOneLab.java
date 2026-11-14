import java.util.*;
import java.util.function.*;

public class WeekOneLab {

    record Expense(String name, String category, int amount) {}

    static Predicate<Expense> inCategory(String category) {
        if(category == null) throw new IllegalArgumentException("category must not be null");

        return ex -> ex.category().equals(category);
    }

    static Predicate<Expense> atLeast(int min) {
        if(min < 0) throw new IllegalArgumentException("min must not be negative");

        return ex -> ex.amount() >= min;
    }

    static List<Expense> select(List<Expense> list, Predicate<Expense> rule) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(rule == null) throw new IllegalArgumentException("rule must not be null");

        List<Expense> result = list.stream()
            .filter(rule)
            .toList();
        return result;
    }

    static List<String> labels(List<Expense> list, Function<Expense, String> f) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(f == null) throw new IllegalArgumentException("f must not be null");

        List<String> result = list.stream()
            // .map(ex -> f.apply(ex)) 
            .map(f)
            .toList();
        return result;
    }

    static void forEachIf(List<Expense> list, Predicate<Expense> rule, Consumer<Expense> action) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(rule == null) throw new IllegalArgumentException("rule must not be null");
        if(action == null) throw new IllegalArgumentException("action must not be null");

        for(Expense ex : list) {
            if(rule.test(ex)) {
                action.accept(ex);
            }
        }
    }

    // ===== ここから下は出題側が用意（変えない） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(String.valueOf(expected), String.valueOf(actual))) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + expected + "  実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (IllegalArgumentException ex) { ok++; System.out.println("OK " + label); }
        catch (RuntimeException ex) { ng++; System.out.println("NG " + label + "  違う例外: " + ex); }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("pen", "daily", 150),
            new Expense("cake", "food", 400),
            new Expense("book", "hobby", 1500),
            new Expense("dinner", "food", 1200));

        System.out.println("=== 区画A ===");
        check("A-1 inCategory food の test", true, inCategory("food").test(list.get(0)));
        check("A-2 inCategory food の test(pen)", false, inCategory("food").test(list.get(1)));
        check("A-3 atLeast 500 の test(cake)", false, atLeast(500).test(list.get(2)));
        check("A-4 atLeast 500 の test(lunch)", true, atLeast(500).test(list.get(0)));
        check("A-5 select food", "[lunch, cake, dinner]", labels(select(list, inCategory("food")), Expense::name));
        check("A-6 select food かつ 500以上", "[lunch, dinner]",
              labels(select(list, inCategory("food").and(atLeast(500))), Expense::name));
        check("A-7 select food 以外", "[pen, book]",
              labels(select(list, inCategory("food").negate()), Expense::name));
        check("A-8 select 該当なし", "[]", select(list, atLeast(99999)));
        check("A-9 labels name:amount", "[lunch:800, pen:150, cake:400, book:1500, dinner:1200]",
              labels(list, e -> e.name() + ":" + e.amount()));
        check("A-10 labels category（メソッド参照）", "[food, daily, food, hobby, food]", labels(list, Expense::category));
        List<String> got = new ArrayList<>();
        forEachIf(list, atLeast(1000), e -> got.add(e.name()));
        check("A-11 forEachIf 1000以上を集める", "[book, dinner]", got);
        List<String> none = new ArrayList<>();
        forEachIf(list, inCategory("travel"), e -> none.add(e.name()));
        check("A-12 forEachIf 該当なし", "[]", none);
        List<Expense> mutable = new ArrayList<>(list);
        select(mutable, inCategory("food"));
        check("A-13 select は元リストを変えない", 5, mutable.size());
        checkThrows("A-14 inCategory(null)", () -> inCategory(null));
        checkThrows("A-15 atLeast(-1)", () -> atLeast(-1));
        checkThrows("A-16 select(null, ...)", () -> select(null, atLeast(0)));
        checkThrows("A-17 labels(list, null)", () -> labels(list, null));
        checkThrows("A-18 forEachIf(list, rule, null)", () -> forEachIf(list, atLeast(0), null));
        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("=== 区画B（実行して、理由を1行で書く） ===");
        // B-1
        Predicate<Expense> rule = inCategory("food");
        rule.and(atLeast(500));
        System.out.println("B-1 " + labels(select(list, rule), Expense::name));
        // and は新しい Predicate を返すだけで rule 自体は変わらない（戻り値を受け取っていない）

        // B-2
        List<String> seen = new ArrayList<>();
        list.stream().filter(e -> { seen.add(e.name()); return true; });
        System.out.println("B-2 " + seen);
        // 終端処理が無いから
    }
}