public class Calculate {
    public static void main(String[] args) {
        int[] data = {12, 45, 7, 23, 89, 34};

        System.out.println("sum: " + sum(data));
        System.out.println("max: " + max(data));
    }

    static int sum(int[] arr) {
        int s = 0;
        for(int i : arr) {
            s += i;
        }
        return s;
    }

    static int max(int[] arr) {
        int m = arr[0];
        for(int i : arr) {
            if(m < i) {
                m = i;
            }
        }
        return m;
    }
}