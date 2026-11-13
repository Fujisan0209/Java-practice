import java.util.*;
import java.util.function.*;

public class ConsumerLab {
    record Expense(String name, String category, int amount) {}

    static void applyAll(List<Expense> list, Consumer<Expense> action) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(action == null) throw new IllegalArgumentException("action must not be null");

        for(Expense e : list) {
            action.accept(e);
        }
    }

    static Consumer<Expense> collectInto(List<String> out) {
        if(out == null) throw new IllegalArgumentException("out must not be null");
        return e -> out.add(e.name());
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
            new Expense("pen", "daily", 120)
        );
        List<Expense> empty = List.of();

        System.out.println("===== 区画A：自動照合 =====");
        List<String> out1 = new ArrayList<>();
        applyAll(list, collectInto(out1));
        check("A-1 名前が入力順に集まる", "[lunch, book, pen]", out1);

        List<String> out2 = new ArrayList<>();
        applyAll(empty, collectInto(out2));
        check("A-2 空リストなら何も入らない", "[]", out2);

        List<String> out3 = new ArrayList<>(List.of("start"));
        applyAll(list, collectInto(out3));
        check("A-3 もとの中身の後ろに足す", "[start, lunch, book, pen]", out3);

        List<String> out4 = new ArrayList<>();
        Consumer<Expense> c4 = collectInto(out4);
        applyAll(list, c4);
        applyAll(list, c4);
        check("A-4 同じ Consumer を2回使える", 6, out4.size());

        List<Integer> amounts = new ArrayList<>();
        applyAll(list, e -> amounts.add(e.amount()));
        check("A-5 ラムダを直接渡す", "[800, 1500, 120]", amounts);

        check("A-6 元のリストは変わらない", 3, list.size());
        checkThrows("A-7 applyAll(null, …)", IllegalArgumentException.class, () -> applyAll(null, collectInto(new ArrayList<>())));
        checkThrows("A-8 applyAll(list, null)", IllegalArgumentException.class, () -> applyAll(list, null));
        checkThrows("A-9 collectInto(null)", IllegalArgumentException.class, () -> collectInto(null));
        System.out.println("OK=" + ok + " NG=" + ng + " / 9");

        System.out.println();
        System.out.println("===== 区画B：実行→理由を1行 =====");
        // B-1
        List<String> outB1 = new ArrayList<>();
        Consumer<Expense> cB1 = collectInto(outB1);
        System.out.println("B-1 " + outB1);
        // まだ方法しか書いていない。本当に作っただけだから

        // B-2
        List<String> outB2 = new ArrayList<>();
        list.forEach(collectInto(outB2));
        System.out.println("B-2 " + outB2);
        // forEachがListの各要素にcollectIntoを使っているから
    }
}
