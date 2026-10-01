import java.util.*;

class Playlist {
    private final String name;
    private final List<String> titles = new ArrayList<>();

    public Playlist(String name) {
        if(name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        this.name = name;
    }

    public void add(String title) {
        if(title == null || title.isBlank()) throw new IllegalArgumentException("title must not be blank");
        titles.add(title);
    }

    public int size() { return titles.size(); }

    static class Stats {
        private final int count;
        private final int longest;

        public Stats(int count, int longest) {
            this.count = count;
            this.longest = longest;
        }

        public String toString() {
            return "count=" + count + " longest=" + longest;
        }
    }

    public Stats stats() {
        int count = titles.size();
        int longest = 0;
        for(String s : titles) {
            if(longest < s.length()) {
                longest = s.length();
            }
        }
        return new Stats(count, longest);
    }

    class Cursor {
        private int index = 0;
        public boolean hasNext() {
            return index < titles.size();
        }

        public String next() {
            if(! hasNext()) throw new NoSuchElementException("no more titles");
            StringBuilder s = new StringBuilder(name + "#" +  (index + 1) + " " + titles.get(index));
            index++;
            return s.toString();
        }
    }

    public Cursor cursor() { return new Cursor(); }
    
    public List<String> sortedByLength() {
        List<String> result = new ArrayList<>(titles);
        result.sort(new Comparator<String>() {
            @Override
            public int compare(String a, String b) {
                return Integer.compare(a.length(), b.length());
            }
        });
        return result;
    }
}

public class NestLab {
    public static void main(String[] args) {
        Playlist p = new Playlist("morning");
        p.add("Blue");
        p.add("Sunrise");
        p.add("Go");

        System.out.println("--- A ---");
        System.out.println("A-1 期待: 3/ 実際: " + p.size());
        System.out.println("A-2 期待: count=3 longest=7/ 実際: " + p.stats());
        Playlist.Cursor c = p.cursor();
        System.out.println("A-3");
        while(c.hasNext()) {
            System.out.println(c.next());
        }
        System.out.println("A-4 期待: [Go, Blue, Sunrise]/ 実際: " + p.sortedByLength());
        System.out.println("A-5 期待: morning#1 Blue/ 実際: " + p.cursor().next());

        System.out.println("--- B ---");
        System.out.println("B-1 " + new Playlist.Stats(0, 0));
        // 予想: count=0 longest=0
        Playlist.Cursor c1 = p.cursor();
        Playlist.Cursor c2 = p.cursor();
        c1.next();
        c1.next();
        System.out.println("B-2 " + c2.next());
        // 予想: morning#1 Blue
        Playlist.Cursor c3 = p.cursor();
        p.add("Night");
        System.out.println("B-3");
        while(c3.hasNext()) {
            System.out.println(c3.next());
        }
        // 予想: morning#1 Blue
              // morning#2 Sunrise
              // morning#3 Go
              // morning#4 Night

        System.out.println("--- C ---");
        try {
            new Playlist(" ");
        } catch(IllegalArgumentException e) {
            System.out.println("C-1 " + e.getMessage());
        }
        try {
            p.add("");
        } catch(IllegalArgumentException e) {
            System.out.println("C-2 " + e.getMessage());
        }

        Playlist p1 = new Playlist("morning");
        p1.add("Go");
        Playlist.Cursor c4 = p1.cursor();

        try {
            c4.next();
            c4.next();
        } catch(NoSuchElementException e) {
            System.out.println("C-3 " + e.getMessage());
        }

        // Playlist.Cursor d = new Playlist.Cursor();
    }
}