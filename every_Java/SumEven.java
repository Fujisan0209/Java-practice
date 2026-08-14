public class SumEven {
    public static void main(String[] args) {
        int[] nums = {12, 7, 4, 9, 20, 15, 8};
        int sum = 0;

        for(int i : nums) {
            if(i % 2 == 0) {
                sum += i;
            }
        }

        System.out.println("Sum of even: " + sum);
    }
}