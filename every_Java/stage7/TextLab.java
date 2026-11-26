import java.util.*;

public class TextLab {
    static List<String> toLines(String text) {
        Objects.requireNonNull(text, "text is null");

        return text.lines()
                   .map(t -> t.strip())
                   .filter(t -> !t.isBlank())
                   .toList();
    }

    static String formatRow(String name, int amount) {
        Objects.requireNonNull(name, "name is null");
        if(amount < 0) throw new IllegalArgumentException("amount must be positive: " + amount);

        return String.format("%-8s%,8d", name, amount);
    }

    static String report(String text) {
        Objects.requireNonNull(text, "text is null");

        List<String> report = new ArrayList<>();
        List<String> parts = toLines(text);
        int sum = 0;
        for(String s : parts) {
            String[] a = s.split(",");
            if(a.length != 2) throw new IllegalArgumentException("length is not 2");

            String name = a[0].strip();
            int amount = Integer.parseInt(a[1].strip());
            sum += amount;
            report.add(formatRow(name, amount));
        }
        report.add("-".repeat(16));
        report.add(formatRow("total", sum));
        return String.join("\n", report);
    }

    public static void main(String[] atgs) {
        String text = """
            lunch,650

            book,1800
            coffee , 150
            """;

        System.out.println("--- A ---");
        System.out.println("A-1 期待 [lunch,650, book,1800, coffee , 150]/ 実際 " + toLines(text));
        System.out.println("A-2 期待 [lunch        650]/ 実際 " + "[" + formatRow("lunch", 650) + "]");
        System.out.println("A-3 期待 [book       1,800]/ 実際 " + "[" + formatRow("book", 1800) + "]");
        System.out.println("A-4 期待 lunch        650\r\n" + //
                                    "book       1,800\r\n" + //
                                    "coffee       150\r\n" + //
                                    "----------------\r\n" + //
                                    "total      2,600/ 実際 \n" + report(text));
        System.out.println("A-5 期待 []/ 実際 " + toLines("   \n\n"));
        try {
            report("tea");
        } catch (IllegalArgumentException e) {
            System.out.println("A-6 期待 例外（列が2つでない）/ 実際 " + e.getMessage());
        }
        try {
            report("tea,abc");
        } catch (NumberFormatException e) {
            System.out.println("A-7 期待 NumberFormatException/ 実際 " + e.getMessage());
        }
        try {
            formatRow("x", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("A-8 期待 例外/ 実際 " + e.getMessage());
        }
        try {
            toLines(null);
        } catch (NullPointerException e) {
             System.out.println("A-9 期待 例外/ 実際 " + e.getMessage());
        }

        // System.out.println("--- B ---");
        // B-1
        // String s = " coffee "; 
        // s.strip(); 
        // System.out.println("[" + s + "]");
        // s.strip()の結果を捨てているから

        // B-2
        // String.format("%d", "650");
        // %d は整数用なのに String "650" を渡したので、実行時に IllegalFormatConversionException になる（コンパイラは気づかない）
    }
}
