enum Operator {
    PLUS("+") { public int apply(int a, int b) { return a + b; }},
    MINUS("-") { public int apply(int a, int b) { return a - b; }},
    TIMES("*") { public int apply(int a, int b) { return a * b; }},
    DIVIDE("/") { public int apply(int a, int b) { 
        if(b == 0) throw new IllegalArgumentException("divide by zero");
        return a / b; 
    }};

    private final String symbol;

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String symbol() { return symbol; }

    public abstract int apply(int a, int b);

    static Operator fromSymbol(String s) {
        if(s == null || s.isBlank()) throw new IllegalArgumentException("symbol is blank");
        for(Operator o : Operator.values()) {
            if(o.symbol().equals(s)) {
                return o; // 見つけた
            }
        }
        throw new IllegalArgumentException("unknown symbol: " + s);
    }
}

public class OperatorLab {
    public static void main(String[] args) {
        System.out.println("--- A ---");
        System.out.println("期待: 10/ 実際: " + Operator.PLUS.apply(7, 3));
        System.out.println("期待: 4/ 実際: " + Operator.MINUS.apply(7, 3));
        System.out.println("期待: 21/ 実際: " + Operator.TIMES.apply(7, 3));
        System.out.println("期待: 2/ 実際: " + Operator.fromSymbol("/").apply(7, 3));
        for(Operator o : Operator.values()) {
            System.out.println("7 " + o.symbol() + " 3 = " + o.apply(7, 3));
        }

        System.out.println("--- B ---");
        System.out.println(Operator.DIVIDE.apply(-7, 2));
        // 予想: -3 根拠: 割り算、整数なので余りは捨てる
        System.out.println(Operator.fromSymbol("*") == Operator.TIMES);
        // 予想: true 根拠: enumは==使える
        System.out.println(Operator.valueOf("MINUS").symbol());
        // 予想: - 根拠: Aでも使ったMINUSのシンボルは-
        System.out.println(Operator.PLUS.getClass() == Operator.class);
        // 予想: false 根拠: カンだったけどOperatorのクラスとPlUSのクラスはintだからかな

        System.out.println("");
        try {
            Operator.DIVIDE.apply(5, 0);
            System.out.println("C-1 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-1 " + e.getMessage());
        }
        try {
            Operator.fromSymbol(null);
            System.out.println("C-2 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-2 " + e.getMessage());
        }
        try {
            Operator.fromSymbol("  ");
            System.out.println("C-3 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-3 " + e.getMessage());
        }
        try {
            Operator.fromSymbol("%");
            System.out.println("C-4 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-4 " + e.getMessage());
        }
    }
}
