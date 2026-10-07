
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EraseLab {
    static boolean sameRuntimeClass(Object a, Object b) {
        if(a == null || b == null) throw new IllegalArgumentException("args must not be null");
        return a.getClass().getName().equals(b.getClass().getName());
    }

    static <T> Optional<T> firstOfType(List<Object> items, Class<T> type) {
        if(items == null) throw new IllegalArgumentException("items must not be null");
        if(type == null) throw new IllegalArgumentException("type must not be null");
        for(Object i : items) {
            if(type.isInstance(i)) {
                return Optional.of(type.cast(i));
            } 
        }
        return Optional.empty();
    }

    // D-1
    // static <T> T[] make(int n) { return new T[n]; }

    // D-2
    // static boolean isStrings(Object o) { return o instanceof List<String>; }

    // D-3
    // static void show(List<String> a) {}
    // static void show(List<Integer> a) {}

    public static void main(String[] args) {
        System.out.println("--- A ---");
        List<Object> mixed = new ArrayList<>(List.of("apple", 42, 3.14, "banana", 7));
        System.out.println("期待: A-1 Optional[apple]/ 実際: " + firstOfType(mixed, String.class));
        System.out.println("期待: A-2 Optional[42]/ 実際: " + firstOfType(mixed, Integer.class));
        System.out.println("期待: A-3 Optional.empty/ 実際: " + firstOfType(mixed, Boolean.class));

        System.out.println("--- B ---");
        System.out.println("B-1 " + sameRuntimeClass(new ArrayList<String>(), new ArrayList<Integer>()));
        // 予想: true/根拠: どっちもList
        System.out.println("B-2 " + new ArrayList<String>().getClass().getName());
        // 予想: ArrayList/根拠: 上と同じで実行中はList
        List<String> names = new ArrayList<>(List.of("sato"));
        List raw = names;
        raw.add(42);
        // names.add(42);
        System.out.println("B-3a size=" + names.size());
        // 予想: size=1/根拠: satoだけ
        // 実際: B-3a size=2
        // 理由: 実行中は<String>が消えているので、raw経由の42も止められずに入る
        try {
            String s = names.get(1);
            System.out.println("B-3b " + s); 
        } catch (ClassCastException e) {
            System.out.println("B-3b " + e.getClass().getSimpleName());
        }
        // 実際: B-3b ClassCastException
        // 理由: 落ちたのはget(1)の行。addの行では型を調べないので、取り出してStringにする所で初めて失敗する

        System.out.println("--- C ---");
        try {
            firstOfType(mixed, null);
        } catch (IllegalArgumentException e) {
            System.out.println("C-1 " + e.getMessage());
        }
        try {
            sameRuntimeClass(null, "x");
        } catch (IllegalArgumentException e) {
            System.out.println("C-2 " + e.getMessage());
        }
    }
}