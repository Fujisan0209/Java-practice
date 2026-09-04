
import java.util.*;

class Book {
    private final String title;
    private final String author;
    private final int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        return title + "(" + author + ", " + year + ")";
    }
}

public class Library {
    private final List<Book> books = new ArrayList<>();

    public int size() {
        return books.size();
    }

    public boolean hasTitle(String title) {
        return books.stream()
            .map(b -> b.getTitle())
            .anyMatch(b -> b.equals(title));
    }
    
    public void add(Book book) {
        if(book == null) throw new IllegalArgumentException("本がnullです");
        if(hasTitle(book.getTitle())) throw new IllegalStateException("登録済み: " + book.getTitle());
        
        books.add(book);
    }

    public List<Book> findByAuthor(String author) {
        return books.stream()
            .filter(b -> b.getAuthor().equals(author))
            .toList();       
    }

    public long countPublishedAfter(int year) {
        return books.stream()
            .filter(b -> b.getYear() > year)
            .count();
    }

    public double averageYear() {
        return books.stream()
            .mapToInt(b -> b.getYear())
            .average()
            .orElse(0.0);
    }

    public void addAll(List<Book> newBooks) {

        for(Book b : newBooks) {
            if(b == null) throw new IllegalArgumentException("本がnullです");
            if(hasTitle(b.getTitle())) throw new IllegalStateException("登録済み: " + b.getTitle());
        }
        for(Book b : newBooks) {
            books.add(b);
        }
    }
    public static void main(String[] args) {
        Library lib = new Library();
        lib.add(new Book("Java入門", "佐藤", 2018));
        lib.add(new Book("アルゴリズム図鑑", "鈴木", 2021));
        lib.add(new Book("Java実践", "佐藤", 2023));
        lib.add(new Book("C言語の教科書", "田中", 2015));

        System.out.println("size = " + lib.size());
        System.out.println("hasTitle(Java入門) = " + lib.hasTitle("Java入門"));
        System.out.println("hasTitle(Python入門) = " + lib.hasTitle("Python入門"));
        System.out.println("findByAuthor(佐藤) = " + lib.findByAuthor("佐藤"));
        System.out.println("findByAuthor(田中) = " + lib.findByAuthor("山田"));
        System.out.println("countPublishedAfter(2018) = " + lib.countPublishedAfter(2018));
        System.out.println("averageYear = " + lib.averageYear());

        try {
            lib.add(new Book("Java入門", "誰か", 2020));
        } catch (IllegalStateException e) {
            System.out.println("error: " + e.getMessage());
        }

        try {
            lib.add(null);
        } catch (IllegalArgumentException e) {
            System.out.println("error: " + e.getMessage());
        }

        System.out.println("size = " + lib.size());

        Library empty = new Library();
        System.out.println("averageYear = " + empty.averageYear());

    }
}

/*
import java.util.*;

class Book {
    private final String title;
    private final String author;
    private final int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle()  { return title; }
    public String getAuthor() { return author; }
    public int    getYear()   { return year; }

    @Override
    public String toString() {
        return title + "(" + author + ", " + year + ")";
    }
}

public class Library {
    private final List<Book> books = new ArrayList<>();

    public int size() {
        return books.size();
    }

    public boolean hasTitle(String title) {
        return books.stream()
            .map(b -> b.getTitle())
            .anyMatch(t -> t.equals(title));     // ← b から t へ（型が変わっているので）
    }

    public void add(Book book) {
        if (book == null) throw new IllegalArgumentException("本がnullです");
        if (hasTitle(book.getTitle())) throw new IllegalStateException("登録済み: " + book.getTitle());
        books.add(book);
    }

    public List<Book> findByAuthor(String author) {
        return books.stream()
            .filter(b -> b.getAuthor().equals(author))
            .toList();
    }

    public long countPublishedAfter(int year) {
        return books.stream()
            .filter(b -> b.getYear() > year)
            .count();
    }

    public double averageYear() {
        return books.stream()
            .mapToInt(b -> b.getYear())
            .average()
            .orElse(0.0);
    }

    // 1冊でも問題があれば、1冊も追加しない
    public void addAll(List<Book> newBooks) {
        if (newBooks == null) throw new IllegalArgumentException("リストがnullです");

        // 検査ずみの新刊タイトルを覚えておく箱（リスト内の重複を見つけるため）
        Set<String> seen = new HashSet<>();

        for (Book b : newBooks) {
            if (b == null) throw new IllegalArgumentException("本がnullです");
            if (hasTitle(b.getTitle())) throw new IllegalStateException("登録済み: " + b.getTitle());
            if (seen.contains(b.getTitle())) throw new IllegalStateException("リスト内で重複: " + b.getTitle());
            seen.add(b.getTitle());
        }

        // ここに来た時点で全冊が安全だと分かっている
        books.addAll(newBooks);
    }

    public static void main(String[] args) {
        Library lib = new Library();
        lib.add(new Book("Java入門", "佐藤", 2018));
        lib.add(new Book("アルゴリズム図鑑", "鈴木", 2021));
        lib.add(new Book("Java実践", "佐藤", 2023));
        lib.add(new Book("C言語の教科書", "田中", 2015));

        System.out.println("size = " + lib.size() + "  (期待 4)");
        System.out.println("hasTitle(Java入門) = " + lib.hasTitle("Java入門") + "  (期待 true)");
        System.out.println("hasTitle(Python入門) = " + lib.hasTitle("Python入門") + "  (期待 false)");
        System.out.println("findByAuthor(佐藤) = " + lib.findByAuthor("佐藤")
                + "  (期待 [Java入門(佐藤, 2018), Java実践(佐藤, 2023)])");
        System.out.println("findByAuthor(山田).size = " + lib.findByAuthor("山田").size() + "  (期待 0)");
        System.out.println("countPublishedAfter(2018) = " + lib.countPublishedAfter(2018) + "  (期待 2)");
        System.out.println("averageYear = " + lib.averageYear() + "  (期待 2019.25)");

        try {
            lib.add(new Book("Java入門", "誰か", 2020));
            System.out.println("★例外が出なかった＝バグ");
        } catch (IllegalStateException e) {
            System.out.println("ISE: " + e.getMessage() + "  (期待 登録済み: Java入門)");
        }

        try {
            lib.add(null);
            System.out.println("★例外が出なかった＝バグ");
        } catch (IllegalArgumentException e) {
            System.out.println("IAE: " + e.getMessage() + "  (期待 本がnullです)");
        }

        System.out.println("size = " + lib.size() + "  (期待 4／例外のあとも壊れていない)");

        Library empty = new Library();
        System.out.println("averageYear = " + empty.averageYear() + "  (期待 0.0)");

        System.out.println("--- addAll ---");

        Library a1 = new Library();
        a1.add(new Book("既存本", "田中", 2010));
        a1.addAll(List.of(new Book("新書A", "山田", 2024), new Book("新書B", "山田", 2025)));
        System.out.println("size = " + a1.size() + "  (期待 3)");

        Library a2 = new Library();
        a2.add(new Book("既存本", "田中", 2010));
        try {
            a2.addAll(List.of(new Book("新書A", "山田", 2024), new Book("既存本", "誰か", 2024)));
            System.out.println("★例外が出なかった＝バグ");
        } catch (IllegalStateException e) {
            System.out.println("ISE: " + e.getMessage() + "  (期待 登録済み: 既存本)");
        }
        System.out.println("size = " + a2.size() + "  (期待 1／新書Aも入っていない＝原子性)");

        Library a3 = new Library();
        try {
            a3.addAll(List.of(new Book("新書A", "山田", 2024), new Book("新書A", "山田", 2024)));
            System.out.println("★例外が出なかった＝バグ");
        } catch (IllegalStateException e) {
            System.out.println("ISE: " + e.getMessage() + "  (期待 リスト内で重複: 新書A)");
        }
        System.out.println("size = " + a3.size() + "  (期待 0)");
    }
}
*/