
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

final class Player implements Comparable<Player> {
    private final String name;
    private final int score;

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }

    public int getScore() { return score; }

    public int compareTo(Player other) { return Integer.compare(this.score, other.score); }
    
    public String toString() {
        return getClass() + "{name=" + name + ", score=" + score; 
    }
}

public class RankLab {
    static class ByScoreDesc implements Comparator<Player> {
        public int compare(Player a, Player b) { 
            return Integer.compare(a.getScore(), b.getScore());
        }
    }

    static class ByName implements Comparator<Player> {
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
        System.out.println("期待値: ");
        System.out.println("期待値: ");
        System.out.println("期待値: ");
        System.out.println("期待値: ");
        System.out.println("期待値: ");

        System.out.println("--- B ---");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        System.out.println("--- C ---");
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
            new Player("a", -10);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}