public class DogDemo {
    public static void main(String[] args) {
        Dog d1 = new Dog("Pochi", 1);
        Dog d2 = new Dog("Max", 5);

        d1.Bark();
        d2.Bark();

        System.out.println(d1.getName() + " is puppy: " + d1.isPuppy());
        System.out.println(d2.getName() + " is puppy: " + d2.isPuppy());   
    }
}