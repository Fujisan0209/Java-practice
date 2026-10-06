class Box<T> {
    private T item;

    public Box() {
    }
    
    public Box(T item) {
        if(item == null) throw new IllegalArgumentException("item must not be null");
        this.item = item;
    }

    public boolean isEmpty() {
        return item == null;
    }

    public T get() {
        if(item == null) throw new IllegalStateException("box is empty");
        return item;
    }

    public void put(T item) {
        if(item == null) throw new IllegalArgumentException("item must not be null");
        if(this.item != null) throw new IllegalStateException("box is full");
        this.item = item;
    }

    public T take() {
        if(item == null) throw new IllegalStateException("box is empty");
        T cp = item;
        item = null;
        return cp;
    }

    @Override
    public String toString() {
        if(item == null) {
            return "Box[empty]";
        } else {
            return "Box[" + item + "]";
        }
    }
}

public class BoxLab {
    public static void main(String[] args) {
        System.out.println("--- A ---");
        Box<String> s = new Box<>("pen");
        System.out.println("期待: A-1 Box[pen]/ 実際: " + s);
        String x = s.take();
        System.out.println("期待: A-2 3 true/ 実際: " + x.length() + " " + s.isEmpty());
        s.put("notebook");
        System.out.println("期待: A-3 NOTEBOOK/ 実際: " + s.get().toUpperCase());
        Box<Integer> n = new Box<>();
        n.put(40);
        int y = n.get() + 2;
        System.out.println("期待: A-4 42 Box[40]/ 実際: " + y + " " + n);
        System.out.println("期待: A-5 40 Box[empty]/ 実際: " + n.take() + " " + n);

        System.out.println("--- B ---");
        Box<Integer> p = new Box<>(500);
        Box<Integer> q = new Box<>(500);
        // 予想: false/ 根拠: アドレスは違う
        System.out.println("B-1: " + (p.get() == q.get()));
        // 予想: true/ 根拠: 中身を見ているので
        System.out.println("B-2: " + p.get().equals(q.get()));
        Box<String> e = new Box<>();
        // 予想: Box[cup]/ 根拠: 1回目でputして2回目で例外へ行くので代入されない
        try {
            e.put("cup");
            e.put("cap");
        } catch(IllegalStateException ex) {
            System.out.println("B-3: " + e);
        }

        System.out.println("--- C ---");
        try {
            new Box<String>(null);
        } catch (IllegalArgumentException ex) {
            System.out.println("C-1 " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }
        try {
            new Box<String>().get();
        } catch (IllegalStateException ex) {
            System.out.println("C-2 " + ex.getClass().getSimpleName() + ": "  + ex.getMessage());
        }
        try {
            new Box<>("a").put("b");
        } catch (IllegalStateException ex) {
            System.out.println("C-3 " + ex.getClass().getSimpleName() + ": "  + ex.getMessage());
        }
        try {
            new Box<String>().take();
        } catch (IllegalStateException ex) {
            System.out.println("C-4 " + ex.getClass().getSimpleName() + ": " + ex.getMessage());
        }

        // Box<String> f = new Box<>();
        // f.put(42);

        // Box<int> k = new Box<>();
    }
}