import java.util.*;

public class StockRoom {
    private final Map<String, Integer> items = new HashMap<>();

    public void add(String name, int count) {
        if(name == null || name.isEmpty()) throw new IllegalArgumentException("品物が空です");
        if(count <= 0) throw new IllegalArgumentException("個数は1以上を指定してください");

        if(items.containsKey(name)) {
            items.put(name, items.get(name) + count);
        } else {
            items.put(name, count);
        }
    }

    public int countOf(String name) {
        return items.get(name);
    }

    public boolean hasStock(String name) {
        return items.get(name) >= 1;
    }

    public void remove(String name, int count) {
        if(count <= 0) throw new IllegalArgumentException();
        if(items.get(name) - count < 0) throw new IllegalStateException("在庫が足りません: " + name + " (在庫" + items.get(name) + ", 要求" + count + ")");

        if(items.get(name) == 0) items.remove(name);
    }

    public int totalCount() {
        if(items.isEmpty()) return 0;

        int sum = 0;
        
        for(int c : items.values()) {
            sum += c;
        }
        return sum;
    }

    public static void main(String[] args) {
        StockRoom r = new StockRoom();
        r.add("apple", 3);
        r.add("banana", 2);
        r.add("apple", 5);

        r.countOf("apple");     //→ 8
        r.countOf("melon");     //→ 0
        r.hasStock("banana");   //→ true
        r.hasStock("melon");    //→ false
        r.totalCount();         //→ 10

        r.remove("apple", 8);
        r.countOf("apple");     //→ 0
        r.hasStock("apple");    //→ false      ← キーごと消えていること
        r.totalCount();         //→ 2

        try {
            r.add("grape", 0);  
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }    //→ IllegalArgumentException「個数は1以上を指定してください」

        try {
            r.add(null, 1);  
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }      //→ IllegalArgumentException「品名が空です」
        
        try {
            r.remove("banana", 5);
        } catch (IllegalStateException e) {
            System.out.println("error: " + e.getMessage());
        }
          //→ IllegalStateException「在庫が足りません: banana (在庫2, 要求5)」
        r.totalCount();         //→ 2          ← 例外が出た後も減っていないこと（原子性の確認）
    }
}
