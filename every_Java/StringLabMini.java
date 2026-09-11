public class StringLabMini {
    static String repeat(String s, int n) {
        if(s == null) throw new IllegalArgumentException("s must not be null");
        if(n < 0) throw new IllegalArgumentException("n must not be negative");
        if(n == 0) return "";

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(s);
        }
        String result = sb.toString();
        return result;
    }

    static void appendToBuilder(StringBuilder sb) {
        if(sb == null) throw new IllegalArgumentException("sb must not be null");
        sb.append("!");
    }

    static void replaceBuilder(StringBuilder sb) {
        StringBuilder n = new StringBuilder();
        n.append("zzz");
        sb = n;       
    }

    public static void main(String[] args) {

        System.out.println("--- A ---");

        System.out.println("repeat(ab, 3) = " + repeat("ab", 3) + " 予想: ababab");
        System.out.println("repeat(ab, 1) = " + repeat("ab", 1) + " 予想: ab");
        System.out.println("repeat(ab, 0) = " + repeat("ab", 0) + " 予想: 空文字");
        System.out.println("repeat(, 3) = " + repeat("", 3) + " 予想: 空文字");
        try {
            repeat(null, 3);
        } catch (IllegalArgumentException e) {
            System.out.println("repeat(null, 3) = " + e.getMessage() + " 予想: s must not be null");
        }
        try {
            repeat("ab", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("repeat(ab, -1) = " + e.getMessage() + " 予想: n must not be negative");
        }
        
        System.out.println("--- B ---");
        // B - 1
        StringBuilder sb1 = new StringBuilder("hi");
        System.out.println("B-1 前 : " + sb1 + " / id = " + System.identityHashCode(sb1));
        appendToBuilder(sb1);
        System.out.println("B-1 後 : " + sb1 + " / id = " + System.identityHashCode(sb1));
        System.out.println("予想：中身＝ 前: hi  / 後: hi!");
        System.out.println("予想：idは変わらない / 根拠: StringBuilderは新しいものに変えるとかではなく可変だからかな？");
        // B - 2
        StringBuilder sb2 = new StringBuilder();
        System.out.println("B-2 前 : " + sb2 + " / id = " + System.identityHashCode(sb2));
        replaceBuilder(sb2);
        System.out.println("B-2 後 : " + sb2 + " / id = " + System.identityHashCode(sb2));
        System.out.println("予想：中身＝ 前:   / 後: ");
        System.out.println("予想：idは変わらない / 根拠: StringBuilderは新しいものに変えるとかではなく可変だからかな？");
        // B - 3
        String s3 = "hi";
        System.out.println("B-3 前 : " + s3 + " / id = " + System.identityHashCode(s3));
        s3 += "!";
        System.out.println("B-3 後 : " + s3 + " / id = " + System.identityHashCode(s3));
        System.out.println("予想：中身＝ 前: hi  / 後: hi!");
        System.out.println("予想：idは変わる / 根拠: Stringは足してコピーを返しているから");
    }
}