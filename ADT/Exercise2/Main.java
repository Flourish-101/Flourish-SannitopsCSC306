package ADT.Exercise2;

public class Main {
    public static void main(String[] args) {
        BankAccount account = new OverdraftAccount(100);
        account.deposit(50);
        account.withdraw(500);  // allowed, within overdraft limit
        account.withdraw(200);  // exceeded
    }
}
