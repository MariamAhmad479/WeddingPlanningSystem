package service.payment;

/**
 * This simulates an external 3rd-party library that we cannot change.
 * The system acts as the Adaptee.
 */
public class StripePaymentAPI {
    public boolean makePayment(String customerEmail, double chargeAmountInCents) {
        System.out.println("Processing external Stripe payment for " + customerEmail + " amount: " + chargeAmountInCents + " cents.");
        return true;
    }
}
