import java.util.*;

public class BudgetPeek {
    public static void main(String[] args) {
        // B-1
        Map<String, List<String>> map = new HashMap<>();
        map.getOrDefault("food", new ArrayList<>()).add("lunch");
        map.getOrDefault("food", new ArrayList<>()).add("dinner");
        System.out.println("B-1: " + map);
        // getOrDefaultはみるだけ
        // B-1: getOrDefault は代わりの袋を返すだけで map に入れないので、lunch は誰も持っていない袋に入って消える

        // B-2
        Map<String, Integer> budgets = Map.of("food", 2000);
        int hobby = budgets.get("hobby");
        System.out.println("B-2: " + hobby);
        // hobbyというkeyはない
        // B-2: 無いキーの get は null を返し、その null を int に変換しようとして NullPointerException になる
    }
}