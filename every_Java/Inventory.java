import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private final Map<String, Integer> items = new HashMap<>();

    public void add(String name, int count) {
        if(items.containsKey(name)) {
            int c = items.get(name);
            items.put(name, c + count);
        } else {
            items.put(name, count);
        }
    }

    public int countOf(String name) {
        if(items.containsKey(name)) {
            return items.get(name);
        } else {
            return 0;
        }
    }

    public boolean hasStock(String name) {
        int c = countOf(name);

        if(c >= 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean remove(String name, int count) {
        if(countOf(name) >= count) { // 直接itemsに触れるとnullのバグが出る
            items.put(name, countOf(name) - count);
            if(countOf(name) == 0) items.remove(name);
            return true;
        } else {
            return false;
        }
    }
    public static void main(String[] args) {
        Inventory inv = new Inventory();
        inv.add("ペン", 5);
        inv.remove("ペン", 2);   // true、残り3
        inv.remove("ペン", 10);  // false、残り3のまま
        inv.remove("ペン", 3);   // true、残り0 → キーごと消える
        inv.countOf("ペン");     // 0

        System.out.println(inv.countOf("ペン"));      
        System.out.println(inv.countOf("ノート"));    
        System.out.println(inv.countOf("消しゴム"));  
        System.out.println(inv.hasStock("ペン"));     
        System.out.println(inv.hasStock("消しゴム")); 
    }
} 