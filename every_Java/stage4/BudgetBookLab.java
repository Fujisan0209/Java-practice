import java.util.*;

public class BudgetBookLab {

    record Expense(String name, String category, int amount, int day) {}

    static Map<String, List<Expense>> groupByCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        Map<String, List<Expense>> map = new LinkedHashMap<>();
        for(Expense e : list) {
            map.computeIfAbsent(e.category(), k -> new ArrayList<>()).add(e);
        }
        return map;
    }

    static Map<String, Integer> totalByCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        Map<String, Integer> result = new LinkedHashMap<>();
        Map<String, List<Expense>> map = groupByCategory(list);
        for(Map.Entry<String, List<Expense>> en : map.entrySet()) {
            int sum = 0;
            for(Expense ep : en.getValue()) {
                sum += ep.amount();
            }
            result.put(en.getKey(), sum);
        }
        return result;
    }

    static List<String> overBudget(List<Expense> list, Map<String, Integer> budgets) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(budgets == null) throw new IllegalArgumentException("budgets must not be null");
        List<String> result = new ArrayList<>();
        Map<String, Integer> map = totalByCategory(list);
        for(String s : map.keySet()) {
            if(map.get(s) > budgets.getOrDefault(s, 0)) {
                result.add(s);
            }
        }
        return result;
    }

    // ===== ここから下は出題側が用意した main（書き換えない） =====
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        String e = String.valueOf(expected), a = String.valueOf(actual);
        if (e.equals(a)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + e + "  実際=" + a); }
    }

    static void checkThrows(String label, Class<? extends Throwable> type, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (Throwable t) {
            if (type.isInstance(t)) { ok++; System.out.println("OK " + label); }
            else { ng++; System.out.println("NG " + label + "  別の例外=" + t); }
        }
    }

    static List<String> namesOf(List<Expense> es) {
        List<String> names = new ArrayList<>();
        for (Expense e : es) names.add(e.name());
        return names;
    }

    public static void main(String[] args) {
        List<Expense> book = new ArrayList<>(List.of(
            new Expense("lunch",  "food",  800, 1),
            new Expense("soap",   "daily", 300, 2),
            new Expense("dinner", "food", 1200, 3),
            new Expense("novel",  "hobby",1500, 3),
            new Expense("coffee", "food",  400, 5),
            new Expense("game",   "hobby",2000, 6)));
        String before = book.toString();

        Map<String, List<Expense>> g = groupByCategory(book);
        check("A-1 袋の順番", "[food, daily, hobby]", g.keySet());
        check("A-2 food の中身", "[lunch, dinner, coffee]", namesOf(g.get("food")));
        check("A-3 daily の中身", "[soap]", namesOf(g.get("daily")));
        check("A-4 hobby の中身", "[novel, game]", namesOf(g.get("hobby")));
        check("A-5 無い袋は null", null, g.get("travel"));

        check("A-6 合計", "{food=2400, daily=300, hobby=3500}", totalByCategory(book));

        Map<String, Integer> budgets = new HashMap<>(Map.of("food", 2000, "daily", 500));
        check("A-7 予算オーバー（hobby は予算なし＝0円扱い）", "[food, hobby]", overBudget(book, budgets));
        budgets.put("food", 2400);
        check("A-8 ちょうど予算は オーバーではない", "[hobby]", overBudget(book, budgets));
        budgets.put("hobby", 5000);
        check("A-9 全部予算内", "[]", overBudget(book, budgets));

        check("A-10 空の帳簿→袋なし", "{}", groupByCategory(new ArrayList<>()));
        check("A-11 空の帳簿→合計なし", "{}", totalByCategory(new ArrayList<>()));
        check("A-12 元の帳簿は変わらない", before, book.toString());

        checkThrows("A-13 groupByCategory(null)", IllegalArgumentException.class, () -> groupByCategory(null));
        checkThrows("A-14 totalByCategory(null)", IllegalArgumentException.class, () -> totalByCategory(null));
        checkThrows("A-15 overBudget(null, 予算)", IllegalArgumentException.class, () -> overBudget(null, budgets));
        checkThrows("A-16 overBudget(帳簿, null)", IllegalArgumentException.class, () -> overBudget(book, null));

        System.out.println("OK=" + ok + " NG=" + ng);
    }
 
}
