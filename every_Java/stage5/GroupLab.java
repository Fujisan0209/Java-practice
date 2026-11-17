import java.util.*;
import java.util.stream.*;

public class GroupLab {
    record Expense(String name, String category, int amount) {}

    static Map<String, List<Expense>> byCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        Map<String, List<Expense>> result = list.stream()
            .collect(Collectors.groupingBy(Expense::category));
        return result;
    }

    static List<String> namesIn(List<Expense> list, String category) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(category == null) throw new IllegalArgumentException("category must not be null");

        Map<String, List<Expense>> map = byCategory(list);
        List<Expense> el = map.get(category);
        if(el == null) return List.of();
        List<String> result = el.stream()
            .map(Expense::name)
            .toList();
        return result;
    }

    // ===== ここから下は main（出題側が用意） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        String e = String.valueOf(expected), a = String.valueOf(actual);
        if (e.equals(a)) { ok++; System.out.println("OK " + label + " : " + a); }
        else { ng++; System.out.println("NG " + label + " : 期待=" + e + " / 実際=" + a); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " : 例外が出なかった"); }
        catch (IllegalArgumentException ex) { ok++; System.out.println("OK " + label + " : IllegalArgumentException"); }
        catch (RuntimeException ex) { ng++; System.out.println("NG " + label + " : 違う例外 " + ex); }
    }

    public static void main(String[] args) {
        List<Expense> data = List.of(
            new Expense("lunch", "food", 800),
            new Expense("train", "daily", 300),
            new Expense("cafe", "food", 450),
            new Expense("book", "hobby", 1200),
            new Expense("soap", "daily", 250));

        System.out.println("=== 区画A ===");
        Map<String, List<Expense>> g = byCategory(data);
        check("A-1 food の件数", 2, g.get("food").size());
        check("A-2 food の2件目", "cafe", g.get("food").get(1).name());
        check("A-3 daily の件数", 2, g.get("daily").size());
        check("A-4 グループの数", 3, g.size());
        check("A-5 空リスト", true, byCategory(List.of()).isEmpty());
        checkThrows("A-6 byCategory(null)", () -> byCategory(null));
        check("A-7 namesIn food", List.of("lunch", "cafe"), namesIn(data, "food"));
        check("A-8 namesIn hobby", List.of("book"), namesIn(data, "hobby"));
        check("A-9 namesIn travel（無い項目）", List.of(), namesIn(data, "travel"));
        checkThrows("A-10 namesIn(null, ...)", () -> namesIn(null, "food"));
        checkThrows("A-11 namesIn(..., null)", () -> namesIn(data, null));
        check("A-12 元のリストはそのまま", 5, data.size());
        System.out.println("OK=" + ok + " / NG=" + ng);

        System.out.println("=== 区画B ===");
        // B-1
        Map<String, List<Expense>> m = byCategory(data);
        m.getOrDefault("travel", new ArrayList<>());
        System.out.println("B-1 travel はある？ " + m.containsKey("travel"));
        // 入らない getOrDefaultはみる専門だから

        // B-2
        try {
            System.out.println("B-2 travel の件数 " + byCategory(data).get("travel").size());
        } catch (RuntimeException ex) {
            System.out.println("B-2 例外: " + ex.getClass().getSimpleName());
        }
        // travelというキーはないからnullになりNullPointerException
    }
}
