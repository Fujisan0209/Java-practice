import java.util.*;
import java.util.stream.*;

public class PartitionLab {
    record Expense(String name, String category, int amount) {}

    static Map<Boolean, List<Expense>> splitByAmount(List<Expense> list, int border) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(border < 0) throw new IllegalArgumentException("border must not be negative");

        Map<Boolean, List<Expense>> result = list.stream()
            .collect(Collectors.partitioningBy(e -> e.amount() >= border));
        return result;
    }

    static Map<Boolean, Long> countByAmount(List<Expense> list, int border) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(border < 0) throw new IllegalArgumentException("border must not be negative");

        Map<Boolean, Long> result = list.stream()
            .collect(Collectors.partitioningBy(e -> e.amount() >= border, Collectors.counting()));
        return result;
    } 

    static List<String> cheapNames(List<Expense> list, int border) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(border < 0) throw new IllegalArgumentException("border must not be negative");

        Map<Boolean, List<Expense>> map = splitByAmount(list, border);
        List<String> result = map.get(false).stream()
            .map(Expense::name)
            .toList();
        return result;
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("book", "hobby", 1500),
            new Expense("coffee", "food", 300),
            new Expense("shampoo", "daily", 600),
            new Expense("game", "hobby", 5000)
        );

        System.out.println("--- A ---");
        System.out.println("期待: {false=3, true=2}/ 実際: " + countByAmount(list, 1000));
        System.out.println("期待: [lunch, coffee, shampoo]/ 実際: " + cheapNames(list, 1000));
        System.out.println("期待: {false=5, true=0}/ 実際: " + countByAmount(list, 10000));
        System.out.println("期待: []/ 実際: " + cheapNames(list, 0));
        System.out.println("期待: {false=0, true=0}/ 実際: " + countByAmount(List.of(), 1000));
        System.out.println("期待: [Expense[name=book, category=hobby, amount=1500], Expense[name=game, category=hobby, amount=5000]]/ 実際: " + splitByAmount(list, 1000).get(true));

        System.out.println("--- B ---");
        // B-1
        System.out.println(splitByAmount(list, 10000).get(true));
        // 10000より大きいものはない
        Map<String, List<Expense>> g = list.stream().collect(Collectors.groupingBy(Expense::category));
        System.out.println(g.get("travel"));
        // travelというキーはないのでnull

        // B-2
        Map<String, Integer> m = new HashMap<>();
        System.out.println(m.put("food", 800));
        // foodというキーに元々入っていたvalueを返す、しかし、新しく登録したのでnull
        System.out.println(m.put("food", 1100));
        // 上記と同じ理由で800
        System.out.println(m);
        // 1100で上書きされたから
    }
}
