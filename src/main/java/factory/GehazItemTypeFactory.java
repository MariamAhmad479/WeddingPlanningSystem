package factory;

import java.util.HashMap;
import java.util.Map;
import enumeration.GehazCategory;

public class GehazItemTypeFactory {
    private static final Map<String, GehazItemType> itemTypes = new HashMap<>();

    public static GehazItemType getGehazItemType(String name, GehazCategory category, double defaultCost) {
        String key = name + "_" + (category != null ? category.toString() : "UNCATEGORIZED");
        GehazItemType result = itemTypes.get(key);
        
        if (result == null) {
            result = new GehazItemType(name, category, defaultCost);
            itemTypes.put(key, result);
            System.out.println("FlyweightFactory: Created new GehazItemType for " + name);
        } else {
            System.out.println("FlyweightFactory: Reusing existing GehazItemType for " + name);
        }
        return result;
    }
    
    public static int getCacheSize() {
        return itemTypes.size();
    }
}
