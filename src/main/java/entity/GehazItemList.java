package entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import enumeration.GehazStatus;
public class GehazItemList {

    private String brideId;
    private double totalBudget;
    private List<GehazItem> gehazItems;
    private double totalPurchased;
    private double remainingBudget;
    private int progressPercentage;


    public GehazItemList() {
        this.gehazItems = new ArrayList<>();
        this.totalBudget = 0.0;
        this.totalPurchased = 0.0;
        this.remainingBudget = 0.0;
        this.progressPercentage = 0;
    }


    public void loadItems(List<GehazItem> items) {
        this.gehazItems.clear();
        this.gehazItems.addAll(items);
        recomputeTotals();
    }


    public void addItem(GehazItem item) {
        this.gehazItems.add(item);
        recomputeTotals();
    }


    public void removeItem(String itemId) {
        Iterator<GehazItem> it = gehazItems.iterator();
        while (it.hasNext()) {
            if (it.next().getItemId().equals(itemId)) {
                it.remove();
                break;
            }
        }
        recomputeTotals();
    }


    public void applyItemChange(String itemId, GehazItemData data) {

        for (GehazItem item : gehazItems) {
            if (item.getItemId().equals(itemId)) {

                item.updateDetails(
                    data.getName(),
                    data.getCategory(),
                    data.getCost()
                );

                item.setStatus(data.getStatus());
                break;
            }
        }

        recomputeTotals();
    }



    public void setBudget(double newBudget) {
        this.totalBudget = newBudget;
        recomputeTotals();
    }


    public GehazSummary computeSummary() {
        GehazSummary summary = new GehazSummary();
        summary.totalBudget = totalBudget;
        summary.totalPurchased = totalPurchased;
        summary.remainingBudget = remainingBudget;
        summary.progressPercentage = progressPercentage;
        return summary;
    }



    public List<GehazItem> getItems() {
        return new ArrayList<>(gehazItems);
    }


    private void recomputeTotals() {

        totalPurchased = 0.0;

        for (GehazItem item : gehazItems) {
            if (item.getStatus() == GehazStatus.PURCHASED) {
                totalPurchased += item.getCost();
            }
        }

        remainingBudget = totalBudget - totalPurchased;
        if (remainingBudget < 0) {
            remainingBudget = 0;
        }

        if (totalBudget > 0) {
            progressPercentage = (int) ((totalPurchased / totalBudget) * 100);
        } else {
            progressPercentage = 0;
        }
    }
}
