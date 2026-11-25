import java.util.*;

public class ResourceLab {
    static class Gate implements AutoCloseable {
        private final String name;
        private final List<String> log;
        private final boolean failOnClose;

        public Gate(String name, List<String> log, boolean failOnClose) {
            if(name == null) throw new IllegalArgumentException("name must not be null");
            if(log == null) throw new IllegalArgumentException("log must not be null");

            this.name = name;
            this.log = log;
            this.failOnClose = failOnClose;
            log.add("open " + name);
        }

        @Override
        public void close() {
            log.add("close " + name);
            if(failOnClose) throw new IllegalStateException("close failed: " + name);
        }
    }

    static void useTwo(List<String> log) {
        try(Gate a = new Gate("A", log, false);
            Gate b = new Gate("B", log, false);) {
            log.add("work");
        }
    }

    static String runAndReport(List<String> log, boolean failWork, boolean failClose) {
        try(Gate c = new Gate("C", log, failClose)) {
            if(failWork) throw new IllegalArgumentException("work failed");
            if(!failWork) log.add("work");
            return "ok";
        } catch(RuntimeException e) {
            return e.getMessage() + " / suppressed=" + e.getSuppressed().length;
        }
    }

    // ===== 区画B 用（さわらない） =====
    @SuppressWarnings("finally")
    static int finallyReturn() {
        try {
            throw new IllegalStateException("lost");
        } finally {
            return 1;
        }
    }

    static String oldStyle(List<String> log) {
        try {
            Gate d = new Gate("D", log, true);
            try {
                throw new IllegalArgumentException("work failed");
            } finally {
                d.close();
            }
        } catch (RuntimeException e) {
            return e.getMessage() + " / suppressed=" + e.getSuppressed().length;
        }
    }

    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + "  期待=" + expected + "  実際=" + actual); }
    }
    static void checkThrows(String label, Class<? extends Throwable> kind, Runnable body) {
        try { body.run(); ng++; System.out.println("NG " + label + "  例外が出なかった"); }
        catch (Throwable t) {
            if (kind.isInstance(t)) { ok++; System.out.println("OK " + label); }
            else { ng++; System.out.println("NG " + label + "  期待=" + kind.getSimpleName() + "  実際=" + t.getClass().getSimpleName()); }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 区画A ===");
        List<String> log1 = new ArrayList<>();
        Gate g = new Gate("X", log1, false);
        check("A-1 作った時点で open X が記録される", List.of("open X"), log1);
        g.close();
        check("A-2 close で close X が記録される", List.of("open X", "close X"), log1);

        List<String> log2 = new ArrayList<>();
        Gate bad = new Gate("Y", log2, true);
        checkThrows("A-3 failOnClose=true の close は ISE", IllegalStateException.class, bad::close);
        check("A-4 失敗しても close の記録は先に残る", List.of("open Y", "close Y"), log2);

        checkThrows("A-5 name が null は IAE", IllegalArgumentException.class, () -> new Gate(null, new ArrayList<>(), false));
        checkThrows("A-6 log が null は IAE", IllegalArgumentException.class, () -> new Gate("Z", null, false));

        List<String> log3 = new ArrayList<>();
        useTwo(log3);
        check("A-7 useTwo は開けた逆順で閉じる", List.of("open A", "open B", "work", "close B", "close A"), log3);

        List<String> log4 = new ArrayList<>();
        check("A-8 処理も close も成功", "ok", runAndReport(log4, false, false));
        check("A-9 成功時の記録", List.of("open C", "work", "close C"), log4);

        List<String> log5 = new ArrayList<>();
        check("A-10 処理だけ失敗", "work failed / suppressed=0", runAndReport(log5, true, false));
        check("A-11 処理が失敗しても close は呼ばれる", List.of("open C", "close C"), log5);

        check("A-12 close だけ失敗", "close failed: C / suppressed=0", runAndReport(new ArrayList<>(), false, true));
        check("A-13 両方失敗＝処理の例外が主役・close の例外は抑制", "work failed / suppressed=1", runAndReport(new ArrayList<>(), true, true));

        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("=== 区画B（1行ずつコメントを外して実行→理由を1行） ===");
        System.out.println("B-1 " + finallyReturn());
        System.out.println("B-2 " + oldStyle(new ArrayList<>()));
        // B-1 の理由： finally の return が最後に実行され、try で投げた例外を捨てて 1 を返してしまうから
        // B-2 の理由： 手書きの finally で close が例外を投げると、それが work failed を上書きして主役になり、本当の原因が消えるから
    }
}
