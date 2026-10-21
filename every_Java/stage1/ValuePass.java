
import java.util.Arrays;

public class ValuePass {
    static int[] reversedCopy(int[] a) {
        if(a == null) throw new IllegalArgumentException("配列がnullです");
        if(a.length == 0) return new int[0];

        int[] b = Arrays.copyOf(a, a.length);
        int j = 0;
        for(int i = a.length - 1; i >= 0; i--) {
            b[j] = a[i];
            j++;
        }
        return b;
    }

    static void reverseInPlace(int[] a) {
        if(a == null) throw new IllegalArgumentException("配列がnullです");

        int left = 0;
        int right = a.length - 1;

        while(left < right) {
            int tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;

            left++;
            right--;
        }
    }

    static void swapInts(int x, int y) {
        int tmp = x; 
        x = y;
        y = tmp;
    }

    static void fillWithZero(int[] a) {
        if(a == null) throw new IllegalArgumentException("配列がnullです");
        Arrays.fill(a, 0);
    }

    static void replaceArray(int[] a) {
        a = new int[]{9, 9, 9};
    }

    static void appendBang(String s) {
        s = s + "!";
    }

    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4};

        System.out.println("--- A ---");
        int[] r = reversedCopy(a);
        System.out.println("reversedCopy(a) = " + Arrays.toString(r) + " 予想: [4, 3, 2, 1]");
        System.out.println("a (呼び出しの後) = " + Arrays.toString(a) + " 予想: [1, 2, 3, 4]");
        r[0] = 999;
        System.out.println("返り値[0]=999 の後 a = " + Arrays.toString(a) + " 予想: [1, 2, 3, 4]");
        System.out.println("a == 返り値 = " + (a == r) + " 予想: false");
        System.out.println("Arrays.equals(a, 返り値) = " + Arrays.equals(a, r) + " 予想: false");
        reverseInPlace(a);
        System.out.println("reverseInPlace の後 a = " + Arrays.toString(a) + " 予想: [4, 3, 2, 1]");
        System.out.println("");

        System.out.println("--- B ---");
        int[] e = a;
        reverseInPlace(e);
        System.out.println("reverseInPlace(e) の後 e = " + Arrays.toString(e) + " 予想: [4, 3, 2, 1]");
        System.out.println("reverseInPlace(e) の後 a = " + Arrays.toString(a) + " 予想: [4, 3, 2, 1]");
        System.out.println("idHash(a) = " + System.identityHashCode(a));
        System.out.println("idHash(e) = " + System.identityHashCode(e) + "a と eは同じ");

        System.out.println("--- C ---");
        int x = 10, y = 20;
        int[] c = {1, 2, 3}; 
        int[] d = {1, 2, 3};
        String s = "java";
        swapInts(x, y);
        System.out.println("swapInts の後 x = " + x + "予想: 20");
        System.out.println("swapInts の後 y = " + y + "予想: 10");
        fillWithZero(c);
        System.out.println("fillWithZero の後 c = " + Arrays.toString(c) + "予想: [0, 0, 0]");
        replaceArray(d);
        System.out.println("replaceArray の後 d = " + Arrays.toString(d) + "予想: [3, 2, 1]");
        appendBang(s);
        System.out.println("appendBang の後 s = " + s + "予想: java!");

        System.out.println("--- D ---");
        try {
            reversedCopy(null);
        } catch(IllegalArgumentException ee) {
            System.out.println("reversedCopy(null) の例外 = "  + "error: " + ee.getMessage() + " 予想: 配列がnullです");
        }
    } 
}