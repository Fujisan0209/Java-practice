import java.util.*;

public class LibraryLab {
    static class Library {
        private final Map<String, Integer> copies = new LinkedHashMap<>();
        private final Map<String, Set<String>> borrowed = new HashMap<>();
        private final Deque<String> history = new ArrayDeque<>();

        public void addBook(String title, int count) {
            if(title == null || title.isBlank()) throw new IllegalArgumentException("title must not be blank");
            if(count <= 0) throw new IllegalArgumentException("count must be positive");

            if(copies.containsKey(title)) {
                copies.put(title, copies.get(title) + count);
            } else {
                copies.put(title, count);
            }
        }

        public int available(String title) {
            return copies.getOrDefault(title, 0);
        }

        public boolean borrow(String member, String title) {
            if(member == null || member.isBlank()) throw new IllegalArgumentException("member must not be blank");
            if(title == null || !copies.containsKey(title)) throw new IllegalArgumentException("title is not exist");
            
            Set<String> mine = borrowed.computeIfAbsent(member, k -> new TreeSet<>());
            if(mine.contains(title)) return false;
            if(copies.get(title) == 0) return false;

            mine.add(title);
            copies.put(title, copies.get(title) - 1);
            history.push("borrow " + member + " " + title);
            return true;
        }

        public void giveBack(String member, String title) {
            if(member == null) throw new IllegalArgumentException("member must not be null");
            if(title == null) throw new IllegalArgumentException("title must not be null");
            Set<String> mine = borrowed.get(member);
            if(!mine.contains(title)) throw new IllegalStateException("title is not containt");

            mine.remove(title);
            copies.put(title, copies.get(title) + 1);
            history.push("return " + member + " " + title);
        }

        public List<String> borrowedBy(String member) {
            if(member == null) throw new IllegalArgumentException("member must not be null");
            if(!(borrowed.containsKey(member))) return List.of();
            List<String> list = new ArrayList<>();
            Set<String> set = borrowed.get(member);
            for(String s : set) {
                list.add(s);
            }
            Collections.sort(list);
            return List.copyOf(list);
        }

        public List<String> recent(int n) {
            if(n < 0) throw new IllegalArgumentException("n must not be negative");

            List<String> list = new ArrayList<>();
            int count = 0;
            for(String s : history) {
                if(count == n) break;
                list.add(s);
                count++;
            }
            return list;
        }
    }
    
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        String e = String.valueOf(expected), a = String.valueOf(actual);
        if (e.equals(a)) { ok++; System.out.println(label + " OK : " + a); }
        else { ng++; System.out.println(label + " NG : 期待 " + e + " / 実際 " + a); }
    }

    static void checkThrows(String label, Class<? extends Exception> type, Runnable r) {
        try {
            r.run();
            ng++; System.out.println(label + " NG : 例外が出なかった（期待 " + type.getSimpleName() + "）");
        } catch (Exception ex) {
            if (type.isInstance(ex)) { ok++; System.out.println(label + " OK : " + ex.getClass().getSimpleName()); }
            else { ng++; System.out.println(label + " NG : 期待 " + type.getSimpleName() + " / 実際 " + ex.getClass().getSimpleName()); }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A：自動照合 ===");
        Library lib = new Library();

        lib.addBook("Java", 2);
        check("A-1 ", 2, lib.available("Java"));
        lib.addBook("Java", 1);
        check("A-2 ", 3, lib.available("Java"));
        check("A-3 ", 0, lib.available("Python"));

        boolean first = lib.borrow("ken", "Java");
        check("A-4 ", "true 2", first + " " + lib.available("Java"));
        boolean again = lib.borrow("ken", "Java");
        check("A-5 ", "false 2", again + " " + lib.available("Java"));

        lib.addBook("SQL", 1);
        boolean amy = lib.borrow("amy", "SQL");
        boolean kenSql = lib.borrow("ken", "SQL");
        check("A-6 ", "true false 0", amy + " " + kenSql + " " + lib.available("SQL"));

        lib.addBook("Algo", 1);
        lib.borrow("ken", "Algo");
        check("A-7 ", "[Algo, Java]", lib.borrowedBy("ken"));
        check("A-8 ", "[]", lib.borrowedBy("nobody"));

        lib.giveBack("ken", "Java");
        check("A-9 ", "3 [Algo]", lib.available("Java") + " " + lib.borrowedBy("ken"));
        checkThrows("A-10", IllegalStateException.class, () -> lib.giveBack("ken", "Java"));

        checkThrows("A-11", IllegalArgumentException.class, () -> lib.borrow("ken", "Python"));
        checkThrows("A-12", IllegalArgumentException.class, () -> lib.addBook("C", 0));
        checkThrows("A-13", IllegalArgumentException.class, () -> lib.borrow(null, "Java"));

        check("A-14", "[return ken Java, borrow ken Algo, borrow amy SQL]", lib.recent(3));
        check("A-15", 4, lib.recent(100).size());
        checkThrows("A-16", IllegalArgumentException.class, () -> lib.recent(-1));
        checkThrows("A-17", UnsupportedOperationException.class, () -> lib.borrowedBy("ken").add("X"));

        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B：実行して、理由を1行ずつ書く ===");
        // B-1：検査より先に変更すると？
        Map<String, Integer> stock = new HashMap<>(Map.of("Java", 2));
        Set<String> kens = new TreeSet<>(Set.of("Java"));
        stock.put("Java", stock.get("Java") - 1);   // 先に在庫を減らす
        boolean okB1 = kens.add("Java");             // あとで「もう借りてる？」を確認
        System.out.println("B-1: ok=" + okB1 + " stock=" + stock);
        // B-1 の理由： わからない　if(mine.contains(title)) のおかげ？
        // B-1 の理由：先に在庫を減らすと、そのあと「借りられない」と分かっても在庫だけ減ったまま戻らない。だから borrow では検査（29・30行目）を全部先に、変更（32〜34行目）を最後に書く
        
        // B-2：push でなく addLast で記録すると？
        Deque<String> d = new ArrayDeque<>();
        d.addLast("1st");
        d.addLast("2nd");
        d.addLast("3rd");
        System.out.println("B-2: " + d);
        // B-2 の理由（recent に使うと何が困るか）： 古い順になってしまう
    }
}
