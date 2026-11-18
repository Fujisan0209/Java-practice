import java.util.*;
import java.util.stream.*;

public class FlatMapLab {
    record Expense(String name, String category, int amount) {}

    record Receipt(String shop, List<Expense> items) {}

    static List<Expense> allItems(List<Receipt> receipts) {
        if(receipts == null) throw new IllegalArgumentException("receipt must not be null");

        List<Expense> result = receipts.stream()
            .flatMap(r -> r.items().stream())
            .toList();
        return result;
    }

    static List<String> allNames(List<Receipt> receipts) {
        if(receipts == null) throw new IllegalArgumentException("receipt must not be null");

        List<Expense> list = allItems(receipts);
        List<String> result = list.stream()
            .map(Expense::name)
            .toList();
        return result;
    }

    static int totalOf(List<Receipt> receipts, String category) {
        if(receipts == null) throw new IllegalArgumentException("receipt must not be null");
        if(category == null) throw new IllegalArgumentException("category must not be null");

        int result = receipts.stream()
            .flatMap(r -> r.items().stream())
            .filter(e -> e.category().equals(category))
            .mapToInt(Expense::amount)
            .sum();
        return result;
    }

    static Map<String, Long> countByCategory(List<Receipt> receipts) {
        if(receipts == null) throw new IllegalArgumentException("receipt must not be null");

        Map<String, Long> result = receipts.stream()
            .flatMap(r -> r.items().stream())
            .collect(Collectors.groupingBy(Expense::category, Collectors.counting()));
        return result;
    }

    // ===== ここから下は main（このまま貼る） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(String.valueOf(expected), String.valueOf(actual))) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + " 期待=" + expected + " 実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " 例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label); }
    }

    public static void main(String[] args) {
        List<Receipt> rs = List.of(
            new Receipt("conbini", List.of(
                new Expense("onigiri", "food", 150),
                new Expense("tea", "food", 120))),
            new Receipt("drugstore", List.of(
                new Expense("shampoo", "daily", 600),
                new Expense("candy", "food", 100))),
            new Receipt("bookstore", List.of(
                new Expense("manga", "hobby", 500))),
            new Receipt("cafe", List.of()));

        System.out.println("--- A ---");
        check("A-1 allItems の件数", 5, allItems(rs).size());
        check("A-2 allItems の先頭", "onigiri", allItems(rs).get(0).name());
        check("A-3 allItems の最後", "manga", allItems(rs).get(4).name());
        check("A-4 allNames", "[onigiri, tea, shampoo, candy, manga]", allNames(rs));
        check("A-5 allNames 空リスト", "[]", allNames(List.of()));
        check("A-6 allNames 中身が空のレシートだけ", "[]", allNames(List.of(new Receipt("cafe", List.of()))));
        check("A-7 totalOf food", 370, totalOf(rs, "food"));
        check("A-8 totalOf hobby", 500, totalOf(rs, "hobby"));
        check("A-9 totalOf 無いカテゴリ", 0, totalOf(rs, "travel"));
        check("A-10 countByCategory food", 3L, countByCategory(rs).get("food"));
        check("A-11 countByCategory daily", 1L, countByCategory(rs).get("daily"));
        check("A-12 countByCategory 種類の数", 3, countByCategory(rs).size());
        check("A-13 元の rs は変わらない", 4, rs.size());
        checkThrows("A-14 allItems(null)", () -> allItems(null));
        checkThrows("A-15 allNames(null)", () -> allNames(null));
        checkThrows("A-16 totalOf(null, ...)", () -> totalOf(null, "food"));
        checkThrows("A-17 totalOf(..., null)", () -> totalOf(rs, null));
        checkThrows("A-18 countByCategory(null)", () -> countByCategory(null));
        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("--- B ---");
        // B-1
        List<List<Expense>> nested = rs.stream().map(r -> r.items()).toList();
        System.out.println(nested.size());
        System.out.println(allItems(rs).size());
        // 上はListの数、下はExpenseの数を数えている。なので数字が違う。

        // B-2
        List<Expense> items = allItems(rs);
        items.add(new Expense("gum", "food", 80));
        System.out.println(items.size());
        // 変更不可のListを変更しようとしたから。
    }
}
