public class CharPeek {
    public static void main(String[] args) {
        char a = 'A';
        char b = '0';
        char c = 'あ';
        char d = '０';          // 全角のゼロ
        System.out.println((int) a);
        System.out.println((int) b);
        System.out.println((int) c);
        System.out.println((int) d);
        System.out.println((char) 65);
        System.out.println("あ".length());
        System.out.println("😀".length());
    }
}