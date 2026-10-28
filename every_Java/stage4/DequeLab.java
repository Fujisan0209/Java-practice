import java.util.*;

public class DequeLab {
    private String current;
    private final Deque<String> backStack = new ArrayDeque<>();
    private final Deque<String> forwardStack = new ArrayDeque<>();

    public DequeLab(String home) {
        if(home == null || home.isBlank()) throw new IllegalArgumentException("home must not be blank");
        current = home;
    }

    public String current() { return current; }

    public void visit(String url) {
        if(url == null || url.isBlank()) throw new IllegalArgumentException("url must not be blank");
        if(!url.equals(current)) {
            backStack.push(current);
            current = url;
            forwardStack.clear();
        }
    }

    public String back() {
        if(backStack.isEmpty()) throw new IllegalStateException("backStack is empty");
        forwardStack.push(current);
        current = backStack.pop();
        return current;
    }

    public String forward() {
        if(forwardStack.isEmpty()) throw new IllegalStateException("forwardStack is empty");
        backStack.push(current);
        current = forwardStack.pop();
        return current;
    }

    public List<String> backList() {
        List<String> list = new ArrayList<>();
        for(String s : backStack) {
            list.add(s);
        }
        return List.copyOf(list);
    }

    // ===== ここから下は main（このまま貼る） =====
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        if (java.util.Objects.equals(expected, actual)) { ok++; System.out.println("OK  " + label); }
        else { ng++; System.out.println("NG  " + label + "  expected=" + expected + "  actual=" + actual); }
    }

    static void checkThrows(String label, Class<? extends Throwable> type, Runnable r) {
        try { r.run(); ng++; System.out.println("NG  " + label + "  例外が出なかった"); }
        catch (Throwable t) {
            if (type.isInstance(t)) { ok++; System.out.println("OK  " + label + "  (" + t.getClass().getSimpleName() + ")"); }
            else { ng++; System.out.println("NG  " + label + "  expected=" + type.getSimpleName() + "  actual=" + t.getClass().getSimpleName()); }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 区画A ---");
        DequeLab b = new DequeLab("home");
        check("A-1 最初は home", "home", b.current());
        b.visit("news");
        b.visit("sports");
        check("A-2 visit で current が変わる", "sports", b.current());
        check("A-3 back は新しい current を返す", "news", b.back());
        check("A-4 もう一度 back", "home", b.back());
        check("A-5 forward は新しい current を返す", "news", b.forward());
        b.visit("news");
        check("A-6 今と同じ URL は無視（戻るが増えない）", List.of("home"), b.backList());
        b.visit("weather");
        checkThrows("A-7 visit すると進む先は消える", IllegalStateException.class, () -> b.forward());
        check("A-8 backList は新しい順", List.of("news", "home"), b.backList());

        DequeLab fresh = new DequeLab("top");
        checkThrows("A-9 最初は戻れない", IllegalStateException.class, () -> fresh.back());
        check("A-10 失敗しても current はそのまま", "top", fresh.current());
        checkThrows("A-11 null のホームは拒否", IllegalArgumentException.class, () -> new DequeLab(null));
        checkThrows("A-12 空白だけの URL は拒否", IllegalArgumentException.class, () -> fresh.visit("   "));
        checkThrows("A-13 backList は書き換え不可", UnsupportedOperationException.class, () -> b.backList().add("x"));

        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("--- 区画B（実行して、理由を1行） ---");
        DequeLab c = new DequeLab("a");
        c.visit("b");
        c.visit("c");
        c.visit("d");
        System.out.println("B-1 backList = " + c.backList());
        // dはcurrent、出力は新しい順だからc,b,a
        List<String> photo = c.backList();
        c.back();
        System.out.println("B-2 photo = " + photo + " / 今の backList = " + c.backList());
        // photoはB-1、今は一度backしたのでcはcurrentに、出力はb,aになる
    }
}