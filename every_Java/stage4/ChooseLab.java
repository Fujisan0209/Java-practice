import java.util.*;

public class ChooseLab {

    // ★ここに countSorted / countInOrder / firstDuplicate を書く
    static Map<String, Integer> countSorted(List<String> categories) {
        if(categories == null) throw new IllegalArgumentException("categories must not be null");
        Set<String> set = new HashSet<>();
        for(String s : categories) { set.add(s); }
        List<String> list = new ArrayList<>();
        for(String s : set) {
            list.add(s);
        }
        Collections.sort(list);
        Map<String, Integer> map = new LinkedHashMap<>();
        for(String s : list) {
            map.put(s, 0);
        }
        for(String s : categories) {
            for(String s2 : map.keySet()) {
                if(s.equals(s2)) {
                    map.put(s, map.get(s) + 1);
                }
            }
        }
        return map;
    }

    // TreeMapは入れるだけでキーを辞書順に保つ
    /*static Map<String, Integer> countSorted(List<String> categories) {
    if (categories == null) throw new IllegalArgumentException("categories must not be null");
    Map<String, Integer> map = new TreeMap<>();
    for (String s : categories) {
        if (map.containsKey(s)) {
            map.put(s, map.get(s) + 1);
        } else {
            map.put(s, 1);
        }
    }
    return map;
    } */

    static Map<String, Integer> countInOrder(List<String> categories) {
        if(categories == null) throw new IllegalArgumentException("categories must not be null");
        Map<String, Integer> map = new LinkedHashMap<>();
        for(String s : categories) {
            if(map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        return map;
    }

    static Optional<String> firstDuplicate(List<String> ids) {
        if(ids == null) throw new IllegalArgumentException("ids must not be null");
        List<String> pre = new ArrayList<>();
        for(String s : ids) {
            for(String tmp : pre) {
                if(s.equals(tmp)) {
                    return Optional.of(s); 
                }
            }
            pre.add(s);
        }
        return Optional.empty();
    }

    // ---- 自動照合（さわらない） ----
    static int ok = 0, ng = 0;

    static void check(String label, Object actual, String expected) {
        String a = String.valueOf(actual);
        if (a.equals(expected)) { ok++; System.out.println(label + " OK   " + a); }
        else { ng++; System.out.println(label + " NG   実際=" + a + "  期待=" + expected); }
    }

    static void checkThrows(String label, Runnable r) {
        try {
            r.run();
            ng++; System.out.println(label + " NG   例外が出なかった");
        } catch (IllegalArgumentException e) {
            ok++; System.out.println(label + " OK   IllegalArgumentException");
        } catch (RuntimeException e) {
            ng++; System.out.println(label + " NG   別の例外 " + e.getClass().getSimpleName());
        }
    }

    public static void main(String[] args) {
        List<String> week = List.of("rent", "food", "hobby", "daily", "food",
                                    "book", "food", "daily", "cafe", "hobby");

        System.out.println("=== 区画A：自動照合 ===");
        check("A-1", countSorted(week), "{book=1, cafe=1, daily=2, food=3, hobby=2, rent=1}");
        check("A-2", countInOrder(week), "{rent=1, food=3, hobby=2, daily=2, book=1, cafe=1}");
        check("A-3", countSorted(List.of()), "{}");
        check("A-4", countInOrder(List.of("food")), "{food=1}");
        check("A-5", countSorted(week).get("food"), "3");
        check("A-6", countInOrder(week).get("tax"), "null");

        List<String> receipts = List.of("R01", "R02", "R03", "R02", "R01");
        check("A-7", firstDuplicate(receipts), "Optional[R02]");
        check("A-8", firstDuplicate(List.of("R01", "R02", "R03")), "Optional.empty");
        check("A-9", firstDuplicate(List.of()), "Optional.empty");
        check("A-10", firstDuplicate(List.of("X", "X")).orElse("なし"), "X");
        check("A-11", firstDuplicate(List.of("X", "Y")).orElse("なし"), "なし");

        checkThrows("A-12", () -> countSorted(null));
        checkThrows("A-13", () -> countInOrder(null));
        checkThrows("A-14", () -> firstDuplicate(null));

        System.out.println("OK=" + ok + " / NG=" + ng + "（全14項目）");

        System.out.println();
        System.out.println("=== 区画B：実行して、理由を1行で ===");
        Map<String, Integer> plain = new HashMap<>(countInOrder(week));
        System.out.println("B-1 HashMap に移すと: " + plain);
        // HashMapに並び方という概念はない
        System.out.println("B-2 重複なしのとき: " + firstDuplicate(List.of("R01", "R02")));
        // nullポインタ例外を防ぐため
    }
}