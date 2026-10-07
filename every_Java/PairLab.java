
import java.util.List;
import java.util.Objects;
import java.util.Optional;

final class Pair<A, B> {
    private final A first;
    private final B second;

    public Pair(A first, B second) {
        if(first == null) throw new IllegalArgumentException("first must not be null");
        if(second == null) throw new IllegalArgumentException("second must not be null");
        this.first = first;
        this.second = second;
    }

    public A first() { return first; }

    public B second() { return second; }

    public  Pair<B, A> swap() {
        return new Pair<>(second, first);
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> other = (Pair<?, ?>) o;
        return first.equals(other.first()) 
            && second.equals(other.second());
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}

public class PairLab {
    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }

    public static <T> Pair<T, T> twin(T value) {
        return new Pair<>(value, value);
    }

    public static <K, V> Optional<V> lookup(List<Pair<K, V>> pairs, K key) {
        if(key == null) throw new IllegalArgumentException("key must not be null");
        for(Pair<K, V> p : pairs) {
            if(p.first().equals(key)) {
                return Optional.of(p.second());
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        Pair<String, Integer> p = of("apple", 120);
        List<Pair<String, Integer>> menu = List.of(of("apple", 120), of("banana", 80), of("cherry", 300));
        System.out.println("期待: A-1: (apple, 120)/ 実際: " + p);
        System.out.println("期待: A-2: (120, apple)/ 実際: " + p.swap());
        System.out.println("期待: A-3: (hi, hi)/ 実際: " + twin("hi"));
        System.out.println("期待: A-4: 80/ 実際: " + lookup(menu, "banana").orElse(-1));
        System.out.println("期待: A-5: -1/ 実際: " + lookup(menu, "durian").orElse(-1));

        Pair<Integer, Integer> t = twin(1000);
        Pair<Integer, Integer> u = of(1000, 1000);

        System.out.println("B-1: " + (t.first() == t.second()));
        // 同じ値を入れているから
        System.out.println("B-2: " + (u.first() == u.second()));
        // アドレスが違う
        System.out.println("B-3: " + p.equals(of("apple", 120)));
        // 中身は同じ
        System.out.println("B-4: " + p.swap().swap().equals(p));
        // 2回swapして元に戻り中身は同じ

        System.out.println("---C ---");
        // 3行
        try {
            of(null, 1);
        } catch (IllegalArgumentException e) {
            System.out.println("C-1: " + e.getMessage());
        }
        try {
            twin(null);
        } catch (IllegalArgumentException e) {
            System.out.println("C-2: " + e.getMessage());
        }
        try {
            lookup(menu, null);
        } catch (IllegalArgumentException e) {
            System.out.println("C-3: " + e.getMessage());
        }

        // D-1
        // Pair<String, Integer> bad = of(1, "a");

        // D-2
        // Integer x = of("a", 1).first();
    }
}