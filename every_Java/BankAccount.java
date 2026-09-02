public class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        if(initialBalance < 0) throw new IllegalArgumentException("初期残高が負です: " + initialBalance);
        else balance = initialBalance;
    }

    public void deposit(int amount) {
        if(amount <= 0) throw new IllegalArgumentException("入金額は1以上にしてください: " + amount);
        else balance += amount;
    }

    public void withdraw(int amount) {
        if(amount <= 0) throw new IllegalArgumentException("出金額は1以上にしてください: " + amount);
        else if(balance < amount) throw new IllegalStateException("残高不足です: 残高=" + balance + ", 出金=" + amount);
        else balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        BankAccount b = new BankAccount(1000);
        System.out.println("残高: " + b.getBalance());

        b.deposit(500);
        System.out.println("入金後: " + b.getBalance());

        b.withdraw(300);
        System.out.println("出金後: " + b.getBalance());

        try {
            b.deposit(0);  
        } catch (IllegalArgumentException e) {
            System.out.println("エラー: " + e.getMessage());
        }

        try {
            b.withdraw(5000);
        } catch (IllegalStateException e) {
            System.out.println("エラー: " + e.getMessage());
        }
     
        System.out.println("残高: " + b.getBalance());

        try {
            BankAccount b2 =  new BankAccount(-100);
        } catch (IllegalArgumentException e) {
            System.out.println("エラー: " + e.getMessage());
        }      
    }
}