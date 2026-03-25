package entity;

import java.util.UUID;

import enumeration.GehazCategory;
import enumeration.GehazStatus;
import factory.GehazItemType;
import factory.GehazItemTypeFactory;

/**
 * The Context class that contains the extrinsic state (brideId, status, customCost).
 * It delegates intrinsic state behavior (name, category) to the GehazItemType flyweight object.
 */
public class GehazItem {

    private String itemId;
    private String brideId;
    private GehazStatus status;
    private double customCost;

    // The Flyweight object containing shared state
    private GehazItemType itemType;

    public GehazItem() {
        this.itemId = UUID.randomUUID().toString();
        this.status = GehazStatus.NOT_PURCHASED;
    }

    public GehazItem(String brideId, GehazItemType itemType) {
        this.itemId = UUID.randomUUID().toString();
        this.brideId = brideId;
        this.itemType = itemType;
        this.status = GehazStatus.NOT_PURCHASED;
        this.customCost = itemType.getDefaultCost();
    }

    public void updateDetails(String name, enumeration.GehazCategory category, double cost) {
        // Fetch or create the flyweight from the factory
        this.itemType = GehazItemTypeFactory.getGehazItemType(name, category, cost);
        this.customCost = cost;
    }

    public void setCost(double cost) {
        this.customCost = cost;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setBrideId(String brideId) {
        this.brideId = brideId;
    }

    public void setItemName(String name) {
        GehazCategory cat = itemType != null ? itemType.getCategory() : null;
        double defCost = itemType != null ? itemType.getDefaultCost() : 0.0;
        this.itemType = GehazItemTypeFactory.getGehazItemType(name, cat, defCost);
    }

    public void setCategory(GehazCategory category) {
        String name = itemType != null ? itemType.getName() : "Unknown";
        double defCost = itemType != null ? itemType.getDefaultCost() : 0.0;
        this.itemType = GehazItemTypeFactory.getGehazItemType(name, category, defCost);
    }

    public void setStatus(enumeration.GehazStatus newStatus) {
        this.status = newStatus;
    }
    
    public void setItemType(GehazItemType itemType) {
        this.itemType = itemType;
        this.customCost = itemType.getDefaultCost();
    }

    public String getBrideId() {
        return brideId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return itemType != null ? itemType.getName() : null;
    }

    public GehazCategory getCategory() {
        return itemType != null ? itemType.getCategory() : null;
    }

    public GehazStatus getStatus() {
        return status;
    }

    public double getCost() {
        return customCost;
    }

    public GehazItemType getItemType() {
        return itemType;
    }

    public void display() {
        if (itemType != null) {
            itemType.displayItem(brideId, status);
        } else {
            System.out.println("Unknown Gehaz Item Status: " + status);
        }
    }
}
