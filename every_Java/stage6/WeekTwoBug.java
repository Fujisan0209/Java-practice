import java.util.*;
import java.util.stream.*;

public class WeekTwoBug {
    record Item(String name, String category, int amount) {}

    // 全部の合計金額
    static int total(List<Item> items) {
        return items.stream().map(Item::amount).reduce(0, Integer::sum);
    }

    // 項目ごとの合計（項目は最初に出てきた順）
    static Map<String, Integer> totalByCategory(List<Item> items) {
        return items.stream()
            .collect(Collectors.toMap(Item::category, Item::amount, Integer::sum, LinkedHashMap::new));
    }

    // 一番高い買い物の名前（1件もなければ "none"）
    static String mostExpensiveName(List<Item> items) {
        return items.stream()
                .max(Comparator.comparingInt(Item::amount))
                .map(Item::name)
                .orElse("none");
    }

    // 500円以上(true)と500円未満(false)に分けた名前
    static Map<Boolean, List<String>> splitByPrice(List<Item> items) {
        return items.stream()
            .collect(Collectors.partitioningBy(i -> i.amount() >= 500,
                Collectors.mapping(Item::name, Collectors.toList())));
    }

    // 指定した項目の名前を ", " でつなぐ
    static String namesOf(List<Item> items, String category) {
        return items.stream()
                .filter(i -> i.category().equals(category))
                .map(Item::name)
                .collect(Collectors.joining(", "));
    }

    public static void main(String[] args) {
        List<Item> week = List.of(
                new Item("bento", "food", 480),
                new Item("detergent", "daily", 320),
                new Item("bread", "food", 350),
                new Item("book", "hobby", 880),
                new Item("tissue", "daily", 250),
                new Item("ramen", "food", 900));

        System.out.println("total    : " + total(week));
        System.out.println("category : " + totalByCategory(week));
        System.out.println("top      : " + mostExpensiveName(week));
        Map<Boolean, List<String>> split = splitByPrice(week);
        System.out.println(">= 500   : " + split.get(true));
        System.out.println("<  500   : " + split.get(false));
        System.out.println("food     : " + namesOf(week, "food"));
        System.out.println("empty    : " + mostExpensiveName(List.of()));
    }
}