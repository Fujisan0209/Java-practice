import java.util.ArrayDeque;
import java.util.Deque;

public class BracketLab {
    static char pairOf(char close) {
        char pair = switch(close) {
            case ')' -> '(';
            case '}' -> '{';
            case ']' -> '[';
            default -> throw new IllegalArgumentException("unknown");
        };
        return pair;
    }

    static int firstErrorIndex(String s) {
        if(s == null) throw new IllegalArgumentException("s must not be null");
        Deque<Integer> stack = new ArrayDeque<>();
        for(int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(c == '(' || c == '{' || c == '[') {
                stack.push(i);
            }

            if(c == ')' || c == '}' || c == ']') {
                if(stack.isEmpty()) return i;
                char pair = s.charAt(stack.pop());
                if(pair != pairOf(c)) {
                    return i;
                }
            }
        }
        if(stack.size() > 0) return stack.pop();
        return -1;
    }

    static boolean isBalanced(String s) {
        return firstErrorIndex(s) == -1;
    }

    // ---- 区画B 用の「失敗作」（さわらない） ----
    static boolean depthOnly(String s) {
        int depth = 0;
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') depth++;
            else if (c == ')' || c == ']' || c == '}') {
                depth--;
                if (depth < 0) return false;
            }
        }
        return depth == 0;
    }

    static boolean popOnly(String s) {
        Deque<Character> stack = new ArrayDeque<>();
        for (char c : s.toCharArray()) {
            if (c == '(' || c == '[' || c == '{') stack.push(c);
            else if (c == ')' || c == ']' || c == '}') {
                if (stack.isEmpty()) return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
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
        System.out.println("=== 区画A：自動照合 ===");
        check("A-1", pairOf(')'), "(");
        check("A-2", pairOf('}'), "{");
        checkThrows("A-3", () -> pairOf('a'));

        check("A-4", firstErrorIndex("(1+2)*[3-{4/2}]"), "-1");
        check("A-5", firstErrorIndex(""), "-1");
        check("A-6", firstErrorIndex("no brackets"), "-1");
        check("A-7", firstErrorIndex("(1+2))"), "5");
        check("A-8", firstErrorIndex("([)]"), "2");
        check("A-9", firstErrorIndex(")("), "0");
        check("A-10", firstErrorIndex("{(a"), "1");
        check("A-11", firstErrorIndex("f(x[0]"), "1");

        check("A-12", isBalanced("{[()]}"), "true");
        check("A-13", isBalanced("{[(])}"), "false");
        check("A-14", isBalanced("if (a[i] > 0) { x(); }"), "true");

        checkThrows("A-15", () -> firstErrorIndex(null));
        checkThrows("A-16", () -> isBalanced(null));

        System.out.println("OK=" + ok + " / NG=" + ng + "（全16項目）");

        System.out.println();
        System.out.println("=== 区画B：実行して、理由を1行で ===");
        System.out.println("B-1 数だけ数える版で \"([)]\": " + depthOnly("([)]")
                + "  （正解版: " + isBalanced("([)]") + "）");
        // 理由： ペアは確認していないから
        // 理由：数（depth）しか覚えていないので、どの種類の括弧がどの順番で開いたかがわからない

        System.out.println("B-2 pop だけする版で \"(]\": " + popOnly("(]")
                + "  （正解版: " + isBalanced("(]") + "）");
        // 理由： ペアは確認していないから
        // 理由：pop() の戻り値を捨てているので、取り出した括弧と閉じ括弧を比べていない
    }
}