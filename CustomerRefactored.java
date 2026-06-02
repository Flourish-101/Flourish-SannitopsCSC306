
public class CustomerRefactored {

  
    public static void validateCustomer(Customer c) {
        if (c.name == null || c.name.isBlank())
            throw new IllegalArgumentException("Customer name must not be empty.");
        if (c.address == null || c.address.isBlank())
            throw new IllegalArgumentException("Customer address must not be empty.");
        if (c.orderCount < 0)
            throw new IllegalArgumentException("Order count must be non-negative.");
        if (c.orders == null || c.orders.length < c.orderCount)
            throw new IllegalArgumentException("Orders array is too short for the given order count.");
        for (int i = 0; i < c.orderCount; i++) {
            if (c.orders[i] < 0)
                throw new IllegalArgumentException(
                    "Order amount at index " + i + " must be non-negative.");
        }
        if (c.customerType != 0 && c.customerType != 1 && c.customerType != 2)
            throw new IllegalArgumentException(
                "Customer type must be 0 (standard), 1 (silver), or 2 (gold).");
    }

  
    public static double orderTotal(Customer c) {
        double sum = 0;
        for (int i = 0; i < c.orderCount; i++) {
            sum += c.orders[i];
        }
        return sum;
    }


    public static double discountRate(int customerType) {
        if (customerType == 1) return 0.10;   // silver – 10%
        if (customerType == 2) return 0.20;   // gold   – 20%
        return 0.0;                            // standard
    }

    
    public static double applyDiscount(double subtotal, double rate) {
        return subtotal - subtotal * rate;
    }

    
    public static String buildGreeting(Customer c, double total) {
        String msg = "Hello " + c.name + " of " + c.address
                   + ", your total is " + total;
        if (c.isVip) msg += " (VIP)";
        return msg;
    }

   
    public static void sendNotification(Customer c, String message) {
        System.out.println(message);
        if (c.email != null && !c.email.isBlank()) {
            sendEmail(c.email, message);
        }
    }

    
    public static void sendEmail(String email, String message) {
        System.out.println("[EMAIL to " + email + "]: " + message);
    }

    
    public static double processCustomer(Customer c) {
        validateCustomer(c);

        double subtotal = orderTotal(c);
        double rate     = discountRate(c.customerType);
        double total    = applyDiscount(subtotal, rate);

        String greeting = buildGreeting(c, total);
        sendNotification(c, greeting);

        return total;  // caller does: c.balance = processCustomer(c);
    }

    
    public static void main(String[] args) {
        double[] orders = {150.0, 200.0, 50.0};

        Customer alice = new Customer(
            "Alice",          
            "Lagos",           
            0.0,              
            2,                 
            "alice@email.com", 
            true,             
            orders,         
            3                 
        );

        
        alice.balance = processCustomer(alice);
        System.out.println("Updated balance: " + alice.balance);
    }
}
