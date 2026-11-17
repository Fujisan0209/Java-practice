import java.util.*;
import java.util.stream.*;

public class SumLab {
    record Expense(String name, String category, int amount, int day) {}

    static Map<String, Integer> totalByCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(list.isEmpty()) return Map.of();

        Map<String, Integer> result = list.stream()
            .collect(Collectors.groupingBy(Expense::category, Collectors.summingInt(Expense::amount)));
        return result;
    }

    static Map<String, Long> countByCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(list.isEmpty()) return Map.of();

        Map<String, Long> result = list.stream()
            .collect(Collectors.groupingBy(Expense::category, Collectors.counting()));
        return result;
    }

    static Map<Integer, Integer> totalByDay(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(list.isEmpty()) return Map.of();

        Map<Integer, Integer> result = list.stream()
            .collect(Collectors.groupingBy(Expense::day, Collectors.summingInt(Expense::amount)));
        return result;
    }

    static Optional<String> topCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(list.isEmpty()) return Optional.empty();

        Map<String, Integer> map = totalByCategory(list);
        Map.Entry<String, Integer> max = null;
        for(Map.Entry<String, Integer> m : map.entrySet()) {
            if(max == null) max = m;
            if(m.getValue() > max.getValue()) max = m;
            if(m.getValue().equals(max.getValue()) && m.getKey().compareTo(max.getKey()) < 0) {
                max = m;
            }
        }
        return Optional.of(max.getKey());
    }

    // ===== ここから下は出題側が用意した main（変更しない） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + expected + "  実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label); }
        catch (RuntimeException e) { ng++; System.out.println("NG " + label + "  別の例外 " + e); }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch",  "food",  800, 1),
            new Expense("train",  "daily", 300, 1),
            new Expense("dinner", "food",  700, 2),
            new Expense("book",   "hobby", 1500, 2),
            new Expense("soap",   "daily", 200, 3)
        );
        List<Expense> empty = List.of();

        System.out.println("=== 区画A ===");
        check("A-1  totalByCategory food",  1500, totalByCategory(list).get("food"));
        check("A-2  totalByCategory daily", 500,  totalByCategory(list).get("daily"));
        check("A-3  totalByCategory hobby", 1500, totalByCategory(list).get("hobby"));
        check("A-4  totalByCategory 全体", Map.of("food", 1500, "daily", 500, "hobby", 1500), totalByCategory(list));
        check("A-5  totalByCategory 空",   Map.of(), totalByCategory(empty));
        check("A-6  countByCategory food",  2L, countByCategory(list).get("food"));
        check("A-7  countByCategory 全体", Map.of("food", 2L, "daily", 2L, "hobby", 1L), countByCategory(list));
        check("A-8  countByCategory 空",   Map.of(), countByCategory(empty));
        check("A-9  totalByDay 全体", Map.of(1, 1100, 2, 2200, 3, 200), totalByDay(list));
        check("A-10 totalByDay 空",   Map.of(), totalByDay(empty));
        check("A-11 topCategory 同額は辞書順", Optional.of("food"), topCategory(list));
        check("A-12 topCategory 同額は辞書順その2", Optional.of("book"),
              topCategory(List.of(new Expense("quiz", "game", 500, 1), new Expense("novel", "book", 500, 2))));
        check("A-13 topCategory 空", Optional.empty(), topCategory(empty));
        checkThrows("A-14 totalByCategory(null)", () -> totalByCategory(null));
        checkThrows("A-15 countByCategory(null)", () -> countByCategory(null));
        checkThrows("A-16 totalByDay(null)",      () -> totalByDay(null));
        checkThrows("A-17 topCategory(null)",     () -> topCategory(null));
        System.out.println("区画A: OK=" + ok + " / NG=" + ng);

        System.out.println("=== 区画B（実行して、理由を1行で） ===");
        // B-1 第2引数を付け忘れた groupingBy
        Map<String, List<Expense>> bags = list.stream().collect(Collectors.groupingBy(Expense::category));
        System.out.println("B-1 " + bags.get("daily"));
        // 第2引数がないと袋に分けるだけなので、中身は Expense の List のまま（合計の 500 にはならない）

        // B-2 getOrDefault のあとで、map を表示する
        Map<String, Integer> totals = new HashMap<>(totalByCategory(list));
        int travel = totals.getOrDefault("travel", 0);
        System.out.println("B-2 travel=" + travel + " / containsKey=" + totals.containsKey("travel"));
        // false getOrDefaultはいれているわけじゃないからkeyはない
    }
}