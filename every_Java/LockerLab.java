
import java.util.*;

final class Locker implements Comparable<Locker>{
    private final int number;
    private final String owner;

    public Locker(int number, String owner) {
        if(number < 1 || 999 < number) throw new IllegalArgumentException("number out of range: " + number);
        if(owner == null || owner.isBlank()) throw new IllegalArgumentException("owner is blank");
        this.number = number;
        this.owner = owner;
    }

    public int getNumber() { return number; }

    public String getOwner() { return owner; }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Locker other = (Locker) o;
        return number == other.number;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }

    @Override
    public String toString() {
        return "No." + number + "(" + owner + ")";
    }

    @Override
    public int compareTo(Locker other) {
        return Integer.compare(number, other.number);
    }
}

public class LockerLab {
    static Optional<Locker> findByNumber(List<Locker> lockers, int number) {
        if(lockers == null) throw new IllegalArgumentException("lockers is null");
        for(Locker l : lockers) {
            if(l.getNumber() == number) {
                return Optional.of(l);
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        List<Locker> list = new ArrayList<>();
        list.add(new Locker(30, "sato"));
        list.add(new Locker(5, "tanaka"));
        list.add(new Locker(12, "suzuki"));

        System.out.println("期待: [No.30(sato), No.5(tanaka), No.12(suzuki)]/ 実際: " + list);
        Collections.sort(list);
        System.out.println("期待: [No.5(tanaka), No.12(suzuki), No.30(sato)]/ 実際: " + list);
        System.out.println("期待: Optional[No.12(suzuki)]/ 実際: " + findByNumber(list, 12));
        System.out.println("期待: Optional.empty/ 実際: " + findByNumber(list, 99));

        System.out.println("--- B ---");        
        System.out.println(new Locker(12, "suzuki").equals(new Locker(12, "kato")));
        // 予想: true/ 根拠: numberで判断するから
        Set<Locker> set = new HashSet<>();       
        System.out.println(set.add(new Locker(12,"suzuki")));
        System.out.println(set.add(new Locker(12,"kato")));
        System.out.println(set);
        // 予想: addは返り値あるの？ setは最初追加されたほうが出力/ 根拠:これもnumberで判断するから 
        // 答え: true
        // 答え: false
        // 答え: [No.12(suzuki)]
        Integer x = 127, y = 127;
        System.out.println(x == y);
        // 予想: false/ 根拠: アドレスは違うから
        // 答え: true
        Integer p = 128, q = 128;
        System.out.println(p == q);
        // 予想: false/ 根拠: アドレスは違うから

        System.out.println("--- C ---");
        try {
            new Locker(0, "a");
        } catch (RuntimeException e) {
            System.out.println("C-1 " + e.getClass().getSimpleName() + ": "  + e.getMessage());
        }
        try {
            new Locker(1000, "a");
        } catch (RuntimeException e) {
            System.out.println("C-2 " + e.getClass().getSimpleName() + ": "  + e.getMessage());
        }
        try {
            new Locker(5, "  ");
        } catch (RuntimeException e) {
            System.out.println("C-3 " + e.getClass().getSimpleName() + ": "  + e.getMessage());
        }
        try {
            findByNumber(null, 1);
        } catch (RuntimeException e) {
            System.out.println("C-4 " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}