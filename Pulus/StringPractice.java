public class StringPractice {
    public static void main(String[] args) {
        String text = "Java,Python,C,Ruby,Go";
        String name = "  Ryotaro Tomita  ";

        int c = 0;
        String[] texts = text.split(",");
        System.out.println("=== Languages ===");
        for(String s : texts) {
            System.out.println(s);
            c++;
        }

        System.out.println("=== Count ===");
        System.out.println(c);

        System.out.println("=== Trimmed ===");
        String n = name.trim();
        System.out.println(n);

        System.out.println("=== Upper ===");
        System.out.println(n.toUpperCase());

        System.out.println("=== Has Python? ===");
        System.out.println(text.contains("Python"));
        
    }
}