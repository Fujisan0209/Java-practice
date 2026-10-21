import java.util.*;

class CountingSet extends HashSet<String> {
    private int addCount = 0;

    @Override
    public boolean add(String s) {
        addCount++;
        return super.add(s);
    }

    /*@Override
    public boolean addAll(Collection<?extends String> c) {
        addCount += c.size();
        return super.addAll(c);
    }*/

    public int getAddCount() { return addCount; }
}

class DelegatingSet {
    private final Set<String> inner = new HashSet<>();
    private int addCount = 0;

    public boolean add(String s) {
        if(s == null || s.isBlank()) throw new IllegalArgumentException("item must not be blank");
        addCount++;
        return inner.add(s);
    }

    public boolean addAll(List<String> items) {
        if(items == null) throw new IllegalArgumentException("items must not be null");
        for(String s : items) {
            if(s == null || s.isBlank()) throw new IllegalArgumentException("item must not be blank");
        }
        addCount += items.size();
        return inner.addAll(items);
    }

    public boolean contains(String s) { return inner.contains(s); }

    public int size() { return inner.size(); }

    public int getAddCount() { return addCount; }
}

public class CounterLab {
    public static void main(String[] args) {
        DelegatingSet d = new DelegatingSet();
        
        System.out.println("--- A ---");
        System.out.println("期待: A-1 add(x)=true count=1 / 実際: add(x)=" + d.add("x") + " count=" + d.getAddCount());
        d.addAll(List.of("a","b","c"));
        System.out.println("期待: A-2 count=4 size=4 / 実際: count=" + d.getAddCount() + " size=" + d.size());
        System.out.println("期待: A-3 add(a)=false count=5 size=4 / 実際: add(a)=" + d.add("a") + " count=" + d.getAddCount() + " size=" + d.size());
        System.out.println("期待: A-4 contains(b)=true / 実際: constains(b)=" + d.contains("b"));

        CountingSet s = new CountingSet();

        System.out.println("--- B ---");
        s.add("x");
        System.out.println(s.getAddCount());
        // 予想: 1 根拠: これは普通にこうなるエラーでもない
        s.addAll(List.of("a","b","c"));
        System.out.println(s.getAddCount());
        // 予想: 4 根拠: addして1、そこに3つ足して4
        // 外れた理由: 親のaddAllが中でaddを1個ずつ呼んでいて、それが自分のadd(count++)だったので3回余分に数えた
        System.out.println(s.size());
        // 予想: 4 根拠: x,a,b,cの4つ 

        System.out.println("--- C ---");
        // 4行
        try {
            d.add("");
        } catch (IllegalArgumentException e) {
            System.out.println("C-1 IAE: " + e.getMessage());
        }
        try {
            d.addAll(null);
        } catch (IllegalArgumentException e) {
            System.out.println("C-2 IAE: " + e.getMessage());
        }
        try {
            d.addAll(List.of("p", "  ", "q"));
        } catch (IllegalArgumentException e) {
            System.out.println("C-3 IAE: " + e.getMessage());
        }
        
        System.out.println("C-4 count=" + d.getAddCount() + " size=" + d.size() + " constains(p)=" + d.contains("p"));
    }
}