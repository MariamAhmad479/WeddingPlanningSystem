package controller;

import java.util.List;
import java.util.UUID;

import DAO.GehazItemAccessor;
import entity.GehazItem;
import entity.GehazItemData;
import entity.GehazItemList;
import enumeration.GehazStatus;

public class GehazItemController {

    private String currentBrideId;
    private final GehazItemAccessor accessor = new factory.SqlDAOFactory().getGehazItemDAO();

    public void setCurrentBride(String brideId) {
        this.currentBrideId = brideId;
    }

    public List<GehazItem> requestGehazData(String brideId) {
        return accessor.getItemsByBrideId(brideId);
    }

    public void addGehazItem(String brideId, GehazItemData data) {
    this.currentBrideId = brideId;
    createItem(data);
}

public void createItem(GehazItemData data) {
    GehazItem item = new GehazItem();
    item.setItemId(UUID.randomUUID().toString());
    item.setBrideId(currentBrideId);
    item.updateDetails(
        data.getName(),
        data.getCategory(),
        data.getCost()
    );
    item.setStatus(data.getStatus());

    accessor.save(item);
}

public void submitEditItem(String itemId, GehazItemData data) {
    accessor.updateItemData(itemId, data);
}

public void deleteItem(String itemId) {
    accessor.deleteItem(itemId); 
}

}

