import java.util.*;
import java.util.stream.Stream;

public class StreamLab {

    record Expense(String name, String category, int amount) {}

    static List<String> namesIn(List<Expense> list, String item) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(item == null) throw new IllegalArgumentException("item must not be null");

        List<String> result = list.stream()
            .filter(e -> e.category().equals(item))
            .map(e -> e.name())
            .toList();
        return result;
    }

    static List<Expense> atLeast(List<Expense> list, int border) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(border < 0) throw new IllegalArgumentException("border must not be negative");

        List<Expense> result = list.stream()
            .filter(e -> e.amount() >= border)
            .toList();
        return result;
    }

    static int countIn(List<Expense> list, String item) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(item == null) throw new IllegalArgumentException("item must not be null");
        // if(list.isEmpty()) return 0;
        // 上の処理が無くてもcount()が0を返す

        long count = list.stream()
            .filter(e -> e.category().equals(item))
            .count();
        return (int)count;
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("book", "hobby", 1500),
            new Expense("coffee", "food", 300),
            new Expense("game", "hobby", 6000),
            new Expense("soap", "daily", 0)
        );
        System.out.println("A-1 期待: [lunch, coffee]/ 実際: " + namesIn(list, "food"));
        System.out.println("A-2 期待: []/ 実際: " + namesIn(list, "travel"));
        System.out.println("A-3 期待: [Expense[name=book, category=hobby, amount=1500], Expense[name=game, category=hobby, amount=6000]]/ 実際: " + atLeast(list, 1000));
        System.out.println("A-4 期待: 5/ 実際: " + atLeast(list, 0).size());
        System.out.println("A-5 期待: 2/ 実際: " + countIn(list, "hobby"));
        System.out.println("A-6 期待: 0/ 実際: " + countIn(new ArrayList<>(), "food"));
        try {
            atLeast(list, -1);
        } catch(IllegalArgumentException e) {
            System.out.println("A-7 期待: border must not be negative/ 実際: " + e.getMessage());
        }
        try {
            namesIn(null, "food");
        } catch(IllegalArgumentException e) {
            System.out.println("A-8 期待: list must not be null/ 実際: " + e.getMessage());
        }
        try {
            namesIn(list, "food").add("x");
        } catch(UnsupportedOperationException e) {
            System.out.println("A-9 期待: UnsupportedOperationException/ 実際: " + e.getMessage());
        }

        System.out.println("--- B ---");
        // B-1
        List<Integer> nums = new ArrayList<>(List.of(5, 12, 3, 20));
        nums.stream().filter(n -> n >= 10);
        System.out.println(nums);
        // filter は nums を書き換えず、新しい流れ（Stream）を返すだけ。その戻り値を捨てているので何も残らない
        // B-2
        Stream<Integer> s = nums.stream().filter(n -> n >= 10);
        System.out.println(s.count());
        System.out.println(s.toList());
        // countの時点で終端処理を行ったから
    }
}
