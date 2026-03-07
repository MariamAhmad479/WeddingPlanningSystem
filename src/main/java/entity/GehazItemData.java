package entity;

import enumeration.GehazCategory;
import enumeration.GehazStatus;

public class GehazItemData {
    private String name;
    private GehazCategory category;
    private GehazStatus status;
    private double cost;

    public GehazItemData() {}

    public GehazItemData(String name, GehazCategory category, GehazStatus status, double cost) {
        this.name = name;
        this.category = category;
        this.status = status;
        this.cost = cost;
    }

    public String getName() { return name; }
    public GehazCategory getCategory() { return category; }
    public GehazStatus getStatus() { return status; }
    public double getCost() { return cost; }

    public void setName(String name) { this.name = name; }
    public void setCategory(GehazCategory category) { this.category = category; }
    public void setStatus(GehazStatus status) { this.status = status; }
    public void setCost(double cost) { this.cost = cost; }
}
