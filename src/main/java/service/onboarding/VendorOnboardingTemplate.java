package service.onboarding;

/**
 * Template Method Pattern — Abstract class defining the skeleton
 * for onboarding any vendor type into the wedding planning platform.
 *
 * The template method onboard() is final. Subclasses override
 * performSpecificCheck() to supply vendor-type-specific validation.
 */
public abstract class VendorOnboardingTemplate {

    /**
     * Template method — defines the invariant onboarding algorithm.
     */
    public final void onboard(String vendorName) {
        System.out.println("\n--- Starting onboarding for: " + vendorName + " ---");
        validateCredentials(vendorName);
        performSpecificCheck(vendorName);
        createProfile(vendorName);
        setupAvailabilityCalendar(vendorName);
        sendWelcomeNotification(vendorName);
        System.out.println("--- Onboarding complete for: " + vendorName + " ---\n");
    }

    /** Step 1: Common — validate basic business credentials. */
    private void validateCredentials(String vendorName) {
        System.out.println("[Step 1] Validating business credentials for " + vendorName + "... ✓");
    }

    /** Step 2: Varies — each vendor type performs its own specific check. */
    protected abstract void performSpecificCheck(String vendorName);

    /** Step 3: Common — create vendor profile in the system. */
    private void createProfile(String vendorName) {
        System.out.println("[Step 3] Creating vendor profile for " + vendorName + "... ✓");
    }

    /** Step 4: Common — initialize the availability calendar. */
    private void setupAvailabilityCalendar(String vendorName) {
        System.out.println("[Step 4] Setting up availability calendar for " + vendorName + "... ✓");
    }

    /** Step 5: Common — send a welcome notification. */
    private void sendWelcomeNotification(String vendorName) {
        System.out.println("[Step 5] Sending welcome notification to " + vendorName + "... ✓");
    }
}
