import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class FileLab {

    // linesをfileに保存する
    static void saveLines(Path file, List<String> lines) throws IOException {
        if(file == null) throw new IllegalArgumentException("file must not be null");
        if(lines == null) throw new IllegalArgumentException("lines must not be null");

        Files.write(file, lines, StandardCharsets.UTF_8);
    }

    // fileの全行をリストで返す
    static List<String> loadLines(Path file) throws IOException {
        if(file == null) throw new IllegalArgumentException("file must not be null");

        return Files.readAllLines(file, StandardCharsets.UTF_8);
    }

    // 空白だけではない行の数を返す
    static int countNonBlank(Path file) throws IOException {
        if(file == null) throw new IllegalArgumentException("file must not be null");

        try(Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8)) {
            long c = lines.filter(s -> !s.isBlank())
            .count();
            return (int)c;
        }
    }

    // ===== ここから下は出題側が用意した main =====
    static int ok = 0, ng = 0;
    static void check(String label, Object expected, Object actual) {
        if (Objects.equals(expected, actual)) { ok++; System.out.println("OK " + label); }
        else { ng++; System.out.println("NG " + label + " 期待=" + expected + " 実際=" + actual); }
    }
    interface Body { void run() throws Exception; }
    static void checkThrows(String label, Class<?> kind, Body body) {
        try { body.run(); ng++; System.out.println("NG " + label + " 例外が出なかった"); }
        catch (Exception e) {
            if (kind.isInstance(e)) { ok++; System.out.println("OK " + label + " (" + e.getClass().getSimpleName() + ")"); }
            else { ng++; System.out.println("NG " + label + " 期待=" + kind.getSimpleName() + " 実際=" + e); }
        }
    }

    public static void main(String[] args) throws Exception {
        Path dir = Files.createTempDirectory("filelab");   // 練習用の一時フォルダ
        Path memo = dir.resolve("memo.txt");

        // --- 区画A ---
        saveLines(memo, List.of("食費", "500", "日用品", "300"));
        List<String> got = loadLines(memo);
        check("A-1 保存→読み込みで同じ中身", "[食費, 500, 日用品, 300]", String.valueOf(got));
        check("A-2 行数", 4, got.size());
        check("A-3 ファイルの中身そのもの", "食費\n500\n日用品\n300\n", Files.readString(memo, StandardCharsets.UTF_8));

        saveLines(memo, List.of("上書き"));
        check("A-4 もう一度保存すると上書き", "[上書き]", String.valueOf(loadLines(memo)));

        Path blank = dir.resolve("blank.txt");
        saveLines(blank, List.of("a", "", "   ", "b"));
        check("A-5 空白だけの行は数えない", 2, countNonBlank(blank));

        Path empty = dir.resolve("empty.txt");
        saveLines(empty, List.of());
        check("A-6 空ファイルは []", "[]", String.valueOf(loadLines(empty)));
        check("A-7 空ファイルは 0", 0, countNonBlank(empty));

        Path none = dir.resolve("none.txt");
        checkThrows("A-8 無いファイルを読む", NoSuchFileException.class, () -> loadLines(none));
        checkThrows("A-9 無いファイルを数える", NoSuchFileException.class, () -> countNonBlank(none));
        checkThrows("A-10 saveLines(null, ...)", IllegalArgumentException.class, () -> saveLines(null, List.of("x")));
        checkThrows("A-11 saveLines(..., null)", IllegalArgumentException.class, () -> saveLines(memo, null));
        checkThrows("A-12 loadLines(null)", IllegalArgumentException.class, () -> loadLines(null));
        checkThrows("A-13 countNonBlank(null)", IllegalArgumentException.class, () -> countNonBlank(null));
        System.out.println("A: OK=" + ok + " NG=" + ng);

        // --- 区画B：実行して、理由を1行でコメントに書く ---
        // B-1
        Path folder = dir;
        folder.resolve("report.txt");
        System.out.println("B-1 report.txt で終わる？ " + folder.endsWith("report.txt"));
        // 理由：folder.resolve("report.txt");は作っただけで、保存されるわけではないから。戻り値として受け取らないと捨ててる。

        // B-2
        Path sjis = dir.resolve("sjis.txt");
        Files.write(sjis, List.of("食費"), Charset.forName("Shift_JIS"));
        try {
            System.out.println("B-2 " + loadLines(sjis));
        } catch (IOException e) {
            System.out.println("B-2 " + e.getClass().getSimpleName());
        }
        // 理由：Shift_JIS で書いたバイトの並びを UTF-8 の約束で読んだので、UTF-8 として成り立たず MalformedInputException になった
    }
}
