sealed interface Shape permits Circle, Rect, Triangle {}

record Circle(double r) implements Shape {
    Circle {
        if(r <= 0) throw new IllegalArgumentException("r must be positive");
    }
}

record Rect(double w, double h) implements Shape {
    Rect {
        if(w <= 0 || h <= 0) throw new IllegalArgumentException("w and h must be positive");
    }
}

record Triangle(double base, double height) implements Shape {
    Triangle {
        if(base <= 0 || height <= 0) throw new IllegalArgumentException("base and height must be positive");
    }
}

public class ShapeLab {
    static double area(Shape s) {
        return switch(s) {
            case Circle c ->  Math.PI * c.r() * c.r();
            case Rect r -> r.w() * r.h();
            case Triangle t -> t.base() * t.height() / 2;
        };
    }

    static String describe(Shape s) {
        return switch(s) {
            case Circle c -> "円";
            case Rect r when r.w() == r.h() -> "正方形";
            case Rect r -> "長方形";
            case Triangle t -> "三角形"; 
        };
    }
    public static void main(String[] args) {
        System.out.println("--- A ---");
        System.out.println("期待: 3.14/ 実際: " + String.format("%.2f", area(new Circle(1.0))));
        System.out.println("期待: 6.00/ 実際: " + String.format("%.2f", area(new Rect(2, 3))));
        System.out.println("期待: 6.00/ 実際: " + String.format("%.2f", area(new Triangle(3, 4))));
        System.out.println("期待: 円/ 実際: " + describe(new Circle(2.0)));
        System.out.println("期待: 三角形/ 実際: " + describe(new Triangle(3, 4)));

        System.out.println("--- B ---");
        System.out.println(describe(new Rect(3, 3)));
        // 予想: 正方形 理由: 辺の長さが同じ
        System.out.println(new Circle(1.0));
        // 予想: toStringで決まるのはわかるけど出力はわからない 理由:
        // 実際: Circle[r=1.0]
        Shape s = new Rect(2, 3);
        System.out.println(describe(s));
        // 予想: 長方形 理由: 辺の長さが異なる
        System.out.println(new Rect(2, 3).equals(new Rect(2, 3)));
        // 予想: true 理由: 中身は同じ

        System.out.println("--- C ---");
        try {
            new Circle(0);
            System.out.println("C-1 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-1 " + e.getMessage());
        }
        try {
            new Rect(-1, 2);
            System.out.println("C-2 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-2 " + e.getMessage());
        }
        try {
            new Triangle(3, 0);
            System.out.println("C-3 投げられなかった");
        } catch (IllegalArgumentException e) {
            System.out.println("C-3 " + e.getMessage());
        }
        try {
            area(null);
            System.out.println("C-4 投げられなかった");
        } catch (NullPointerException e) {
            System.out.println("C-4 " + e.getClass().getSimpleName());
        }
    }
}

// record Hexagon(double a) implements Shape {}