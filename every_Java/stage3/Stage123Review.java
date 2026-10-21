import java.util.*;

record Item(String name, int price) implements Comparable<Item> {
    public Item {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("name must not be null");
        if(price < 0) throw new IllegalArgumentException("price must be positive");
     }
     
    @Override
    public int compareTo(Item other) {
        int result = Integer.compare(this.price, other.price);
        if(result != 0) {
            return result;
        } else {
            return this.name.compareTo(other.name);
        }
    }
}

public class Stage123Review {
    // ★ここに ② total と ③ maxOf を書く
    static int total(Item... items) {
        if(items == null) throw new IllegalArgumentException("items must not be null");
        for(Item i : items) {
            if(i == null) throw new IllegalArgumentException("item must not be null");
        }

        // if(items.length == 0) return 0; 
        int sum = 0;
        for(Item i : items) {
            sum = Math.addExact(sum, i.price());
        }
        return sum;
    }

    static <T extends Comparable<T>> T maxOf(List<? extends T> list) {
        if(list == null || list.isEmpty()) throw new IllegalArgumentException("list must not be null");

        T max = list.get(0);
        for(int i = 1; i < list.size(); i++) {
            int c = max.compareTo(list.get(i));
            if(c < 0) {
                max = list.get(i);
            }//  else {
            //     continue;
            // }
        }
        return max;
    }


    // ===== ここから下は用意済み（書き換えない） =====
    static void check(String label, Object expected, Object actual) {
        System.out.println((Objects.equals(expected, actual) ? "OK " : "NG ") + label
                + "  期待: " + expected + " / 実際: " + actual);
    }
    static void checkThrows(String label, Class<? extends Exception> type, Runnable r) {
        try {
            r.run();
            System.out.println("NG " + label + "  例外が出なかった");
        } catch (Exception e) {
            System.out.println((type.isInstance(e) ? "OK " : "NG ") + label
                    + "  " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
    static void addX(StringBuilder sb) {
        sb.append("x");
        sb = new StringBuilder("y");
    }
    static String kind(Object o) { return "Object"; }
    static String kind(String s) { return "String"; }

    public static void main(String[] args) {
        Item pen = new Item("pen", 120);
        Item note = new Item("note", 300);
        Item eraser = new Item("eraser", 120);

        System.out.println("--- A ---");
        check("A-1 total 3件", 540, total(pen, note, eraser));
        check("A-2 total 0件", 0, total());
        check("A-3 maxOf Item", note, maxOf(List.of(pen, note, eraser)));
        check("A-4 maxOf 同じ値段は名前順で後ろ", pen, maxOf(List.of(pen, eraser)));
        check("A-5 maxOf Integer", 9, maxOf(List.of(3, 9, 4)));
        check("A-6 maxOf String", "pear", maxOf(List.of("apple", "pear", "fig")));
        List<Item> sorted = new ArrayList<>(List.of(note, pen, eraser));
        Collections.sort(sorted);
        check("A-7 sort", List.of(eraser, pen, note), sorted);

        System.out.println("--- B（実行して、理由を // の後に1行） ---");
        StringBuilder sb = new StringBuilder("ab");
        addX(sb);
        System.out.println("B-1 " + sb);                
        // 理由:コピーされた値にnewできない
        // append は中身の書き換えなので届く（x が付く）。sb = new ... は仮引数への入れ直しなので届かない（y にならない）
        Integer a = 127, b = 127, c = 128, d = 128;
        System.out.println("B-2 " + (a == b) + " " + (c == d)); 
        // 理由: 128からは別
        // Integer は −128〜127 だけ使い回す（キャッシュ）。128 は毎回別の実体になるので、==（同じ実体か？）は false
        Object s = "hi";
        System.out.println("B-3 " + kind(s) + " " + s.getClass().getSimpleName()); 
        // 理由: なんで？
        // オーバーロードは変数の型で、コンパイル時に決まる。オーバーライドと getClass は実物で、実行時に決まる
        Set<Item> set = new HashSet<>();
        set.add(new Item("pen", 100));
        set.add(new Item("pen", 100));
        System.out.println("B-4 " + set.size());         
        // 理由: 重複は追加されない
        // record が equals と hashCode を自動で作るので、中身が同じ2つは同じものとして扱われます
        List<String> x = new ArrayList<>();
        List<Integer> y = new ArrayList<>();
        System.out.println("B-5 " + (x.getClass() == y.getClass())); 
        // 理由: どちらもArrayList
        // 理由は型消去で、<String> や <Integer> は実行時には消えているので、どちらもただの ArrayList になります
        List<? super Integer> box = new ArrayList<Number>();
        box.add(5);
        Object got = box.get(0);
        System.out.println("B-6 " + got.getClass().getSimpleName()); 
        // 理由: 中身はInteger
        // 変数の型は Object でも、getClass() は実物を答えます

        System.out.println("--- C ---");
        checkThrows("C-1 name が空白", IllegalArgumentException.class, () -> new Item("  ", 100));
        checkThrows("C-2 price が負", IllegalArgumentException.class, () -> new Item("pen", -1));
        checkThrows("C-3 total(null 配列)", IllegalArgumentException.class, () -> total((Item[]) null));
        checkThrows("C-4 total(要素に null)", IllegalArgumentException.class, () -> total(pen, null));
        checkThrows("C-5 total が int を超える", ArithmeticException.class,
                () -> total(new Item("a", Integer.MAX_VALUE), new Item("b", 1)));
        checkThrows("C-6 maxOf(空)", IllegalArgumentException.class, () -> maxOf(List.<Integer>of()));
    }
}