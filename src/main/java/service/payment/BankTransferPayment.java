package service.payment;

/**
 * Strategy Pattern — Concrete Strategy: Bank Transfer payment.
 */
public class BankTransferPayment implements PaymentStrategy {

    private final String bankAccountNumber;

    public BankTransferPayment(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    @Override
    public boolean pay(String userId, double amount) {
        System.out.println("[BankTransfer] Initiating EGP " + amount
                + " transfer from account " + bankAccountNumber + " for user " + userId);
        System.out.println("[BankTransfer] Transfer confirmed — funds received ✓");
        return true;
    }
}
