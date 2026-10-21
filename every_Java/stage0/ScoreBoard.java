
import java.util.ArrayList;
import java.util.List;

class Student {
    private final String name;
    private final int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
}

public class ScoreBoard {
    private final List<Student> students = new ArrayList<>();

    void add(Student s) {
        students.add(s);
    }

    double average() {
        return students.stream()
            .mapToInt(s -> s.getScore())
            .average()
            .orElse(0.0);
    }

    Student highest() {
        Student best = students.get(0);

        for(Student s : students) {
            if(best.getScore() < s.getScore()) {
                best = s;
            }
        }
        return best;
    }

    public static void main(String[] args) {
        ScoreBoard board = new ScoreBoard();
        board.add(new Student("佐藤", 80));
        board.add(new Student("鈴木", 95));
        board.add(new Student("高橋", 60));

        System.out.println(board.average());          // 78.333...
        System.out.printf("%.1f%n", board.average()); // 78.3
        System.out.println(board.highest().getName()); // 鈴木
    }
}
