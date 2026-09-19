
import java.util.*;

class Product {
    private final String code;
    private final String name;

    public Product(String code, String name) {
        if(code == null) throw new IllegalArgumentException("code must not be null: " + code);
        if(code.isEmpty()) throw new IllegalArgumentException("code must not be empty: " + code);
        if(name == null) throw new IllegalArgumentException("name must not be null: " + name);
        if(name.isEmpty()) throw new IllegalArgumentException("name must not be empty: " + name);

        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "Product{code=" + code + ", name=" + name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(getClass() != o.getClass()) return false;
        Product other = (Product) o;
        return this.code.equals(other.getCode()) && this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}

class BadProduct {
    private final String code;
    private final String name;

    public BadProduct(String code, String name) {
        if(code == null) throw new IllegalArgumentException("code must not be null: " + code);
        if(code.isEmpty()) throw new IllegalArgumentException("code must not be empty: " + code);
        if(name == null) throw new IllegalArgumentException("name must not be null: " + name);
        if(name.isEmpty()) throw new IllegalArgumentException("name must not be empty: " + name);

        this.code = code;
        this.name = name;
    }

    public String getCode() { return code; }

    public String getName() { return name; }

    @Override
    public String toString() {
        return "Product{code=" + code + ", name=" + name + "}";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null) return false;
        if(getClass() != o.getClass()) return false;
        BadProduct other = (BadProduct) o;   // 直前の行で「自分と同じクラス」と確認済みなので、自分の型でキャストする
        return this.code.equals(other.getCode()) && this.name.equals(other.getName());
    }
}

public class ProductLab {
    public static void main(String[] args) {
        Product p1 = new Product("A-01", "ねじ");
        Product p2 = new Product("A-01", "ねじ");
        Product p3 = new Product("B-02", "ナット");
        BadProduct b1 = new BadProduct("A-01", "ねじ");
        BadProduct b2 = new BadProduct("A-01", "ねじ");

        System.out.println("--- A ---");
        System.out.println("期待値: true " + p1.equals(p2));
        System.out.println("期待値: false " + p1.equals(p3));
        System.out.println("期待値: false " + (p1 == p2));
        System.out.println("期待値: false " + p1.equals(null));
        System.out.println("期待値: false " + p1.equals("A-01"));
        System.out.println("期待値: true " + (p1.hashCode() == p2.hashCode()));
        System.out.println("期待値: Product{code=A-01, name=ねじ} " + p1.toString());
        
        Set<Product> set = new HashSet<>();
        set.add(p1); set.add(p2);
        Set<BadProduct> badSet = new HashSet<>();
        badSet.add(b1); badSet.add(b2);
        Map<Product, Integer> stock = new HashMap<>();
        stock.put(p1, 10);
         
        System.out.println("--- B ---");
        System.out.println(set.size());
        // 予想: 2 → 答え: 1
        System.out.println(set.contains(new Product("A-01", "ねじ")));
        // 予想: true → 答え: true
        System.out.println(b1.equals(b2));
        // 予想: false → 答え: true
        System.out.println(badSet.size());
        // 予想: 2 → 答え: 
        System.out.println(badSet.contains(b1));
        // 予想: true → 答え: true
        System.out.println(badSet.contains(new BadProduct("A-01", "ねじ")));
        // 予想: true → 答え: false
        System.out.println(stock.get(new Product("A-01", "ねじ")));
        // 予想: 分からない → 答え: 10 

        System.out.println("--- C ---");
        // 4行
        try {
            new Product(null, "ねじ");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Product("", "ねじ");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Product("A-01", null);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Product("A-01", "");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("--- 発展 ---");
        System.out.println(p1.hashCode() + " / " + p2.hashCode());
        // 同じ値
        System.out.println(b1.hashCode() + " / " + b2.hashCode());
        // 同じ値 → 一致しない
    }
}