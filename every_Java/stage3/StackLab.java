
import java.util.Arrays;

class ArrayStack<T> {
    private Object[] items;
    private int size;

    public ArrayStack(int initialCapacity) {
        if(initialCapacity <= 0) throw new IllegalArgumentException("capacity must be positive: " + initialCapacity);
        size = 0;
        items = new Object[initialCapacity];
    }

    void push(T value) {
        if(value == null) throw new IllegalArgumentException("value must not be null");
        if(size == items.length) {
            items = Arrays.copyOf(items, items.length * 2);
        }
        items[size] = value; size++;  
    }

    @SuppressWarnings("unchecked")
    T peek() {
        if(isEmpty()) throw new IllegalStateException("stack is empty");
        return (T)items[size - 1];
    }

    T pop() {
        T item = peek();          // 空チェックもキャストも peek がやってくれる
        items[size - 1] = null;
        size--;
        return item;
    }

    int size() { return size; }

    boolean isEmpty() { return size == 0; }

    int capacity() { return items.length; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        int count = 0;
        for(Object i : items) {
            if(i == null) break;
            sb.append(i);count++;
            if(count < size) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}

public class StackLab {
    static boolean isBalanced(String s) {
        if(s == null) throw new IllegalArgumentException("s must not be null");
        if(s.isBlank()) return true;

        ArrayStack<Character> stack = new ArrayStack<>(4);

        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if(c == '(' || c == '[' || c == '{') {
                stack.push(c);
            } else if(c == ')' || c == ']' || c == '}') {
                if(stack.isEmpty()) return false;
                char open = stack.pop();
                if (!(open == '(' && c == ')' || open == '[' && c == ']' || open == '{' && c == '}')) {
                    return false;
                }
            } else {
                continue;
            }
        }
        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println("--- A ---");
        ArrayStack<String> s = new ArrayStack<>(2);
        s.push("a"); s.push("b");s.push("c");
        System.out.println("A-1 期待: [a, b, c] size=3 cap=4/実際: " + s.toString() + " size=" + s.size() + " cap=" + s.capacity());
        System.out.println("A-2 期待: c [a, b, c]/実際: " + s.peek() + " " + s.toString());
        System.out.println("A-3 期待: c [a, b]/実際: " + s.pop() + " " + s.toString());
        s.push("d"); s.push("e");s.push("f");
        System.out.println("A-4 期待: [a, b, d, e, f] size=5 cap=8/実際: " + s.toString() + " size=" + s.size() + " cap=" + s.capacity());
        System.out.println("A-5 (a[b]{c}) 期待: true  / 実際: " + isBalanced("(a[b]{c})"));
        System.out.println("A-5 ([)]      期待: false / 実際: " + isBalanced("([)]"));
        System.out.println("A-5 ((        期待: false / 実際: " + isBalanced("(("));
        System.out.println("A-5 \"\"        期待: true  / 実際: " + isBalanced(""));
        System.out.println("A-5 ())       期待: false / 実際: " + isBalanced("())"));
        System.out.println("A-5 ({)}      期待: false / 実際: " + isBalanced("({)}"));        
        
        System.out.println("--- B ---");
        ArrayStack<Integer> n = new ArrayStack<>(1);
        n.push(1000);
        Integer x = n.peek(); Integer y = n.pop();
        System.out.println("B-1 " + (x == y));
        // peekとpopは同じ場所をさしているから
        System.out.println("B-3 " + n.isEmpty() + " " + n);
        // popしたから中身はない
        ArrayStack<Integer> m = new ArrayStack<>(1);
        m.push(0); m.push(1); m.push(2); m.push(3); m.push(4);
        System.out.println("B-2 " + m.capacity());
        // lengthは2倍になるから1 → 2 → 4 → 8となる

        System.out.println("--- C ---");
        try {
            new ArrayStack<String>(0);
        } catch (IllegalArgumentException ex) {
            System.out.println("C-1 " + ex.getMessage());
        }
        ArrayStack<Integer> f = new ArrayStack<>(1);
        try {
            f.pop();
        } catch (IllegalStateException ex) {
            System.out.println("C-2 " + ex.getMessage());
        }
        try {
            f.push(null);
        } catch (IllegalArgumentException ex) {
            System.out.println("C-3 " + ex.getMessage());
        }
        try {
            isBalanced(null);
        } catch (IllegalArgumentException ex) {
            System.out.println("C-4 " + ex.getMessage());
        }

        System.out.println("--- E ---");
        ArrayStack<String> e = new ArrayStack<>(2);
        System.out.println("E-1 期待: true / 実際: " + e.isEmpty());
        try { e.pop(); } catch (IllegalStateException ex) { System.out.println("E-2 期待: stack is empty / 実際: " + ex.getMessage()); }
    }
}