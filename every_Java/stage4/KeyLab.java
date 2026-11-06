import java.util.*;

public class KeyLab {
    static final class Point {
        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x() { return x; }

        int y() { return y; }

        Point withX(int newX) { return new Point(newX, y); }

        @Override 
        public boolean equals(Object o) {
            if(this == o) return true;
            if(!(o instanceof Point other)) return false;
            // if(x == other.x() && y == other.y()) return true;
            // return false;
            // 1行で書ける
            return x == other.x() && y == other.y();
        }

        @Override 
        public String toString() {
            return "(" + this.x() + ", " + this.y() + ")"; 
        }

        @Override 
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
    
    static Map<Point, Integer> countVisits(List<Point> route) {
        if(route == null) throw new IllegalArgumentException("route must not be null");
        for(Point r : route) {
            if(r == null) throw new IllegalArgumentException("route contents must not be null");
        }

        Map<Point, Integer> map = new LinkedHashMap<>();
        for(Point p : route) {
            if(map.containsKey(p)) {
                map.put(p, map.get(p) + 1);
            } else {
                map.put(p, 1);
            }
        }
        return map;
    }

    // ===== 区画B 用（さわらない） =====
    static final class BadPoint {
        private final int x;
        private final int y;
        BadPoint(int x, int y) { this.x = x; this.y = y; }
        @Override
        public String toString() { return "(" + x + ", " + y + ")"; }
    }

    static final class MovablePoint {
        int x;
        int y;
        MovablePoint(int x, int y) { this.x = x; this.y = y; }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MovablePoint other)) return false;
            return x == other.x && y == other.y;
        }
        @Override
        public int hashCode() { return Objects.hash(x, y); }
        @Override
        public String toString() { return "(" + x + ", " + y + ")"; }
    }

    // ===== 自動照合 =====
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        String e = String.valueOf(expected), a = String.valueOf(actual);
        if (e.equals(a)) { ok++; System.out.println("OK " + label + " : " + a); }
        else { ng++; System.out.println("NG " + label + " : 期待=" + e + " 実際=" + a); }
    }

    static void checkThrows(String label, Runnable r) {
        try {
            r.run();
            ng++; System.out.println("NG " + label + " : 例外が出なかった");
        } catch (IllegalArgumentException ex) {
            ok++; System.out.println("OK " + label + " : IAE");
        } catch (RuntimeException ex) {
            ng++; System.out.println("NG " + label + " : 別の例外 " + ex);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A：Point と countVisits ===");
        Point a = new Point(1, 2);
        Point b = new Point(1, 2);
        Point c = new Point(2, 1);

        check("A-1 toString", "(1, 2)", a);
        check("A-2 自分自身と equals", true, a.equals(a));
        check("A-3 同じ座標の別実体と equals", true, a.equals(b));
        check("A-4 == は別実体なので false", false, a == b);
        check("A-5 x と y が逆なら equals は false", false, a.equals(c));
        check("A-6 null と equals は false（例外を出さない）", false, a.equals(null));
        check("A-7 別の型と equals は false", false, a.equals("(1, 2)"));
        check("A-8 equals が true なら hashCode も同じ", true, a.hashCode() == b.hashCode());

        Point moved = a.withX(5);
        check("A-9 withX は新しい Point を返す", "(5, 2)", moved);
        check("A-10 withX の後も元の a は変わらない", "(1, 2)", a);

        Set<Point> set = new HashSet<>();
        set.add(a);
        set.add(b);
        check("A-11 HashSet に同じ座標を2回 add → 1個", 1, set.size());

        Map<Point, String> names = new HashMap<>();
        names.put(new Point(0, 0), "start");
        check("A-12 HashMap を別実体のキーで get", "start", names.get(new Point(0, 0)));

        List<Point> route = List.of(new Point(0, 0), new Point(1, 0), new Point(0, 0),
                                    new Point(1, 1), new Point(1, 0), new Point(0, 0));
        check("A-13 countVisits（初登場順・回数）", "{(0, 0)=3, (1, 0)=2, (1, 1)=1}", countVisits(route));
        check("A-14 countVisits 空リスト", "{}", countVisits(List.of()));
        checkThrows("A-15 countVisits(null) は IAE", () -> countVisits(null));
        List<Point> withNull = new ArrayList<>();
        withNull.add(new Point(0, 0));
        withNull.add(null);
        checkThrows("A-16 null の要素が混ざっていたら IAE", () -> countVisits(withNull));

        System.out.println("区画A 結果: OK=" + ok + " NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B：実行して、理由を1行ずつ書く ===");
        Set<BadPoint> badSet = new HashSet<>();
        badSet.add(new BadPoint(1, 2));
        badSet.add(new BadPoint(1, 2));
        System.out.println("B-1 BadPoint を2回 add した size = " + badSet.size());
        // B-1 の理由：equals と hashCode を書いていないので Object のもの（同じ実体かどうか）が使われ、new した2個は別物として扱われるから

        Set<MovablePoint> moveSet = new HashSet<>();
        MovablePoint m = new MovablePoint(1, 2);
        moveSet.add(m);
        m.x = 5;
        System.out.println("B-2 中身 = " + moveSet + " / contains(m) = " + moveSet.contains(m));
        // B-2 の理由：add した時点の (1, 2) の hashCode で棚が決まったが、x を 5 に変えると hashCode が変わり、contains は別の棚を探して見つからないから
    }
}
