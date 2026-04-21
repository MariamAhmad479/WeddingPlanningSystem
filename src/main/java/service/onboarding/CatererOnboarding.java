package service.onboarding;

/**
 * Concrete Template Method — Caterer vendor onboarding.
 *
 * Overrides performSpecificCheck() to verify health and
 * food-safety licences before allowing the caterer onto the platform.
 */
public class CatererOnboarding extends VendorOnboardingTemplate {

    @Override
    protected void performSpecificCheck(String vendorName) {
        System.out.println("[Step 2] Verifying food-safety licences for " + vendorName + "...");
        System.out.println("         → Health department certificate... ✓");
        System.out.println("         → Food handling permit valid until 2027... ✓");
    }
}
