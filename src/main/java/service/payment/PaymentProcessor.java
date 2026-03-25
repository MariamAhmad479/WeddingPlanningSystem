package service.payment;

public interface PaymentProcessor {
    boolean processPayment(String brideId, double amount);
}
