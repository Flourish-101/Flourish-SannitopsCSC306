class TaxCalculator {
    double calculateTax(String state, double amount) {
        if (state.equals("CA")) {
            return amount * 0.08;
        }
        return 0;
    }
}

class Logger {
    void log(String userId, boolean success) {
        System.out.println("LOG: " + userId + " | success=" + success + " | time=" + System.currentTimeMillis());
    }
}

public class CheckoutFacadeExtra {

    private Inventory inventory = new Inventory();
    private Payment payment = new Payment();
    private Shipping shipping = new Shipping();
    private Email email = new Email();

    // NEW
    private TaxCalculator taxCalculator = new TaxCalculator();
    private Logger logger = new Logger();

    public OrderResult checkout(String userId, String productId,
                                double price, String address, String state) {

        double tax = taxCalculator.calculateTax(state, price);
        double total = price + tax;

        if (!inventory.checkStock(productId)) {
            logger.log(userId, false);
            return new OrderResult(false, null, "Out of stock");
        }

        if (!payment.charge(userId, total)) {
            logger.log(userId, false);
            return new OrderResult(false, null, "Payment failed");
        }

        inventory.reserve(productId);

        if (!shipping.isAvailable()) {
            payment.refund(userId, total);
            inventory.release(productId);
            logger.log(userId, false);
            return new OrderResult(false, null, "Shipping unavailable");
        }

        String trackingNumber = shipping.createLabel(address);
        shipping.schedulePickup(trackingNumber);

        email.send(userId, "Order Confirmed",
                "Total: " + total + " | Tracking: " + trackingNumber);

        logger.log(userId, true);

        return new OrderResult(true, trackingNumber, "Order successful");
    }
}