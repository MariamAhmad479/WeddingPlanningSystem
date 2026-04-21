package service.payment;

/**
 * Strategy Pattern — Concrete Strategy: Credit Card payment.
 */
public class CreditCardPayment implements PaymentStrategy {

    private final String cardNumber;

    public CreditCardPayment(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(String userId, double amount) {
        String maskedCard = "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
        System.out.println("[CreditCard] Charging EGP " + amount
                + " to card " + maskedCard + " for user " + userId);
        System.out.println("[CreditCard] Payment authorized ✓");
        return true;
    }
}
