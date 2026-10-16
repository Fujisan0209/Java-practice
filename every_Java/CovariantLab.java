import java.util.*;

public class CovariantLab {
    static void copyInto(Object[] dst, Object[] src) {
        if(dst == null || src == null) throw new IllegalArgumentException("must not be null");
        if(dst.length < src.length) throw new IllegalArgumentException("src is longer than dst");
        int i = 0;
        for(Object o : src) {
            dst[i] = src[i];
            i++;
        }
    }

    static <T> void copyInto(List<T> dst, List<? extends T> src) {
        if(dst == null || src == null) throw new IllegalArgumentException("must not be null");
        if(dst.size() < src.size()) throw new IllegalArgumentException("src is longer than dst");
        int i = 0;
        for(T o : src) {
            dst.set(i, o);
            i++;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        Object[] o = new Integer[3];
        copyInto(o, new Integer[]{1, 2}); 
        System.out.println("A-1 期待: [1, 2, null]/ 実際: " + Arrays.toString(o));
        Number[] n = new Number[3];
        copyInto(n, new Object[]{1, 2.5});
        System.out.println("A-2 期待: [1, 2.5, null]/ 実際: " + Arrays.toString(n));
        List<Number> ln = new ArrayList<>();
        ln.add(0); ln.add(0); ln.add(0); 
        copyInto(ln, List.of(7, 8));
        System.out.println("A-3 期待: [7, 8, 0]/ 実際: " + ln);

        System.out.println("--- B ---");
        Object[] objs = new Integer[2];
        System.out.println("B-1 " + objs.getClass().getSimpleName());
        // 理由: 実体はIntegerだから
        Object[] p = new String[1];
        try {
            p[0] = 1;
        } catch (ArrayStoreException e) {
            System.out.println("B-2 " + e.getMessage());
        }
        // 理由: 実体はStringだからIntegerは入れられない

        System.out.println("--- C ---");
        try {
            copyInto(null, new Integer[]{1, 2});
        } catch (IllegalArgumentException e) {
            System.out.println("C-1 " + e.getMessage());
        }
        try {
            copyInto(new Integer[1], new Integer[]{1,2});
        } catch (IllegalArgumentException e) {
            System.out.println("C-2 " + e.getMessage());
        }
        Integer[] d = {0,0,0};
        try {
            copyInto(d, new Object[]{1, 2, "x"});
        } catch (ArrayStoreException e) {
            System.out.println("C-3 " + Arrays.toString(d));
        }
        try {
            copyInto(null, new ArrayList<>(List.of(2, 3)));
        } catch (IllegalArgumentException e) {
            System.out.println("C-4 " + e.getMessage());
        }

        // D-1
        // List<Object> lo = new ArrayList<Integer>();
        // Object[] oa = new Integer[2];

        // D-2
        // copyInto(new ArrayList<Integer>(), List.of("x"));
    }
}