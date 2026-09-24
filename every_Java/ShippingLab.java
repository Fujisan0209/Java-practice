interface trackable {
    String trackingCode();
    default String label() {
        return "追跡: " + trackingCode();
    }
}

abstract class Parcel {
    private final String name;
    private final int weightgrams;

    public Parcel(String name, int weightgrams) {
        if(name == null) throw new IllegalArgumentException("name must not be null");
        if(name.isEmpty()) throw new IllegalArgumentException("name must not be empty");
        if(weightgrams <= 0) throw new IllegalArgumentException("weight must be positive: " + weightgrams);

        this.name = name;
        this.weightgrams = weightgrams;
    }

    public String getName() { return name; }

    public int getWeightGrams() { return weightgrams; }

    public abstract int fee();

    public String describe() { return name + "(" + weightgrams + "g) " + fee() + "円"; }

    @Override
    public String toString() {
        return "Parcel{" + name + ", " + weightgrams + "g}";
    }
}

class Letter extends Parcel {
    public Letter(String name, int weightgrams) {
        super(name, weightgrams);
    }

    @Override
    public int fee() { return 120; }
}

class Box extends Parcel implements trackable {
    private final String code;

    public Box(String name, int weightgrams, String code) {
        super(name, weightgrams);
        if(code == null) throw new IllegalArgumentException("code must not be null");
        if(code.isEmpty()) throw new IllegalArgumentException("code must not be empty");
        this.code = code;
    }

    @Override
    public int fee() {
        if(this.getWeightGrams() >= 1000) return 700;
        return 500;
    }

    @Override
    public String trackingCode() {
        return code;
    }
}

class ExpressBox extends Box {
    public ExpressBox(String name, int weightgrams, String code) {
        super(name, weightgrams, code);
    }

    @Override
    public int fee() { return super.fee() *2; }

    @Override
    public String label() {
        return "速達追跡: " + trackingCode();
    }
}

public class ShippingLab {
    public static void main(String[] args) {
        Parcel a = new Letter("手紙", 20);
        Box b = new Box("箱", 1200, "BX-001");
        ExpressBox c = new ExpressBox("速達箱", 1200, "EX-009");

        System.out.println("--- A ---");
        System.out.println("期待: 手紙(20g) 120円 / 実際: " + a.describe());
        System.out.println("期待: 箱(1200g) 700円 / 実際: " + b.describe());
        System.out.println("期待: 速達箱(1200g) 1400円 / 実際: " + c.describe());
        System.out.println("期待: Parcel{箱, 1200g} / 実際: " + b);
        System.out.println("期待: 追跡: BX-001 / 実際: " + b.label());
        System.out.println("期待: 速達追跡: EX-009 / 実際: " + c.label());

        Parcel p = b;
        Box q =b;
        trackable t = c;

        System.out.println("--- B ---");
        System.out.println(p.fee());
        // 予想: 700
        System.out.println(tag(p));
        // 予想: Box版 → Parcel版
        System.out.println(tag(q));
        // 予想: Box版
        System.out.println(t.label());
        // 予想: 速達追跡: EX-009

        System.out.println("--- C ---");
        // 4行
        try {
            new Letter(null, 20);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Letter("", 20);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Box("箱", 0, "BX-001");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Box("箱", 100, "");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    static String tag(Parcel p) {
        return "Parcel版";
    }

    static String tag(Box b) {
        return "Box版";
    }

    // Parcel x = new Parcel("直接", 10);
}

