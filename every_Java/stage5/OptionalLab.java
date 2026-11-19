import java.util.*;

public class OptionalLab {
    record Expense(String name, String category, int amount) {}

    static Optional<Expense> findByName(List<Expense> list, String name) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(name == null) throw new IllegalArgumentException("name must not be null");

        return list.stream()
            .filter(e -> e.name().equals(name))
            .findFirst();
    }

    static int amountOf(List<Expense> list, String name) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(name == null) throw new IllegalArgumentException("name must not be null");

        /* return list.stream()
            .filter(e -> e.name().equals(name))
            .mapToInt(Expense::amount)
            .findFirst()
            .orElse(0); */

        return findByName(list, name)
            .map(Expense::amount)
            .orElse(0);
    }

    static String labelOf(List<Expense> list, String name) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(name == null) throw new IllegalArgumentException("name must not be null");

        /* return list.stream()
            .filter(e -> e.name().equals(name))
            .map(e -> e.name() + ":" + e.amount() + "円")
            .findFirst()
            .orElse("なし"); */
        
        return findByName(list, name)
            .map(e -> e.name() + ":" + e.amount() + "円")
            .orElse("(なし)");
    }

    static Optional<Integer> budgetOf(Map<String, Integer> budgets, String category) {
        if(budgets == null) throw new IllegalArgumentException("budgets must not be null");
        if(category == null) throw new IllegalArgumentException("category must not be null");

        return Optional.ofNullable(budgets.get(category));
    }

    static int remaining(Map<String, Integer> budgets, List<Expense> list, String category) {
        if(budgets == null) throw new IllegalArgumentException("budgets must not be null");
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(category == null) throw new IllegalArgumentException("category must not be null");

        int budget = budgetOf(budgets, category).orElseThrow(() -> new IllegalStateException("no budget"));

        int used = list.stream()
            .filter(e -> e.category().equals(category))
            .mapToInt(Expense::amount)
            .sum();
            
        return budget - used;
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + " 期待=" + expected + " 実際=" + actual); }
    }
    static void checkThrows(String label, Class<? extends Exception> type, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + " 例外が出なかった"); }
        catch (Exception e) {
            if (type.isInstance(e)) { ok++; System.out.println("OK " + label); }
            else { ng++; System.out.println("NG " + label + " 期待=" + type.getSimpleName() + " 実際=" + e.getClass().getSimpleName()); }
        }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("book", "hobby", 1500),
            new Expense("coffee", "food", 300),
            new Expense("lunch", "food", 650));
        Map<String, Integer> budgets = Map.of("food", 3000, "hobby", 1000);

        // ===== 区画A =====
        check("A-1 findByName ある", "book", findByName(list, "book").map(Expense::name).orElse("なし"));
        check("A-2 findByName 同名は先の方", 800, findByName(list, "lunch").get().amount());
        check("A-3 findByName ない", Optional.empty(), findByName(list, "tea"));
        check("A-4 amountOf ある", 300, amountOf(list, "coffee"));
        check("A-5 amountOf ないは0", 0, amountOf(list, "tea"));
        check("A-6 labelOf ある", "book:1500円", labelOf(list, "book"));
        check("A-7 labelOf ない", "(なし)", labelOf(list, "tea"));
        check("A-8 budgetOf ある", Optional.of(3000), budgetOf(budgets, "food"));
        check("A-9 budgetOf ない", Optional.empty(), budgetOf(budgets, "travel"));
        check("A-10 remaining food", 1250, remaining(budgets, list, "food"));
        check("A-11 remaining hobby 使いすぎはマイナス", -500, remaining(budgets, list, "hobby"));
        check("A-12 remaining 予算あり支出0件", 3000, remaining(Map.of("food", 3000), List.of(), "food"));
        checkThrows("A-13 remaining 予算なしは ISE", IllegalStateException.class, () -> remaining(budgets, list, "travel"));
        checkThrows("A-14 findByName(null,…)", IllegalArgumentException.class, () -> findByName(null, "book"));
        checkThrows("A-15 findByName(…,null)", IllegalArgumentException.class, () -> findByName(list, null));
        checkThrows("A-16 budgetOf(null,…)", IllegalArgumentException.class, () -> budgetOf(null, "food"));
        System.out.println("OK=" + ok + " NG=" + ng);

        // ===== 区画B（実行して、理由を1行で）=====
        // B-1
        try {
            Optional<Integer> b1 = Optional.of(budgets.get("travel"));
            System.out.println("B-1: " + b1);
        } catch (Exception e) {
            System.out.println("B-1: " + e.getClass().getSimpleName());
        }
        // budgets.get("travel")はnullで.of()の引数がnullのときはNPEになる。
        // 例外にならないようにするにはofNullableを使う

        // B-2
        Optional<Expense> b2 = findByName(list, "book");
        b2.map(Expense::amount);
        System.out.println("B-2: " + b2);
        // Optional の map は「金額が入った新しい Optional」を返すだけ。その戻り値を捨てたので、b2 は Expense の箱のまま
    }
}
