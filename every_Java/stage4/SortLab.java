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

    static List<Expense> byCategoryThenDayDesc(List<Expense> list) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        Comparator<Expense> byCategory = Comparator.comparing(l -> l.category());
        Comparator<Expense> byDay = Comparator.comparing(l -> l.day());
        Comparator<Expense> byAmount = Comparator.comparing(l -> l.amount());
        Comparator<Expense> order = byCategory
            .thenComparing(byDay.reversed())
            .thenComparing(byAmount.reversed());
        
        List<Expense> result = new ArrayList<>(list);
        result.sort(order);
        return result;
    }

    static List<String> topN(List<Expense> list, int n) {
        if(list == null) throw new IllegalArgumentException("list must not be null");
        if(n < 0) throw new IllegalArgumentException("n must be positive");
        List<Expense> a = byAmountDesc(list);
        int count = 0;
        List<String> result = new ArrayList<>();
        for(Expense e : a) {
            if(count == n) break;
            result.add(e.name());
            count++;
        }
        return result;
    }

    // ---------- ここから下は完成済み（変更しない） ----------
    static int ok = 0, ng = 0;

    static void check(String label, Object actual, String expected) {
        String a = String.valueOf(actual);
        if (a.equals(expected)) { ok++; System.out.println("OK " + label + " " + a); }
        else { ng++; System.out.println("NG " + label + " 実際=" + a + " 期待=" + expected); }
    }

    static void checkThrows(String label, Runnable r, Class<? extends Throwable> type) {
        try {
            r.run();
            ng++; System.out.println("NG " + label + " 例外が出なかった");
        } catch (Throwable t) {
            if (type.isInstance(t)) { ok++; System.out.println("OK " + label + " " + t.getClass().getSimpleName()); }
            else { ng++; System.out.println("NG " + label + " 違う例外: " + t); }
        }
    }

    static List<String> names(List<Expense> list) {
        List<String> result = new ArrayList<>();
        for (Expense e : list) result.add(e.name());
        return result;
    }

    public static void main(String[] args) {
        List<Expense> data = List.of(
            new Expense("lunch",  "food",      800,  3),
            new Expense("book",   "study",     1500, 2),
            new Expense("coffee", "food",      300,  3),
            new Expense("train",  "transport", 500,  1),
            new Expense("dinner", "food",      1500, 1),
            new Expense("pen",    "study",     300,  3)
        );
        List<Expense> mutable = new ArrayList<>(data);

        System.out.println("===== 区画A：自動照合 =====");
        check("A-1 金額の高い順", names(byAmountDesc(data)), "[book, dinner, lunch, train, coffee, pen]");
        byAmountDesc(mutable);   // ここだけ、元のリストを見るためにわざと戻り値を使わない
        check("A-2 元のリストは変わらない", names(mutable), "[lunch, book, coffee, train, dinner, pen]");
        check("A-3 カテゴリ→日→金額", names(byCategoryThenDayDesc(data)), "[lunch, coffee, dinner, pen, book, train]");
        byCategoryThenDayDesc(mutable);   // 同上
        check("A-4 元のリストは変わらない", names(mutable), "[lunch, book, coffee, train, dinner, pen]");
        check("A-5 上位2件", topN(data, 2), "[book, dinner]");
        check("A-6 上位0件", topN(data, 0), "[]");
        check("A-7 件数より大きい n", topN(data, 10), "[book, dinner, lunch, train, coffee, pen]");
        check("A-8 空リスト", byAmountDesc(List.of()), "[]");
        checkThrows("A-9 byAmountDesc(null)", () -> byAmountDesc(null), IllegalArgumentException.class);
        checkThrows("A-10 byCategoryThenDayDesc(null)", () -> byCategoryThenDayDesc(null), IllegalArgumentException.class);
        checkThrows("A-11 topN(null, 1)", () -> topN(null, 1), IllegalArgumentException.class);
        checkThrows("A-12 topN(data, -1)", () -> topN(data, -1), IllegalArgumentException.class);
        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println();
        System.out.println("===== 区画B：実行して、理由を1行で =====");
        // B-1：高い順にしたつもり
        Comparator<Expense> c = Comparator.comparingInt(e -> e.amount());
        c.reversed();
        List<Expense> b1 = new ArrayList<>(data);
        b1.sort(c);
        System.out.println("B-1 " + names(b1));
        // reversedの戻り値を捨ててるから

        // B-2：「カテゴリ昇順、同じカテゴリの中は日の新しい順」にしたつもり
        Comparator<Expense> wrong = Comparator.comparing((Expense e) -> e.category())
                                              .thenComparingInt(e -> e.day())
                                              .reversed();
        List<Expense> b2 = new ArrayList<>(data);
        b2.sort(wrong);
        System.out.println("B-2 " + names(b2));
        // reversedが最後に結果を反対にしているから
    }
}
