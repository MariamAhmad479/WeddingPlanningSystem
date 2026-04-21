package service.payment;

/**
 * Strategy Pattern — Concrete Strategy: Fawry mobile wallet payment.
 */
public class FawryPayment implements PaymentStrategy {

    private final String phoneNumber;

    public FawryPayment(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean pay(String userId, double amount) {
        System.out.println("[Fawry] Sending EGP " + amount
                + " payment request to phone " + phoneNumber + " for user " + userId);
        System.out.println("[Fawry] OTP verified — payment complete ✓");
        return true;
    }
}
