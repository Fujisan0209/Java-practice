import java.util.*;
import java.util.regex.*;

public class RegexLab {
    static boolean isDate(String s) {
        if(s == null) throw new IllegalArgumentException("s must not be null");

        return s.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    static List<Integer> amounts(String memo) {
        if(memo == null) throw new IllegalArgumentException("s must not be null");

        List<Integer> result = new ArrayList<>();
        Matcher m = Pattern.compile("(\\d+)円").matcher(memo);
        while(m.find()) {
            int a = Integer.parseInt(m.group(1));
            result.add(a);
        }
        return result;
    }

    static List<String> tags(String memo) {
        if(memo == null) throw new IllegalArgumentException("s must not be null");

        List<String> result = new ArrayList<>();
        Matcher m = Pattern.compile("#([a-z]+)").matcher(memo);
        while(m.find()) {
            result.add(m.group(1));
        }
        return result;
    }

    static Optional<String> firstQuoted(String memo) {
        if(memo == null) throw new IllegalArgumentException("s must not be null");

        Matcher m = Pattern.compile("\"(.*?)\"").matcher(memo);
        if(m.find()) {
            return Optional.of(m.group(1));
        }
        return Optional.empty();
    }

    // ===== ここから下は出題側が用意（本人は変更しない） =====
    static int ok = 0, ng = 0;

    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(String.valueOf(expected), String.valueOf(actual))) {
            ok++; System.out.println("OK " + label);
        } else {
            ng++; System.out.println("NG " + label + " 期待=" + expected + " 実際=" + actual);
        }
    }

    static void checkThrows(String label, Class<? extends Throwable> type, Runnable r) {
        try {
            r.run();
            ng++; System.out.println("NG " + label + " 例外が出なかった");
        } catch (Throwable t) {
            if (type.isInstance(t)) { ok++; System.out.println("OK " + label); }
            else { ng++; System.out.println("NG " + label + " 違う例外 " + t); }
        }
    }

    public static void main(String[] args) {
        String memo = "11/26 \"昼ごはん\" 650円 #food 本 1200円 #book \"文具\" 300円";

        System.out.println("=== 区画A：自動照合 ===");
        check("A-1 isDate 正しい形", true, isDate("2026-11-26"));
        check("A-2 isDate 月が1桁", false, isDate("2026-1-26"));
        check("A-3 isDate 前後に余計な文字", false, isDate("day 2026-11-26"));
        check("A-4 isDate 空文字", false, isDate(""));
        check("A-5 amounts 3件", "[650, 1200, 300]", amounts(memo));
        check("A-6 amounts 円が無い数字は拾わない", "[500]", amounts("11/26 2個 500円"));
        check("A-7 amounts 0件", "[]", amounts("今日は使っていない"));
        check("A-8 tags 2件", "[food, book]", tags(memo));
        check("A-9 tags # だけは拾わない", "[ok]", tags("# と #ok"));
        check("A-10 tags 0件", "[]", tags("タグなし"));
        check("A-11 firstQuoted 最初の1つだけ", "Optional[昼ごはん]", firstQuoted(memo));
        check("A-12 firstQuoted 無い", "Optional.empty", firstQuoted("引用なし"));
        check("A-13 firstQuoted 閉じていない", "Optional.empty", firstQuoted("\"閉じてない"));
        check("A-14 firstQuoted 空の引用", "Optional[]", firstQuoted("\"\" と \"x\""));
        checkThrows("A-15 isDate null", IllegalArgumentException.class, () -> isDate(null));
        checkThrows("A-16 amounts null", IllegalArgumentException.class, () -> amounts(null));
        checkThrows("A-17 tags null", IllegalArgumentException.class, () -> tags(null));
        checkThrows("A-18 firstQuoted null", IllegalArgumentException.class, () -> firstQuoted(null));
        System.out.println("OK=" + ok + " NG=" + ng);

        System.out.println();
        System.out.println("=== 区画B：実行して、理由を1行で ===");
        // B-1：? を外した「欲張り」版だと、何が取れる？
        Matcher greedy = Pattern.compile("\"(.*)\"").matcher(memo);
        if (greedy.find()) System.out.println("B-1 欲張り版 = " + greedy.group(1));
                // .* は欲張りでできるだけ長く取るので、最初の " から一番最後の " まで全部取ってしまう

        // B-2：find() を呼ばずに group(1) を読むと？（コメントを外して実行→確認したら戻す）
        // Matcher noFind = Pattern.compile("(\\d+)円").matcher(memo);
        // System.out.println("B-2 = " + noFind.group(1));
        // findがないかたまだ探していないのに見せてと頼んでいるからエラーになる
    }
}