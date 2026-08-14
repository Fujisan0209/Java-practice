public class MovieDemo {
    public static void main(String[] args) {
        Movie a = new Movie("Inception", 148);
        Movie b = new Movie("Up", 96);

        System.out.println(a.getTitle() + ": " + a.getMinutes() + " min (long: " + a.isLong() + ")");
        System.out.println(b.getTitle() + ": " + b.getMinutes() + " min (long: " + b.isLong() + ")");
    }
}