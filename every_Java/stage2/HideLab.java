class Device {
    String label = "device";

    static String category() { return "Device"; }

    Device() { System.out.println("init: " + this.describe()); }

    String name() { return "Device"; }

    int power() { return 10; }

    String describe() { return name() + "/" + power(); }

    Device copy() { return new Device(); }
}

class Lamp extends Device {
    String label = "lamp";
    private final int watts;

    static String category() { return "Lamp"; }

    Lamp(int watts) {
        if(watts <= 0) throw new IllegalArgumentException("watts must be positive");
        this.watts = watts;
    }

    @Override
    String name() { return "Lamp"; }

    @Override
    int power() { return watts; }

    @Override
    Lamp copy() { return new Lamp(watts); }
}

public class HideLab {
    public static void main(String[] args) {
        System.out.println("--- A --- ");
        Device d = new Device();
        System.out.println("期待: A-1 Device/10/ 実際: " + d.describe());
        Lamp l = new Lamp(40);
        System.out.println("期待: A-2 Lamp/40/ 実際: " + l.describe());
        Lamp c = l.copy();
        System.out.println("期待: A-3 40/ 実際: " + c.power());
        System.out.println("期待: A-4 Device/ 実際: " + Device.category());
        System.out.println("期待: A-5 Lamp/ 実際: " + Lamp.category());

        System.out.println("--- B ---");
        new Lamp(40);
        System.out.println("B-1 予想: init: Lamp/40/ 根拠: power()はOverrideしてるから");
        System.out.println("B-1 答え init: Lamp/0");
        Device x = new Lamp(60);
        System.out.println(x.label);
        System.out.println("B-2 予想: device/ 根拠: 変数に書いてある型で決まるから");
        System.out.println(((Lamp) x).label);
        System.out.println("B-3 予想: lamp/ 根拠: キャストしている");
        System.out.println(x.name());
        System.out.println("B-4 予想: Lamp/ 根拠: これもOverrideしている");
        System.out.println(x.category());
        System.out.println("B-5 予想: Lamp/ 根拠: 実体はLampだから");
        System.out.println("B-5 答え Device");
        System.out.println(x.copy().getClass().getSimpleName());
        System.out.println("B-6 予想: Lamp/ 根拠: 上と同じ");

        System.out.println("--- C ---");
        System.out.println("2行");
        System.out.println("C 根拠: 親のコンストラクタ（init 出力）が先に動き、watts の検査はその後だから");
        try { new Lamp(0); } catch (IllegalArgumentException e) { System.out.println("C-1 " + e.getMessage()); }
    }
}





