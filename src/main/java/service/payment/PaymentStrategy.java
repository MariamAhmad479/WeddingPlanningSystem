package service.payment;

/**
 * Strategy Pattern — Strategy interface for payment processing.
 *
 * Different implementations represent different payment methods
 * (Credit Card, Fawry, Bank Transfer), swappable at runtime.
 */
public interface PaymentStrategy {

    /**
     * Process a payment for the given user and amount.
     *
     * @param userId the bride / user making the payment
     * @param amount the payment amount in EGP
     * @return true if payment succeeded
     */
    boolean pay(String userId, double amount);
}
