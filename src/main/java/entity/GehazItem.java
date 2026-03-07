package entity;

import java.util.UUID;

import enumeration.GehazCategory;
import enumeration.GehazStatus;

public class GehazItem {

    private String itemId;
    private String name;
     private String brideId;
    private GehazCategory category;
    private GehazStatus status;
    private double cost;

    public GehazItem() {
        this.itemId = UUID.randomUUID().toString();
        this.status = GehazStatus.NOT_PURCHASED;
    }

    public void updateDetails(String name, enumeration.GehazCategory category, double cost) {
        this.name = name;
        this.category = category;
        this.cost = cost;
    }

        public void setCost(double cost) {
        this.cost = cost;
    }

        public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setBrideId(String brideId) {
        this.brideId = brideId;
    }

    public void setItemName(String name) {
        this.name = name;
    }

    public void setCategory(GehazCategory category) {
        this.category = category;
    }

    public void setStatus(enumeration.GehazStatus newStatus) {
        this.status = newStatus;
    }


    public String getBrideId() {
        return brideId;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public GehazCategory getCategory() {
        return category;
    }

    public GehazStatus getStatus() {
        return status;
    }

    public double getCost() {
        return cost;
    }
}
