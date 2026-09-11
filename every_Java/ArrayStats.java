public class ArrayStats {
    public static int max(int[] a) {
        if(a == null || a.length == 0) throw new IllegalArgumentException("配列が空です");

        int m = a[0];
        for(int i = 0; i < a.length; i++) {
            if(m < a[i]) m = a[i];
        }
        return m;
    }

    public static int[] withoutNegatives(int[] a) {
        int[] b = new int[a.length];
        int j = 0;
        for(int i = 0; i < a.length; i++) {
            if(a[i] >= 0) {
                b[j] = a[i];
                j++;
            }
        }

        int[] c = new int[j];
        for(int i = 0; i < j; i++) {
            c[i] = b[i];
        }
        return c;
    }

    public static void reverseInPlace(int[] a) {
        int left = 0;
        int right = a.length - 1;

        while(left <= right) {
            int tmp = a[left];
            a[left] = a[right];
            a[right] = tmp;

            left++;
            right--; 
        }
    }

    public static void main(String[] args) {
        int[] a = {3, -1, 7, -5, 7};

        System.out.println("max = " + max(a));
        System.out.println("withoutNegatives = ");
        int[] b = withoutNegatives(a);
        for(int i : b) System.out.print(i + " ");
        
        System.out.println();
        for(int i : a) System.out.print(i + " ");
        System.out.println("");

        reverseInPlace(a);
        for(int i : a) System.out.print(i + " ");
        System.out.println("");


        try {
            System.out.println("max(new int[0] = " + max(new int[0]));
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }
        
        System.out.println("withoutNegatives = ");
        int[] c = withoutNegatives(new int[]{-1, -2});
        for(int i : c) System.out.print(i + " ");

        int[] d = {9};
        reverseInPlace(d); 
        for(int i : d) System.out.print(i + " ");
        System.out.println("");
    }
}