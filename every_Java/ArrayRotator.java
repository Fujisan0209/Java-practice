import java.util.Arrays;

public class ArrayRotator {

    public static int[] rotateRight(int[] src, int k) {
    int n = src.length;
    if (n == 0) return new int[0];

    k = k % n;
    if (k < 0) k = k + n;      // ← あなたの書き方でOK

    int[] result = new int[n];
    for (int i = 0; i < n; i++) {
        result[(i + k) % n] = src[i];
    }
    return result;
}

    public static void reverseInPlace(int[] a) {
        int left = 0;
        int right = a.length - 1;
        while (left < right) {
            int tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;

            left++;
            right--;
        }
    }

    public static void main(String[] args) {
        int[] src = {1, 2, 3, 4, 5};

        System.out.println("元の配列    : " + Arrays.toString(src));
        System.out.println("右に2回転   : " + Arrays.toString(rotateRight(src, 2)));
        System.out.println("右に7回転   : " + Arrays.toString(rotateRight(src, 7)));
        System.out.println("左に2回転   : " + Arrays.toString(rotateRight(src, -2)));
        System.out.println("回転後の元配列: " + Arrays.toString(src));

        System.out.println();

        int[] a = {1, 2, 3, 4, 5};
        System.out.println("反転前 : " + Arrays.toString(a));
        reverseInPlace(a);
        System.out.println("反転後 : " + Arrays.toString(a));
    }
}