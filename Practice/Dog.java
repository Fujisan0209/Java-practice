public class Dog {
    private String name;
    private int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void Bark() {
        System.out.println(this.getName() + ": Wan!");
    }

    public boolean isPuppy() {
        if(this.getAge() < 2) {
            return true;
        } else {
            return false;
        }
    }
}