public class FizzBuzz {
    public static void main(String[] args) {
        fizzbuzz();
    }

    static void fizzbuzz() { // FizzBuzz -> fizzbuzz
        for(int i = 1; i < 31; i++) {
            if(i % 15 == 0) {
                System.out.println("FizzBuzz");
            } else if(i % 5 == 0) {
                System.out.println("Buzz");
            } else if(i % 3 == 0) {
                System.out.println("Fizz");
            } else {
                System.out.println(i);
            }
        }
    }
}