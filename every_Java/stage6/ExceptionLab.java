import java.util.*;

public class ExceptionLab {
    record Expense(String name, String category, int amount) {}

    static class BadLineException extends Exception {
        public BadLineException(String message) {
            super(message);
        }

        public BadLineException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    static Expense parseLine(String line) throws BadLineException {
        if(line == null) throw new IllegalArgumentException("line must not be null");

        String[] parts = line.split(",");
        if(parts.length != 3) throw new BadLineException("wrong column count: " + line);

        int amount;
        try {
            amount = Integer.parseInt(parts[2].strip());
        } catch (NumberFormatException e) {
            throw new BadLineException("amount is not a number: " + line, e);
        }

        if(amount < 0) throw new BadLineException("amount is negative: " + line);

        String name = parts[0].strip();
        String category = parts[1].strip();

        return new Expense(name, category, amount);
    }

    static List<Expense> parseAll(List<String> lines) throws BadLineException {
        if(lines == null) throw new IllegalArgumentException("lines must not be null");

        List<Expense> result = new ArrayList<>();

        for(String s : lines) {
            result.add(parseLine(s));
        }
        return result;
    }

    static int totalSkippingBad(List<String> lines, List<String> errors) {
        if(lines == null) throw new IllegalArgumentException("lines must not be null");
        if(errors == null) throw new IllegalArgumentException("errors must not be null");

        int sum = 0;
        for(String s : lines) {
            try {
                Expense ex = parseLine(s);
                sum += ex.amount();
            } catch (BadLineException e) {
                errors.add(e.getMessage());
            }
        }
        return sum;
    }

    // ======== ここから下はそのまま貼る（出題側で用意した main） ========
    interface Body { Object run() throws Exception; }

    static String result(Body b) {
        try { return String.valueOf(b.run()); }
        catch (Exception e) { return e.getClass().getSimpleName() + ": " + e.getMessage(); }
    }

    static String kind(Body b) {
        try { b.run(); return "no exception"; }
        catch (Exception e) { return e.getClass().getSimpleName(); }
    }

    static int ok = 0, ng = 0;
    static void check(String label, String expected, String actual) {
        if (expected.equals(actual)) { ok++; System.out.println(label + " OK"); }
        else { ng++; System.out.println(label + " NG  期待: " + expected + "  実際: " + actual); }
    }

    public static void main(String[] args) {
        System.out.println("===== 区画A：自動照合 =====");
        check("A-1", "Expense[name=lunch, category=food, amount=800]", result(() -> parseLine("lunch,food,800")));
        check("A-2", "Expense[name=coffee, category=food, amount=300]", result(() -> parseLine(" coffee , food , 300 ")));
        check("A-3", "Expense[name=book, category=hobby, amount=0]", result(() -> parseLine("book,hobby,0")));
        check("A-4", "BadLineException: wrong column count: lunch,food", result(() -> parseLine("lunch,food")));
        check("A-5", "BadLineException: amount is not a number: lunch,food,abc", result(() -> parseLine("lunch,food,abc")));
        check("A-6", "NumberFormatException", result(() -> {
            try { parseLine("lunch,food,abc"); return "no exception"; }
            catch (BadLineException e) { return e.getCause() == null ? "cause なし" : e.getCause().getClass().getSimpleName(); }
        }));
        check("A-7", "BadLineException: amount is negative: lunch,food,-5", result(() -> parseLine("lunch,food,-5")));
        check("A-8", "IllegalArgumentException", kind(() -> parseLine(null)));

        check("A-9", "[Expense[name=a, category=food, amount=100], Expense[name=b, category=daily, amount=200]]",
                result(() -> parseAll(List.of("a,food,100", "b,daily,200"))));
        check("A-10", "[]", result(() -> parseAll(List.of())));
        check("A-11", "BadLineException: wrong column count: bad",
                result(() -> parseAll(List.of("a,food,100", "bad", "c,food,x"))));
        check("A-12", "IllegalArgumentException", kind(() -> parseAll(null)));

        List<String> errors = new ArrayList<>();
        int total = totalSkippingBad(List.of("a,food,100", "bad", "b,food,x", "c,daily,50"), errors);
        check("A-13", "150", String.valueOf(total));
        check("A-14", "[wrong column count: bad, amount is not a number: b,food,x]", errors.toString());
        check("A-15", "0", result(() -> totalSkippingBad(List.of(), new ArrayList<>())));
        check("A-16", "IllegalArgumentException",
                kind(() -> totalSkippingBad(Arrays.asList("a,food,100", null), new ArrayList<>())));
        check("A-17", "IllegalArgumentException", kind(() -> totalSkippingBad(null, new ArrayList<>())));
        check("A-18", "IllegalArgumentException", kind(() -> totalSkippingBad(List.of("a,food,100"), null)));
        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println("===== 区画B：実行→理由1行 =====");
        BadLineException noCause = new BadLineException("amount is not a number: x");
        System.out.println("B-1 cause = " + noCause.getCause());
        // B-1：原因を渡していないので、元の例外（なぜ失敗したか）が失われる

        // B-2：下の2行のコメントを1行ずつ外して javac してみる（試したら戻す）
        // int n = Integer.parseInt("abc");
        // Expense x = parseLine("a,b,1");
        // B-2：BadLineException は検査例外なので catch か throws が必要。parseInt の NumberFormatException は非検査例外なので javac はチェックしない
    }
}