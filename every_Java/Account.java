public abstract class Account {
    protected String owner;
    protected int balance;

    public Account(String owner, int balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public void deposit(int amount) {
        if(amount <= 0) {
            throw new IllegalArgumentException("不正な金額 : " + amount);
        }
        balance += amount;
    }

    public abstract boolean withdraw(int amount);

    public abstract String getTypeName();

    public String toString() {
        System.out.println("[" + this.getTypeName() + "] " + this.owner + " 残高:" + this.balance + "円");
    }
}