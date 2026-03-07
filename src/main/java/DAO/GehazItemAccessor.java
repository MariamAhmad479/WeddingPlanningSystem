package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DBConnection;
import entity.GehazItem;
import entity.GehazItemData;
import enumeration.GehazCategory;
import enumeration.GehazStatus;

public class GehazItemAccessor {

    private Map<String, List<GehazItem>> gehazItemsByBride;
    private Map<String, Double> brideBudgets;

    public GehazItemAccessor() {
        this.gehazItemsByBride = new HashMap<>();
        this.brideBudgets = new HashMap<>();
    }

    public List<GehazItem> getItemsByBrideId(String brideId) {
        List<GehazItem> list = new ArrayList<>();

        String sql = "SELECT * FROM GehazItem WHERE bride_id = ?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, brideId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                GehazItem item = new GehazItem();

                item.setItemId(rs.getString("item_id"));
                item.setBrideId(rs.getString("bride_id"));
                item.updateDetails(
                        rs.getString("name"),
                        GehazCategory.valueOf(rs.getString("category")),
                        rs.getDouble("cost")
                );
                item.setStatus(GehazStatus.valueOf(rs.getString("status")));

                list.add(item);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void insertItem(String brideId, GehazItem item) {
        List<GehazItem> items = getItemsByBrideId(brideId);
        items.add(item);
    }

    public void deleteItem(String itemId) {
        String sql = "DELETE FROM GehazItem WHERE item_id = ?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, itemId);
            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateItemData(String itemId, GehazItemData data) {
        String sql =
                "UPDATE GehazItem " +
                "SET name = ?, category = ?, cost = ?, status = ? " +
                "WHERE item_id = ?";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, data.getName());
            ps.setString(2, data.getCategory().name());
            ps.setDouble(3, data.getCost());
            ps.setString(4, data.getStatus().name());
            ps.setString(5, itemId);

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBudget(String brideId, double newBudget) {
        brideBudgets.put(brideId, newBudget);
    }

    public double getBudget(String brideId) {
        return brideBudgets.getOrDefault(brideId, 0.0);
    }

    public void save(GehazItem item) {
        String sql =
                "INSERT INTO GehazItem " +
                "(item_id, bride_id, name, category, cost, status) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, item.getItemId());
            ps.setString(2, item.getBrideId());
            ps.setString(3, item.getName());
            ps.setString(4, item.getCategory().name());
            ps.setDouble(5, item.getCost());
            ps.setString(6, item.getStatus().name());

            ps.executeUpdate();

            ps.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}