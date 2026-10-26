import java.util.*;

class Roster {
    private final List<String> names = new ArrayList<>();

    void add(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        names.add(name);
    }

    List<String> viewNames() {
        return Collections.unmodifiableList(names);
    }

    List<String> snapshot() {
        return List.copyOf(names);
    }
}

public class FreezeLab {
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) {
            ok++;
            System.out.println("OK " + label + " : " + actual);
        } else {
            ng++;
            System.out.println("NG " + label + " : 期待=" + expected + " 実際=" + actual);
        }
    }

    static void checkThrows(String label, Class<? extends Throwable> expected, Runnable r) {
        try {
            r.run();
            ng++;
            System.out.println("NG " + label + " : 例外が出なかった");
        } catch (Throwable e) {
            if (expected.isInstance(e)) {
                ok++;
                System.out.println("OK " + label + " : " + e.getClass().getSimpleName());
            } else {
                ng++;
                System.out.println("NG " + label + " : 期待=" + expected.getSimpleName()
                        + " 実際=" + e.getClass().getSimpleName());
            }
        }
    }

    public static void main(String[] args) {
        // ===== 区画A：動作確認（自動照合） =====
        Roster r = new Roster();
        r.add("佐藤");
        r.add("鈴木");

        List<String> view = r.viewNames();
        List<String> snap = r.snapshot();
        check("A-1 view の中身", List.of("佐藤", "鈴木"), view);
        check("A-2 snap の中身", List.of("佐藤", "鈴木"), snap);

        r.add("高橋");   // ← view と snap を取った「後」に1人増やす
        check("A-3 view は後から増えた人も見える", 3, view.size());
        check("A-4 snap は取った時点のまま", 2, snap.size());

        checkThrows("A-5 view に add できない", UnsupportedOperationException.class,
                () -> view.add("田中"));
        checkThrows("A-6 snap に add できない", UnsupportedOperationException.class,
                () -> snap.add("田中"));
        check("A-7 外から add を試みても中身は3人のまま", 3, r.viewNames().size());

        // ===== 区画C：例外 =====
        checkThrows("C-1 null", IllegalArgumentException.class, () -> r.add(null));
        checkThrows("C-2 空文字", IllegalArgumentException.class, () -> r.add(""));
        checkThrows("C-3 空白だけ", IllegalArgumentException.class, () -> r.add("   "));
        check("C-4 失敗した add のあとも3人のまま", 3, r.snapshot().size());

        System.out.println("---- OK=" + ok + " NG=" + ng + " ----");

        // ===== 区画B：実行して、出た結果の「理由」を1行で書く =====
        // B-1
        try {
            List<String> b1 = List.of("a", null);
            System.out.println("B-1 " + b1);
        } catch (Exception e) {
            System.out.println("B-1 " + e.getClass().getSimpleName());
        }
        // 要素にnullが入っているから

        // B-2
        try {
            Collections.sort(r.viewNames());
            System.out.println("B-2 並べ替えできた");
        } catch (Exception e) {
            System.out.println("B-2 " + e.getClass().getSimpleName());
        }
        // 窓から変更しようとしたから
    }
}