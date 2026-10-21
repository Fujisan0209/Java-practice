
import java.util.*;

final class Player implements Comparable<Player> {
    private final String name;
    private final int score;

    public Player(String name, int score) {
        if(name == null) throw new IllegalArgumentException("name must not be null");
        if(name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        if(score < 0) throw new IllegalArgumentException("score must not be negative: " + score);
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }

    public int getScore() { return score; }

    //@Override
    //public int compareTo(Player other) { return Integer.compare(this.score, other.score); }

    // 発展
    @Override
    public int compareTo(Player other) {
        int result = Integer.compare(this.score, other.score);   // ① まずスコアで比べる

        if (result != 0) return result;                           // ② 差がついたらそれで決まり

        return this.name.compareTo(other.name);                   // ③ 同点なら名前で比べる
    }
    
    @Override
    public String toString() {
        return "Player{name=" + name + ", score=" + score + "}"; 
    }
}

public class RankLab {
    /*static class ByScoreDesc implements Comparator<Player> {
        @Override
        public int compare(Player a, Player b) { 
            return Integer.compare(b.getScore(), a.getScore());
        }
    }*/

    static class ByScoreDesc implements Comparator<Player> {
        @Override
        public int compare(Player a, Player b) {
            int result = Integer.compare(b.getScore(), a.getScore());

            if(result != 0) return result;

            return a.getName().compareTo(b.getName());
        }
    }

    static class ByName implements Comparator<Player> {
        @Override
        public int compare(Player a, Player b) {
            return a.getName().compareTo(b.getName());
        }
    }

    public static void main(String[] name) {
        List<Player> list = new ArrayList<>();
        list.add(new Player("Abe", 50));
        list.add(new Player("Ito", 70));
        list.add(new Player("Ueda", 50));   // Abe と同点

        System.out.println("--- A ---");
        Collections.sort(list);
        System.out.println("A-1 期待 [Player{name=Abe, score=50}, Player{name=Ueda, score=50}, Player{name=Ito, score=70}] / 実際 " + list);
        list.sort(new ByScoreDesc());
        System.out.println("A-2 期待 [Player{name=Ito, score=70}, Player{name=Abe, score=50}, Player{name=Ueda, score=50}] / 実際 " + list);
        list.sort(new ByName());
        System.out.println("A-3 期待 [Player{name=Abe, score=50}, Player{name=Ito, score=70}, Player{name=Ueda, score=50}] / 実際 " + list);
        System.out.println("A-4 期待 Player{name=Ito, score=70} / 実際 " + Collections.max(list));
        System.out.println("A-5 期待 Player{name=Abe, score=50} / 実際 " + Collections.min(list));

        System.out.println("--- B ---");
        TreeSet<Player> ts = new TreeSet<>(); 
        ts.addAll(list);
        System.out.println(ts.size()); 
        // 予想: 3 → 2
        System.out.println(ts);
        // 予想: 
        HashSet<Player> hs = new HashSet<>(list);
        System.out.println(hs.size());
        // 予想: 3
        TreeSet<Player> byName = new TreeSet<>(new ByName()); 
        byName.addAll(list);
        System.out.println(byName.size());
        // 予想: 3

        System.out.println("--- C ---");
        // 3行 → 4行
        try {
            new Player(null, 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Player("", 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Player("   ", 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new Player("a", -10);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
       
        System.out.println("--- A-6（追加） ---");
        List<Player> list2 = new ArrayList<>();
        list2.add(new Player("Ueda", 50));   // さっきと違い、Ueda を先に入れる
        list2.add(new Player("Ito", 70));
        list2.add(new Player("Abe", 50));
        list2.sort(new ByScoreDesc());
        System.out.println("A-6 期待 [Player{name=Ito, score=70}, Player{name=Abe, score=50}, Player{name=Ueda, score=50}] / 実際 " + list2);        
    }
}