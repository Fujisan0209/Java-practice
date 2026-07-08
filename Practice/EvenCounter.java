public class EvenCounter {
    public static int countEven(int[] nums) {
        int count = 0;
        for(int i : nums) {
            if(i % 2 == 0) {
                count++;
            }
        }
        return count;
    } 

    public static void main(String[] args) {
        int[] nums = {12, 7, 4, 9, 20, 15, 8};
        int count = countEven(nums);
        
        System.out.println("Even count: " + count);
    }
}

