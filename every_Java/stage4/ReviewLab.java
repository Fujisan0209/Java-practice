import java.util.*;

public class ReviewLab {

    record Budget(String category, int limit) implements Comparable<Budget> {
        Budget {
            if(category == null || category.isBlank()) throw new IllegalArgumentException("category must not be empty");
            if(limit < 0) throw new IllegalArgumentException("limit must be positive");
        }

        Budget withLimit(int newLimit) {
            if(newLimit < 0) throw new IllegalArgumentException("newLimit must be positive");
            return new Budget(category, newLimit);
        }

        @Override 
        public int compareTo(Budget other) {
            int c = Integer.compare(this.limit(), other.limit());
            if(c == 0) {
                return this.category().compareTo(other.category());
            } else {
                return c;
            }
        }
    }

    static <T extends Comparable<? super T>> T maxOf(List<? extends T> list) {
        if(list == null || list.isEmpty()) throw new IllegalArgumentException("list must not be blank");
        T max = list.get(0);
        for(int i = 1; i < list.size(); i++) {
            T a = list.get(i);
            if(max.compareTo(a) < 0) {
                max = a;
            }
        }
        return max;
    }

    // ===== ここから下は出題側が用意した main（変更しない） =====
    static int ok = 0, ng = 0;

    static void check(String id, Object expected, Object actual) {
        if (Objects.equals(String.valueOf(expected), String.valueOf(actual))) {
            System.out.println(id + " OK"); ok++;
        } else {
            System.out.println(id + " NG 期待=" + expected + " 実際=" + actual); ng++;
        }
    }

    static void checkThrows(String id, Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
            System.out.println(id + " NG 例外が出なかった"); ng++;
        } catch (Throwable t) {
            if (type.isInstance(t)) { System.out.println(id + " OK"); ok++; }
            else { System.out.println(id + " NG 期待=" + type.getSimpleName() + " 実際=" + t.getClass().getSimpleName()); ng++; }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A：自動照合 ===");
        Budget food = new Budget("food", 30000);
        check("A-1", "Budget[category=food, limit=30000]", food);
        check("A-2", true, food.equals(new Budget("food", 30000)));
        check("A-3", true, food.hashCode() == new Budget("food", 30000).hashCode());
        checkThrows("A-4", IllegalArgumentException.class, () -> new Budget(null, 100));
        checkThrows("A-5", IllegalArgumentException.class, () -> new Budget("   ", 100));
        checkThrows("A-6", IllegalArgumentException.class, () -> new Budget("food", -1));

        Budget lowered = food.withLimit(500);
        check("A-7", "Budget[category=food, limit=500]", lowered);
        check("A-8", 30000, food.limit());
        checkThrows("A-9", IllegalArgumentException.class, () -> food.withLimit(-5));

        check("A-10", true, new Budget("train", 100).compareTo(new Budget("food", 200)) < 0);
        check("A-11", true, new Budget("food", 100).compareTo(new Budget("fun", 100)) < 0);

        check("A-12", 9, maxOf(List.of(3, 9, 4)));
        check("A-13", "pear", maxOf(List.of("apple", "pear", "fig")));
        List<Budget> budgets = List.of(
                new Budget("food", 30000),
                new Budget("train", 10000),
                new Budget("fun", 30000));
        check("A-14", "Budget[category=fun, limit=30000]", maxOf(budgets));
        checkThrows("A-15", IllegalArgumentException.class, () -> maxOf(List.<Integer>of()));
        checkThrows("A-16", IllegalArgumentException.class, () -> maxOf(null));

        System.out.println("A: OK=" + ok + " NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B：実行して理由を1行 ===");
        Budget b = new Budget("food", 30000);
        b.withLimit(500);
        System.out.println("B-1: " + b);
        // withLimitはbを変えるのではない。戻り値を捨ててる

        Set<Budget> set = new HashSet<>();
        set.add(new Budget("food", 30000));
        set.add(new Budget("food", 30000));
        System.out.println("B-2: size=" + set.size());
        // setなので重複は消える
        // record は全フィールドで比べる equals と hashCode を自動で作るので、別々に new した2つでも「同じ」と判断され、2回目の add は入らない
    }
}