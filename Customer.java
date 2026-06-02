/**
 * Customer.java
 * Data class representing a customer. Replaces the 8-parameter list
 * in the original processCustomer routine.
 */
public class Customer {
    public String   name;
    public String   address;
    public double   balance;
    public int      customerType;  // 0 = standard, 1 = silver, 2 = gold
    public String   email;
    public boolean  isVip;
    public double[] orders;
    public int      orderCount;

    public Customer(String name, String address, double balance,
                    int customerType, String email, boolean isVip,
                    double[] orders, int orderCount) {
        this.name         = name;
        this.address      = address;
        this.balance      = balance;
        this.customerType = customerType;
        this.email        = email;
        this.isVip        = isVip;
        this.orders       = orders;
        this.orderCount   = orderCount;
    }
}
