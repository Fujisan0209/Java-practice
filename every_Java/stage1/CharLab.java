public class CharLab {
    public static String toHalfWidthDigits(String s) {
        if(s == null) throw new IllegalArgumentException("s must not be null: " + s);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c >= '０' && c <= '９') {
                c = (char)(c - '０' + '0'); // c -= 65248はダメ
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println("--- 区間A ---");
        System.out.println("toHalfWidthDigits(\"１２３\") = " + toHalfWidthDigits("１２３") + " \t123");
        System.out.println("toHalfWidthDigits(\"価格は９８円\") = " + toHalfWidthDigits("価格は９８円") + " 価格は98円");
        System.out.println("toHalfWidthDigits(\"abc\") = " + toHalfWidthDigits("abc") + " abc");
        System.out.println("toHalfWidthDigits(\"\") = " + toHalfWidthDigits("") + " （空行）");
        // 発展
        System.out.println(toHalfWidthDigits("価格は９８円").equals("価格は98円"));
        System.out.println("--- 区間B ---");
        System.out.println('A' + 1);
        // 予想: B →答え: 66
        System.out.println((char)('A' + 1));
        // 予想: A + 1 →答え: B
        System.out.println('９' - '０');
        // 予想: コード9のやつ →答え: 9
        System.out.println(Character.isDigit('５'));   // 全角の5
        // 予想: true
        System.out.println("--- 区間C ---");
        try {
            toHalfWidthDigits(null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
