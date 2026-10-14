public class VarargsLab {

    // ★ ここに sum / max / join の3本を書く
    static int sum(int... nums) {
        if(nums == null) throw new IllegalArgumentException("nums must not be null");
        int sum = 0;
        for(int n : nums) {
            sum += n;
        }
        return sum;
    }

    static int max(int first, int... rest) {
        if(rest.length == 0) return first;
        int max = first;
        for(int n : rest) {
            if(max < n) max = n;
        }
        return max;
    }

    static String join(String sep, String... parts) {
        if(sep == null) throw new IllegalArgumentException("sep must not be null");
        for(String s : parts) {
            if(s == null) throw new IllegalArgumentException("parts must not contain null");
        }
        if(parts.length == 0) return "";
        StringBuilder sb = new StringBuilder(parts[0]);
        for(int i = 1; i < parts.length; i++) {
            sb.append(sep);
            sb.append(parts[i]);
        }
        return sb.toString();
    }

    // ===== ここから下は用意済み（触らなくてOK） =====
    public static void main(String[] args) {
        System.out.println("--- 区画A 動作確認 ---");
        check("A-1", 6, sum(1, 2, 3));
        check("A-2", 5, sum(5));
        check("A-3", 9, max(3, 9, 4));
        check("A-4", 7, max(7));
        check("A-5", "a-b-c", join("-", "a", "b", "c"));
        check("A-6", "", join(","));
        check("A-7", "x", join("/", "x"));

        System.out.println("--- 区画B 実行して理由を1行 ---");
        System.out.println("B-1 sum() = " + sum());
        int[] a = {1, 2, 3};
        System.out.println("B-2 sum(a) = " + sum(a));
        // B-1 理由： nullではなく0個なら0を返す
        // B-2 理由： 普通の配列でも大丈夫

        System.out.println("--- 区画C 例外（5行とも OK なら合格） ---");
        checkThrows("C-1", () -> sum((int[]) null), "nums must not be null");
        checkThrows("C-2", () -> join(null, "a"), "sep must not be null");
        checkThrows("C-3", () -> join(",", "a", null, "c"), "parts must not contain null");
        checkThrows("C-4", () -> join(",", (String) null), "parts must not contain null");
        checkThrows("C-5", () -> join(null), "sep must not be null");

        // 区画D：下の2行を1行ずつコメントを外して javac → エラー文を貼る → 戻す
        // max();
        // static int bad(int... a, int b) { return b; }  ← これは main の外（クラスの中）に置く
    }

    static void check(String label, Object expected, Object actual) {
        String mark = java.util.Objects.equals(expected, actual) ? "OK" : "NG";
        System.out.println(label + " " + mark + "  期待:" + expected + "  実際:" + actual);
    }

    static void checkThrows(String label, Runnable r, String expectedMsg) {
        try {
            r.run();
            System.out.println(label + " NG  例外が出なかった");
        } catch (IllegalArgumentException e) {
            String mark = expectedMsg.equals(e.getMessage()) ? "OK" : "NG";
            System.out.println(label + " " + mark + "  メッセージ:" + e.getMessage());
        }
    }
}