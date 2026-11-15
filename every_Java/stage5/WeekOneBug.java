import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class WeekOneBug {
    record Expense(String name, String category, int amount) {}

    static Predicate<Expense> inCategory(String category) {
        return e -> e.category().equals(category);
    }

    static Predicate<Expense> atLeast(int min) {
        return e -> e.amount() >= min;
    }

    static int sum(List<Expense> list, Predicate<Expense> p) {
        int total = 0;
        for (Expense e : list) {
            if (p.test(e)) {
                total += e.amount();
            }
        }
        return total;
    }

    static List<String> labels(List<Expense> list, Predicate<Expense> p) {
        return list.stream()
                .filter(p)
                .map(e -> e.name() + "(" + e.amount() + ")")
                .toList();
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("tissue", "daily", 300),
            new Expense("dinner", "food", 1200),
            new Expense("book", "hobby", 1500),
            new Expense("soap", "daily", 450)
        );

        Predicate<Expense> isFood = inCategory("food");
        Predicate<Expense> isDaily = inCategory("daily");

        // 1. カテゴリ別の合計
        int foodTotal = sum(list, isFood);
        int dailyTotal = sum(list, isDaily); // isFood → isDailyに変えた
        System.out.println("food合計: " + foodTotal);
        System.out.println("daily合計: " + dailyTotal);

        // 2. 食費以外で 400円以上のものを表示
        Predicate<Expense> target = atLeast(400);
        // target.and(isFood.negate())によってtargetが変わるわけではないから
        System.out.println("食費以外で400円以上: " + labels(list, target.and(isFood.negate())));

        // 3. 1000円以上の件数と名前
        Stream<Expense> big = list.stream().filter(atLeast(1000));
        System.out.println("1000円以上の件数: " + big.count());
        Stream<Expense> big1 = list.stream().filter(atLeast(1000));
        System.out.println("1000円以上: " + big1.map(Expense::name).toList());
        // .count()で終端処理があるから
    }
}