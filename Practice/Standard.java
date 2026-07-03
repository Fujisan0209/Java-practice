public class Standard {
    public static void main(String[] args) {
        String word = "apple,banana,cherry,date";
        String[] words = word.split(",");

        System.out.println("=== Words ===");
        for(String w : words) {
            System.out.println(w);
        }

        System.out.println("=== Count ===");
        System.out.println(words.length);
        System.out.println("=== Upper ===");
        for(String w : words) {
            System.out.println(w.toUpperCase());
        }

    }
}