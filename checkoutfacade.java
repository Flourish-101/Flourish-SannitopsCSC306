class Inventory {
    boolean checkStock(String productId) {
        return true;
    }

    void reserve(String productId) {
        System.out.println("Reserved " + productId);
    }

    void release(String productId) {
        System.out.println("Released " + productId);
    }
}

class Payment {
    boolean charge(String userId, double amount) {
        return true;
    }

    void refund(String userId, double amount) {
        System.out.println("Refunded " + amount);
    }
}

class Shipping {
    String createLabel(String address) {
        return "TRK" + System.currentTimeMillis();
    }

    void schedulePickup(String label) {
        System.out.println("Pickup scheduled for " + label);
    }

    boolean isAvailable() {
        return true;
    }
}

class Email {
    void send(String to, String subject, String body) {
        System.out.println("Email sent to " + to);
    }
}

class OrderResult {
    private final boolean success;
    private final String trackingNumber;
    private final String message;

    public OrderResult(boolean success, String trackingNumber, String message) {
        this.success = success;
        this.trackingNumber = trackingNumber;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getMessage() {
        return message;
    }
}

public class CheckoutFacade {

    private Inventory inventory = new Inventory();
    private Payment payment = new Payment();
    private Shipping shipping = new Shipping();
    private Email email = new Email();

    public OrderResult checkout(String userId, String productId,
                                double price, String address) {

        // 1. Check stock
        if (!inventory.checkStock(productId)) {
            return new OrderResult(false, null, "Out of stock");
        }

        // 2. Charge FIRST (important requirement)
        if (!payment.charge(userId, price)) {
            return new OrderResult(false, null, "Payment failed");
        }

        // 3. Reserve AFTER successful payment
        inventory.reserve(productId);

        // 4. Check shipping
        if (!shipping.isAvailable()) {
            payment.refund(userId, price);
            inventory.release(productId);
            return new OrderResult(false, null, "Shipping unavailable");
        }

        // 5. Create shipment
        String trackingNumber = shipping.createLabel(address);
        shipping.schedulePickup(trackingNumber);

        // 6. Send confirmation
        email.send(userId, "Order Confirmed",
                "Tracking number: " + trackingNumber);

        return new OrderResult(true, trackingNumber, "Order successful");
    }

    // Test
    public static void main(String[] args) {
        CheckoutFacade facade = new CheckoutFacade();

        OrderResult result = facade.checkout(
                "user@email.com",
                "PROD1",
                10000.0,
                "Lagos, Nigeria"
        );

        System.out.println("Success: " + result.isSuccess());
        System.out.println("Tracking: " + result.getTrackingNumber());
        System.out.println("Message: " + result.getMessage());
    }
}