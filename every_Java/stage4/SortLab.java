import java.util.*;

record Expense(String name, String category, int amount, int day) {}
// name=品名 / category=カテゴリ / amount=金額(円) / day=何日に使ったか

public class SortLab {
    static List<Expense> byAmountDesc(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        Comparator<Expense> byAmount = Comparator.comparingInt(l -> l.amount());
        Comparator<Expense> order = byAmount.reversed()
            .thenComparing(l -> l.name());
        List<Expense> result = new ArrayList<>(list);

        result.sort(order);
        return result;
    }
}
