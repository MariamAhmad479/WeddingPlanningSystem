package service.payment;

/**
 * Strategy Pattern — Context class for payment processing.
 *
 * Holds a PaymentStrategy and delegates payment execution to it.
 * The strategy can be switched at runtime via setStrategy().
 */
public class PaymentContext {

    private PaymentStrategy strategy;

    public PaymentContext(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /** Change the payment method at runtime. */
    public void setStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }

    /** Execute the payment using the current strategy. */
    public boolean executePayment(String userId, double amount) {
        System.out.println("\n--- Processing Payment ---");
        boolean result = strategy.pay(userId, amount);
        System.out.println("--- Payment " + (result ? "Succeeded" : "Failed") + " ---\n");
        return result;
    }
}
