import java.util.*;
import java.util.stream.*;

public class CollectLab {
    record Expense(String name, String category, int amount) {}
    
    static Map<String, Integer> amountByName(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        return list.stream()
            .collect(Collectors.toMap(e -> e.name(), e -> e.amount(), Integer::sum, LinkedHashMap::new));
    }

    static String categoryLine(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        return list.stream()
            .map(Expense::category)
            .distinct()
            .collect(Collectors.joining(", "));
    }

    static Map<String, String> namesByCategory(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");

        return list.stream()
            .collect(Collectors.groupingBy(
                Expense::category,
                LinkedHashMap::new,
                Collectors.mapping(
                    Expense::name,
                    Collectors.joining("/")
                )
            ));
    }

    // ===== ここから下は出題側が用意した main（変更しない） =====
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(String.valueOf(expected), String.valueOf(actual))) {
            ok++; System.out.println("OK " + label + " : " + actual);
        } else {
            ng++; System.out.println("NG " + label + " : 期待=" + expected + " / 実際=" + actual);
        }
    }

    static void checkThrows(String label, Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
            ng++; System.out.println("NG " + label + " : 例外が出なかった");
        } catch (Throwable t) {
            if (type.isInstance(t)) { ok++; System.out.println("OK " + label + " : " + t.getClass().getSimpleName()); }
            else { ng++; System.out.println("NG " + label + " : 期待=" + type.getSimpleName() + " / 実際=" + t.getClass().getSimpleName()); }
        }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch",  "food",  800),
            new Expense("tissue", "daily", 200),
            new Expense("coffee", "food",  300),
            new Expense("lunch",  "food",  700),
            new Expense("game",   "hobby", 5000)
        );
        List<Expense> empty = List.of();

        System.out.println("=== 区画A：自動照合 ===");
        Map<String, Integer> byName = amountByName(list);
        check("A-1 amountByName", "{lunch=1500, tissue=200, coffee=300, game=5000}", byName);
        check("A-2 lunch は合計", 1500, byName.get("lunch"));
        check("A-3 種類の数", 4, byName.size());
        check("A-4 空リスト", "{}", amountByName(empty));
        checkThrows("A-5 amountByName(null)", IllegalArgumentException.class, () -> amountByName(null));

        check("A-6 categoryLine", "food, daily, hobby", categoryLine(list));
        check("A-7 空リストは空文字", "", categoryLine(empty));
        check("A-8 空リストは null ではない", false, categoryLine(empty) == null);
        checkThrows("A-9 categoryLine(null)", IllegalArgumentException.class, () -> categoryLine(null));

        Map<String, String> byCat = namesByCategory(list);
        check("A-10 namesByCategory", "{food=lunch/coffee/lunch, daily=tissue, hobby=game}", byCat);
        check("A-11 food だけ", "lunch/coffee/lunch", byCat.get("food"));
        check("A-12 空リスト", "{}", namesByCategory(empty));
        checkThrows("A-13 namesByCategory(null)", IllegalArgumentException.class, () -> namesByCategory(null));

        System.out.println("OK=" + ok + " / NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B：実行して、理由を1行で ===");
        // B-1：合計の指定（3つ目の引数）を書かないと？
        try {
            Map<String, Integer> b1 = list.stream()
                .collect(Collectors.toMap(Expense::name, Expense::amount));
            System.out.println("B-1 " + b1);
        } catch (Exception e) {
            System.out.println("B-1 " + e.getClass().getSimpleName() + " : " + e.getMessage());
        }
        // B-1：同じキー lunch が2回来たとき、800 と 700 をどうまとめるか（3つ目の引数）を書いていないので、Java が決められず IllegalStateException で止まる

        // B-2：distinct を消すと？
        String b2 = list.stream().map(Expense::category).collect(Collectors.joining(", "));
        System.out.println("B-2 " + b2);
        // B-2：distinct がないと、map で項目に変えたものが重複したまま全部 joining に流れる
    }
}
