import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class ReceiptLab {
    static BigDecimal lineTotal(BigDecimal unitPrice, int quantity) {
        if(unitPrice == null) throw new IllegalArgumentException("unitPrice must not be null");
        if(unitPrice.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("unitPrice must not be negative: " + unitPrice);
        if(quantity < 0) throw new IllegalArgumentException("quantity must not be negative: " + quantity);

        BigDecimal q = new BigDecimal(Integer.toString(quantity));
        return unitPrice.multiply(q).setScale(2, RoundingMode.HALF_UP);
    }

    static String formatLine(String name, BigDecimal unitPrice, int quantity) {
        if(name == null) throw new IllegalArgumentException("name must not be null");
        if(name.length() == 0) throw new IllegalArgumentException("name must not be empty");

        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(" x");
        sb.append(quantity);
        sb.append(" = ");
        sb.append(lineTotal(unitPrice, quantity));
        return sb.toString();
    }

    static void appendAll(StringBuilder sb, List<String> lines) {
        if(lines == null) throw new IllegalArgumentException("lines must not be null");
        if(sb == null) throw new IllegalArgumentException("sb must not be null");

        for(String l : lines) {
            sb.append(l);
            sb.append("\n");
        }
    }

    public static void main(String[] args) {
        // A
        System.out.println("lineTotal(new BigDecimal(\"150\"), 3) = " + lineTotal(new BigDecimal("150"), 3) + " 予想: 450.00");
        System.out.println("lineTotal(new BigDecimal(\"1.005\"), 2) = " + lineTotal(new BigDecimal("1.005"), 2) + " 予想: 2.01");
        System.out.println("lineTotal(new BigDecimal(\"150\"), 0) = " + lineTotal(new BigDecimal("150"), 0) + " 予想: 0.00");
        System.out.println("\formatLine(\"りんご\", new BigDecimal(\"150\"), 3)" + formatLine("りんご", new BigDecimal("150"), 3) + " 予想: りんご x3 = 450.00");
        System.out.println("formatLine(\"ぶどう\", new BigDecimal(\"399\"), 2)" + formatLine("ぶどう", new BigDecimal("399"), 2) + " 予想: ぶどう x2 = 798.00");

        StringBuilder sb = new StringBuilder();
        System.out.println("appendAll(sb, List.of(\"りんご x3 = 450.00\", \"ぶどう x2 = 798.00\"))");
        appendAll(sb, List.of("りんご x3 = 450.00", "ぶどう x2 = 798.00"));
        System.out.println(sb);
        // 予想: "りんご x3 = 450.00"\n"ぶどう x2 = 798.00"\n
        
        // B
        StringBuilder sb2 = new StringBuilder();
        System.out.println("B-1前: " + sb2);
        // 予想:空白
        appendAll(sb2, List.of("A", "B"));
        System.out.println("B-1後: ");
        System.out.println(sb2);
        // 予想:A\nB\n
        System.out.println("B-2: lineTotal(new BigDecimal(\"150\"), 0) 答え→ " + lineTotal(new BigDecimal("150"), 0));
        // 予想:0.00
        System.out.println("B-3: new BigDecimal(\"1.0\").equals(new BigDecimal(\"1.00\")) = " + new BigDecimal("1.0").equals(new BigDecimal("1.00")));
        // 予想: false
        System.out.println("B-3: new BigDecimal(\"1.0\").compareTo(new BigDecimal(\"1.00\")) = " + new BigDecimal("1.0").compareTo(new BigDecimal("1.00")));
        // 予想: true → 答えは 0
        System.out.println("");

        //C
        try {
            lineTotal(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        try {
            formatLine("", new BigDecimal("1"), 1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // 発展
        String a = formatLine("りんご", new BigDecimal("150"), 3);
        String b = "りんご x3 = 450.00";
        System.out.println(a == b);        // 予想: error →答えはfalse 
        System.out.println(a.equals(b));   // 予想: true
    }
}