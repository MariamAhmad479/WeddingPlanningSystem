package service.payment;

public class StripePaymentAdapter implements PaymentProcessor {
    private StripePaymentAPI stripeAPI;

    public StripePaymentAdapter(StripePaymentAPI stripeAPI) {
        this.stripeAPI = stripeAPI;
    }

    @Override
    public boolean processPayment(String brideId, double amount) {
        // Simulate formatting the internal brideId to a Stripe email
        String customerEmail = brideId + "@example.com";
        
        // Stripe API expects the amount in cents, our internal system uses dollars/EGP
        double amountInCents = amount * 100;
        
        System.out.println("Adapter converting PaymentProcessor request to StripePaymentAPI format...");
        
        // Delegate to the Adaptee
        return stripeAPI.makePayment(customerEmail, amountInCents);
    }
}
