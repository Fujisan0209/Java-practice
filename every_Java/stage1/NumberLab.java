import java.math.BigDecimal;
import java.math.RoundingMode;

public class NumberLab {
    static long sumAsLong(int[] values) {
        if(values == null) throw new IllegalArgumentException("values is null");
        long sum = 0;
        for(int v : values) {
            sum += v;
        }
        return sum;
    }

    static int sumExact(int[] values) {
        if(values == null) throw new IllegalArgumentException("values is null");
        int sum = 0;
        for(int v : values) {
            sum = Math.addExact(sum, v);
        }
        return sum;
    }

    static int average(int[] values) {
        if(values == null || values.length == 0) throw new IllegalArgumentException("values is null");
        long sum = 0;
        for(int v : values) {
            sum += v;
        }
        return (int)(sum / values.length); // ←元々(int)sum / values.lengthこれだとバグが起きた
    }

    static BigDecimal totalPrice(BigDecimal unitPrice, int quantity) {
        if(unitPrice == null || unitPrice.compareTo(BigDecimal.ZERO) < 0 || quantity < 0) throw new IllegalArgumentException();

        BigDecimal q = new BigDecimal(Integer.toString(quantity));
        BigDecimal result = unitPrice.multiply(q);
        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        // A
        int[] a = {1, 2, 3};
        System.out.println("sumAsLong({1, 2, 3}) = " + sumAsLong(a) + " 予想: 6");
        int[] b = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        System.out.println("sumAsLong({Integer.MAX_VALUE, Integer.MAX_VALUE}) = " + sumAsLong(b) + " 予想: MAX + MAXが返ってくるでしょう");
        int[] c = {};
        System.out.println("sumAsLong({}) = " + sumAsLong(c) + " 予想: 0");
        
        int[] d = {1, 2, 3};
        System.out.println("sumExact({1, 2, 3}) = " + sumExact(d)+ " 予想: 6");
        int[] e = {Integer.MAX_VALUE, 1};
        try {
            sumExact(e);
        } catch (ArithmeticException ee) {
            System.out.println("sumExact({Integer.MAX_VALUE, 1}) = " + ee.getMessage() + " 予想: maxで止まる ← over flow");
        }
        int[] f = {1, 2, 3, 4};
        System.out.println("average({1, 2, 3, 4}) = " + average(f) + " 予想: 2");
        int[] g = {-1, -2};
        System.out.println("average({-1, -2}) = " + average(g) + " 予想: -1");
        BigDecimal h = new  BigDecimal("1.005");
        System.out.println("totalPrice(new BigDecimal(1.005), 3) = " + totalPrice(h, 3) + " 予想: 3.015 ←間違い、答えは3.02");
        BigDecimal i = new BigDecimal("100");
        System.out.println("totalPrice(new BigDecimal(100), 0) = " + totalPrice(i, 0) + " 予想: 0 ← 0.00");
        int[] big = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        System.out.println("average({MAX,MAX}) = " + average(big) + " / 期待 2147483647");

        // B
        System.out.println("---- 区画B ----");
        // 予想: 
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));
        // 予想: 
        System.out.println("(0.1 + 0.2) == 0.3 -> " + ((0.1 + 0.2) == 0.3));
        // 予想: 
        System.out.println("1_000_000 * 1_000_000 = " + (1_000_000 * 1_000_000));
        // 予想: 
        long x = 1_000_000 * 1_000_000;
        System.out.println("long x = 1_000_000 * 1_000_000 -> " + x);
        // 予想: 
        long y = 1_000_000L * 1_000_000;
        System.out.println("long y = 1_000_000L * 1_000_000 -> " + y);
        // 予想: 
        System.out.println("Integer.MAX_VALUE + 1 = " + (Integer.MAX_VALUE + 1));
        // 予想: 
        System.out.println("new BigDecimal(0.1) = " + new BigDecimal(0.1));
        // 予想: 
        System.out.println("BigDecimal.valueOf(0.1) = " + BigDecimal.valueOf(0.1));
        // 予想: 
        System.out.println("\"1.0\".equals(\"1.00\") -> " + new BigDecimal("1.0").equals(new BigDecimal("1.00")));
        // 予想: 
        System.out.println("\"1.0\".compareTo(\"1.00\")==0 -> " + (new BigDecimal("1.0").compareTo(new BigDecimal("1.00")) == 0));
    }
}