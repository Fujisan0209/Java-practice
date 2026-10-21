
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

record Product(String name, int price) {
    Product {
        if(name == null) throw new IllegalArgumentException("name must not be null");
        if(name.isEmpty()) throw new IllegalArgumentException("name must not be empty");
        if(price < 0) throw new IllegalArgumentException("price must not be negative: " + price);
    }

    Product withPrice(int newPrice) {
        return new Product(name, newPrice);
    }
}

record Order(String customer, List<String> items) {
    Order {
        items = List.copyOf(items);
    }
}

public class RecordLab {
    public static void main(String[] args) {
        Product p1 = new Product("ノート", 120);

        System.out.println("--- A ---");
        System.out.println("期待値: ノート, 答え→ " + p1.name());
        System.out.println("期待値: 120, 答え→ " + p1.price());
        System.out.println("期待値: Product[name=ノート, price=120], 答え→ " + 	p1);
        System.out.println("期待値: Product[name=ノート, price=150], 答え→ " + p1.withPrice(150));
        System.out.println("期待値: Product[name=ノート, price=120], 答え→ " + p1);

        Product p2 = new Product("ノート", 120);
        List<String> list = new ArrayList<>();
        list.add("ペン");
        Order order = new Order("富田", list);

        System.out.println("--- B ---");
        System.out.println(p1.equals(p2));
        // true

        Set<Product> pros = new HashSet<>();

        pros.add(p1); pros.add(p2);
        System.out.println(pros.size());
        // 1
        list.add("消しゴム");
        System.out.println(order.items());
        // [ペン, 消しゴム]
        order.items().add("定規");
        System.out.println(order.items());
        // [ペン, 消しゴム, 定規]

        System.out.println("--- C ---");
        try {
            new Product(null, 100);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Product("", 100);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Product("ペン", -1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        // p1.price = 999;

        record Scores(String name, int[] values) {

            Scores {
                values = values.clone();
            }

            public int[] values() {
                return values.clone();
            }
        }

        int[] arr = {10, 20, 30};
        Scores s = new Scores("富田", arr);

        arr[0] = 999;                         // 入口を試す
        System.out.println(s.values()[0]);    // 予想: 10

        s.values()[0] = 777;                  // 出口を試す
        System.out.println(s.values()[0]);    // 予想: 10
    }
}