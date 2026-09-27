enum SeatKind {
    NORMAL("自由席", 0), 
    RESERVED("指定席", 530), 
    GREEN("グリーン席", 1000);

    private final String label;
    private final int extraFee;

    SeatKind(String label, int extraFee) {
        this.label = label;
        this.extraFee = extraFee;
    }

    public String getLabel() { return label; }

    public int getExtraFee() { return extraFee; }

    public int fare(int baseFare) {
        if(baseFare < 0) throw new IllegalArgumentException("baseFare must not be negative");
        return baseFare + extraFee;
    }

    public static SeatKind fromLabel(String label) {
        if(label == null || label.isBlank()) throw new IllegalArgumentException("label must not be blank");
        for(SeatKind s : SeatKind.values()) {
            if(s.getLabel().equals(label)) {
                return s; // 見つかった
            }
        }
        throw new IllegalArgumentException("unknown label: " + label);
    }
}

public class SeatLab {
    public static void main(String[] args) {
        System.out.println("--- A ---");
        System.out.println("期待: グリーン席/ 実際: " + SeatKind.GREEN.getLabel());
        System.out.println("期待: 2030/ 実際: " + SeatKind.RESERVED.fare(1500));
        System.out.println("期待: 1500/ 実際: " + SeatKind.NORMAL.fare(1500));
        System.out.println("期待: RESERVED/ 実際: " + SeatKind.fromLabel("指定席"));
        System.out.println("期待: \nNORMAL 自由席 +0\nRESERVED 指定席 +530\nGREEN グリーン席 +1000\n/ 実際: ");
        for(SeatKind s : SeatKind.values()) { System.out.println(s + " " + s.getLabel() + " +" + s.getExtraFee()); }

        System.out.println("--- B ---");
        System.out.println(SeatKind.fromLabel("グリーン席") == SeatKind.GREEN);
        // 予想: true
        System.out.println(SeatKind.GREEN.ordinal());
        // 予想: 分からない → 2
        System.out.println(SeatKind.valueOf("RESERVED").getExtraFee());
        // 予想: 530
        System.out.println(SeatKind.NORMAL);
        // 予想: 分からない → NORMAL

        System.out.println("--- C ---");
        // 4行
        try {
            SeatKind.NORMAL.fare(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            SeatKind.fromLabel("   ");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            SeatKind.fromLabel("寝台");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            SeatKind.valueOf("green");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
    // SeatKind s = new SeatKind("寝台", 3000);
}