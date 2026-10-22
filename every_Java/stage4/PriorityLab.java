
import java.util.*;

record Task(String name, int priority) {
    Task {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("name must not be brank");
        if(priority < 1 || 5 < priority) throw new IllegalArgumentException("priority must be 1 ~ 5");
    }

    @Override 
    public String toString() {
        return name + "(" + priority + ")";
    }
}

public class PriorityLab {
    public static PriorityQueue<Task> byPriority(List<Task> tasks) {
        if(tasks == null) throw new IllegalArgumentException("tasks must not be null");
        PriorityQueue<Task> pq = new PriorityQueue<>(
            Comparator.comparingInt(Task::priority)
                      .thenComparing(Task::name)
        );
        for(Task t : tasks) {
            pq.add(t);
        }
        return pq;
    }

    public static List<String> drainInOrder(PriorityQueue<Task> q) {
        List<String> nameList = new ArrayList<>();
        while(true) {
            if(q.isEmpty()) break;
            Task t = q.poll();
            nameList.add(t.name());
        }
        return nameList;
    }

    // ===== ここから下は用意済み（触らなくてよい） =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + expected + "  実際=" + actual); }
    }
    static void checkThrows(String label, Runnable r) {
        try { r.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (IllegalArgumentException e) { ok++; System.out.println("OK " + label + "  -> " + e.getMessage()); }
    }

    public static void main(String[] args) {
        List<Task> tasks = List.of(
            new Task("wash", 3), new Task("call", 1), new Task("cook", 2),
            new Task("bath", 3), new Task("mail", 1));

        System.out.println("== A 動作確認 ==");
        check("A-1 toString", "call(1)", new Task("call", 1).toString());
        PriorityQueue<Task> q = byPriority(tasks);
        check("A-2 件数", 5, q.size());
        check("A-3 peek は先頭を見るだけ", "call", q.peek().name());
        check("A-4 peek しても減らない", 5, q.size());
        check("A-5 取り出し順", List.of("call", "mail", "cook", "bath", "wash"), drainInOrder(q));
        check("A-6 空リスト", List.of(), drainInOrder(byPriority(List.of())));

        System.out.println("== B 実行して、出力の理由を1行で ==");
        PriorityQueue<Task> q2 = byPriority(tasks);
        System.out.println("B-1 そのまま表示: " + q2);
        List<String> first = drainInOrder(q2);
        List<String> second = drainInOrder(q2);
        System.out.println("B-2 1回目: " + first + " / 2回目: " + second);

        System.out.println("== C 例外（5行出るはず） ==");
        checkThrows("C-1 名前 null", () -> new Task(null, 1));
        checkThrows("C-2 名前 空白だけ", () -> new Task("  ", 1));
        checkThrows("C-3 優先度 0", () -> new Task("x", 0));
        checkThrows("C-4 優先度 6", () -> new Task("x", 6));
        checkThrows("C-5 リスト null", () -> byPriority(null));

        System.out.println("OK=" + ok + " NG=" + ng);
    }
}
