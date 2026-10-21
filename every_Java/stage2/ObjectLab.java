
import java.util.ArrayList;
import java.util.List;

class Plain {
    int x;

    public Plain(int x) {
        this.x = x;
    }
}

class Bag implements Cloneable {
    private final String owner;
    private List<String> items;

    public Bag(String owner) {
        if(owner == null || owner.isBlank()) throw new IllegalArgumentException("owner must not be blank");
        this.owner = owner;
        items = new ArrayList<>();
    }

    public Bag(Bag other) {
        if(other == null) throw new NullPointerException("other must not be null");
        this.owner = other.owner;
        this.items = new ArrayList<>(other.items);
    }

    public void add(String item) {
        if(item == null || item.isBlank()) throw new IllegalArgumentException("item must not be blank");
        items.add(item);
    }

    public List<String> getItems() { return List.copyOf(items); }

    @Override
    public String toString() {
        return owner + items;
    }

    @Override
    public Bag clone() {
        try {
            return (Bag) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}

public class ObjectLab {
    public static void main(String[] args) {
        System.out.println("--- A ---");
        Bag b = new Bag("tomita"); 
        b.add("pen"); 
        b.add("note");
        System.out.println("A-1: tomita[pen, note] / " + b);
        Bag c = new Bag(b); 
        c.add("eraser");
        System.out.println("A-2: tomita[pen, note] / " + b);
        System.out.println("A-3: tomita[pen, note, eraser] / " + c);
        Bag d = b.clone();
        System.out.println("A-4: Bag / " + d.getClass().getSimpleName());
        System.out.println("A-5: false / " + (d == b));

        System.out.println("--- B ---");
        Plain p1 = new Plain(1);
        Plain p2 = new Plain(1);
        System.out.println("B-1: " + p1.equals(p2));
        // 予想: true/根拠: 中身は同じ
        // 実際 false
        System.out.println("B-2: " + p1.toString().equals("Plain@" + Integer.toHexString(p1.hashCode())));
        // 予想: true/根拠: 勘だけど、クラス名@ハッシュコードだった気がする
        // 合ってた
        d.add("ruler");
        System.out.println("B-3: " + b);
        // 予想: tomita[pen, note, ruler]/根拠:toString()通りに 
        // 合ってた

        System.out.println("--- C ---");
        try {
            new Bag(" ");
        } catch (RuntimeException e) {
            System.out.println("C-1 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        try {
            b.add("");
        } catch (RuntimeException e) {
            System.out.println("C-2 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        try {
            new Bag((Bag) null);
        } catch (RuntimeException e) {
            System.out.println("C-3 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }

    }
}