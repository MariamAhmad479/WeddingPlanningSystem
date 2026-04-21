package service.onboarding;

/**
 * Concrete Template Method — Photographer vendor onboarding.
 *
 * Overrides performSpecificCheck() to verify portfolio quality
 * before allowing the photographer onto the platform.
 */
public class PhotographerOnboarding extends VendorOnboardingTemplate {

    @Override
    protected void performSpecificCheck(String vendorName) {
        System.out.println("[Step 2] Reviewing photography portfolio for " + vendorName + "...");
        System.out.println("         → Portfolio has 50+ wedding photos... ✓");
        System.out.println("         → High-resolution samples verified... ✓");
    }
}
