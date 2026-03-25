package factory;
import enumeration.GehazCategory;
/**
 * The Flyweight class that contains the intrinsic (shared) state of a Gehaz Item.
 * This state is context-independent and shared across many GehazItem objects.*/
public class GehazItemType {
    private final String name;
    private final GehazCategory category;
    private final double defaultCost;

    public GehazItemType(String name, GehazCategory category, double defaultCost) {
        this.name = name;
        this.category = category;
        this.defaultCost = defaultCost;
    }

    public String getName() {
        return name;
    }

    public GehazCategory getCategory() {
        return category;
    }

    public double getDefaultCost() {
        return defaultCost;
    }

    // Business operation that uses extrinsic state passed by the client
    public void displayItem(String brideId, enumeration.GehazStatus status) {
        System.out.println("Gehaz Item [Name=" + name + ", Category=" + category + "] for Bride: " + brideId + ", Status: " + status);
    }
}
