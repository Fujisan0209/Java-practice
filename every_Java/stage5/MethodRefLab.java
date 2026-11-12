import java.util.*;
import java.util.function.Function;

public class MethodRefLab {

    record Expense(String name, String category, int amount) {}

    record Label(String text) {}

    static boolean isLarge(Expense e) {
        return e.amount() >= 1000;
    }

    static List<String> upperNames(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        List<String> result = list.stream()
            .map(Expense::name)
            .map(String::toUpperCase)
            .toList();
        return result;
    }

    static List<Expense> largeOnes(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        List<Expense> result = list.stream()
            .filter(MethodRefLab::isLarge)
            .toList();
        return result;
    }

    static List<String> withPrefix(List<Expense> list, String prefix) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(prefix == null) throw new IllegalArgumentException("prefix must not be null");
        List<String> result = list.stream()
            .map(Expense::name)
            .map(prefix::concat)
            .toList();
        return result;
    }

    static List<Label> toLabels(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        List<Label> result = list.stream()
            .map(Expense::name)
            .map(Label::new)
            .toList();
        return result;
    }

    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        String e = String.valueOf(expected), a = String.valueOf(actual);
        if (e.equals(a)) { ok++; System.out.println("OK " + label + " : " + a); }
        else { ng++; System.out.println("NG " + label + " : 期待 " + e + " / 実際 " + a); }
    }

    static void checkThrows(String label, Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
            ng++; System.out.println("NG " + label + " : 例外が出なかった");
        } catch (Throwable t) {
            if (type.isInstance(t)) { ok++; System.out.println("OK " + label + " : " + t.getClass().getSimpleName()); }
            else { ng++; System.out.println("NG " + label + " : 期待 " + type.getSimpleName() + " / 実際 " + t.getClass().getSimpleName()); }
        }
    }

    public static void main(String[] args) {
        List<Expense> list = List.of(
            new Expense("lunch", "food", 800),
            new Expense("book", "hobby", 1500),
            new Expense("coffee", "food", 1000),
            new Expense("pen", "daily", 120)
        );
        List<Expense> empty = List.of();

        System.out.println("===== 区画A：自動照合 =====");
        check("A-1 isLarge 1500", true, isLarge(list.get(1)));
        check("A-2 isLarge 1000（ちょうど）", true, isLarge(list.get(2)));
        check("A-3 isLarge 800", false, isLarge(list.get(0)));
        check("A-4 upperNames", "[LUNCH, BOOK, COFFEE, PEN]", upperNames(list));
        check("A-5 upperNames 空", "[]", upperNames(empty));
        check("A-6 largeOnes", "[Expense[name=book, category=hobby, amount=1500], Expense[name=coffee, category=food, amount=1000]]", largeOnes(list));
        check("A-7 largeOnes 空", "[]", largeOnes(empty));
        check("A-8 withPrefix #", "[#lunch, #book, #coffee, #pen]", withPrefix(list, "#"));
        check("A-9 withPrefix 空文字", "[lunch, book, coffee, pen]", withPrefix(list, ""));
        check("A-10 toLabels", "[Label[text=lunch], Label[text=book], Label[text=coffee], Label[text=pen]]", toLabels(list));
        check("A-11 toLabels 1件目の text", "lunch", toLabels(list).get(0).text());
        check("A-12 元のリストは変わらない", 4, list.size());
        checkThrows("A-13 upperNames(null)", IllegalArgumentException.class, () -> upperNames(null));
        checkThrows("A-14 largeOnes(null)", IllegalArgumentException.class, () -> largeOnes(null));
        checkThrows("A-15 withPrefix(null, \"#\")", IllegalArgumentException.class, () -> withPrefix(null, "#"));
        checkThrows("A-16 withPrefix(list, null)", IllegalArgumentException.class, () -> withPrefix(list, null));
        checkThrows("A-17 toLabels(null)", IllegalArgumentException.class, () -> toLabels(null));
        System.out.println("OK=" + ok + " NG=" + ng + " / 17");

        System.out.println();
        System.out.println("===== 区画B：実行→理由を1行 =====");
        // B-1
        List<String> names = new ArrayList<>(List.of("lunch", "book"));
        names.forEach(String::toUpperCase);
        System.out.println("B-1 " + names);
        // toUpperCase は新しい String を返すだけで、forEach はその戻り値を捨てるので names は変わらない

        // B-2
        Function<String, Integer> f = String::length;
        System.out.println("B-2 " + f.apply("coffee"));
        // String::length は「流れてきた物のメソッド」（イの形）なので、apply に渡した "coffee" が持ち主になり "coffee".length() = 6
    }
}
