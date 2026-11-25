import java.util.*;

public class ValidateLab {
    record Expense(String name, String category, int amount) {
        Expense {
            Objects.requireNonNull(name, "name is null");
            Objects.requireNonNull(category, "category is null");
            if(name.isBlank()) throw new IllegalArgumentException("name is blank");
            if(category.isBlank()) throw new IllegalArgumentException("category is blank");
            if(amount <= 0) throw new IllegalArgumentException("amount must be positive: " + amount);
        }
    }

    record Loose(String name, int amount) {}

    static class Wallet {
        private final int budget;
        private int spent = 0;
        private List<Expense> history = new ArrayList<>();
        
        public Wallet(int budget) {
            if(budget < 0) throw new IllegalArgumentException("budget must be >= 0: " + budget);
            this.budget = budget;
        }

        void spend(Expense e) {
            Objects.requireNonNull(e, "expense is null");
            if(budget - (this.spent + e.amount()) < 0) throw new IllegalStateException("budget exceeded");
            this.spent += e.amount();
            history.add(e);
        }

        /*void spend(Expense e) {
            Objects.requireNonNull(e, "expense is null");
            this.spent += e.amount();
            if(spent > budget) throw new IllegalStateException("budget exceeded");
            
            history.add(e);
        }*/

        int remaining() { return budget - spent; }

        List<Expense> history() { return List.copyOf(history); }
    }

    static Expense nth(List<Expense> list, int i) {
        Objects.requireNonNull(list, "list is null");
        Objects.checkIndex(i, list.size());
        return list.get(i);
    }

    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + " 期待=" + expected + " 実際=" + actual); }
    }

    static void checkThrows(String label, Class<? extends Throwable> type, String message, Runnable r) {
        try {
            r.run();
            ng++; System.out.println("NG " + label + " 例外が出なかった");
        } catch (Throwable t) {
            boolean typeOk = type.isInstance(t);
            boolean msgOk = message == null || message.equals(t.getMessage());
            if (typeOk && msgOk) { ok++; System.out.println("OK " + label); }
            else {
                ng++;
                System.out.println("NG " + label + " 期待=" + type.getSimpleName() + "(" + message + ")"
                        + " 実際=" + t.getClass().getSimpleName() + "(" + t.getMessage() + ")");
            }
        }
    }

    public static void main(String[] args) {
        // --- Expense の入口チェック ---
        Expense lunch = new Expense("lunch", "food", 800);
        check("A-1 正しい値は作れる", "lunch", lunch.name());
        checkThrows("A-2 name が null", NullPointerException.class, "name is null",
                () -> new Expense(null, "food", 800));
        checkThrows("A-3 category が null", NullPointerException.class, "category is null",
                () -> new Expense("lunch", null, 800));
        checkThrows("A-4 name が空白だけ", IllegalArgumentException.class, "name is blank",
                () -> new Expense("  ", "food", 800));
        checkThrows("A-5 category が空文字", IllegalArgumentException.class, "category is blank",
                () -> new Expense("lunch", "", 800));
        checkThrows("A-6 amount が 0", IllegalArgumentException.class, "amount must be positive: 0",
                () -> new Expense("lunch", "food", 0));
        checkThrows("A-7 amount が負", IllegalArgumentException.class, "amount must be positive: -5",
                () -> new Expense("lunch", "food", -5));
        check("A-8 1円は作れる", 1, new Expense("gum", "food", 1).amount());

        // --- Wallet ---
        checkThrows("A-9 予算が負", IllegalArgumentException.class, "budget must be >= 0: -1",
                () -> new Wallet(-1));
        Wallet w = new Wallet(1000);
        w.spend(lunch);
        check("A-10 使ったら残りが減る", 200, w.remaining());
        checkThrows("A-11 予算オーバーは ISE", IllegalStateException.class, "budget exceeded",
                () -> w.spend(new Expense("book", "hobby", 300)));
        check("A-12 失敗した spend は残りを変えない", 200, w.remaining());
        check("A-13 失敗した spend は履歴に残らない", 1, w.history().size());
        w.spend(new Expense("coffee", "food", 200));
        check("A-14 ちょうど0円まで使える", 0, w.remaining());
        checkThrows("A-15 spend(null)", NullPointerException.class, "expense is null",
                () -> w.spend(null));
        checkThrows("A-16 履歴は外から書き換えられない", UnsupportedOperationException.class, null,
                () -> w.history().clear());

        // --- nth ---
        List<Expense> list = List.of(lunch, new Expense("bus", "daily", 220));
        check("A-17 1番目", "bus", nth(list, 1).name());
        checkThrows("A-18 範囲外の位置", IndexOutOfBoundsException.class, null,
                () -> nth(list, 2));
        checkThrows("A-19 負の位置", IndexOutOfBoundsException.class, null,
                () -> nth(list, -1));
        checkThrows("A-20 list が null", NullPointerException.class, "list is null",
                () -> nth(null, 0));

        System.out.println("---- OK=" + ok + " NG=" + ng);

        // ===== 区画B：実行して、理由を1行で =====
        // B-1：チェックなしの record に null を入れると、どこで落ちる？
        //   （ValidateLab の中に record Loose(String name, int amount) {} を足して、下の3行を動かす）
        // Loose x = new Loose(null, 500);
        // System.out.println("作れた");
        // System.out.println(x.name().length());
        // 理由：null のまま作れてしまい、原因（null を入れた行）から離れた「使った行」でやっと NPE になる。入口で止めればその場で分かる

        // B-2：spend の中を「先に spent を増やしてから、spent > budget なら ISE」の順に変えて、A-12 を見る
        // 理由：先に足すと、ISEを投げても、追加されてしまうから
    }
}