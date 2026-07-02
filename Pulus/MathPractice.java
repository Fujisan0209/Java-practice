public class MathPractice {
    public static void main(String[] args) {
        int a = 17, b = 42;

        System.out.println("=== Max/Min ===");
        System.out.println("max: " + Math.max(a, b));
        System.out.println("min: " + Math.min(a, b));

        System.out.println("=== Abs ===");
        System.out.println(Math.abs(-25));

        System.out.println("=== Power ===");
        System.out.println((int)Math.pow(2, 8));

        System.out.println("=== Sqrt ===");
        System.out.println((int)Math.sqrt(144));

        System.out.println("=== Dice ===");
        for(int i = 0; i < 3; i++) {
            System.out.println((int)(Math.random() * 6) + 1);
        }
    }
}