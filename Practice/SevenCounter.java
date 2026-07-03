public class SevenCounter {
    public static void main(String[] args) {
        for(int i = 1; i < 31; i++) {
            if(i % 7 == 0) {
                System.out.println("Seven");
            } else {
                System.out.println(i);
            }
        }
    }
}