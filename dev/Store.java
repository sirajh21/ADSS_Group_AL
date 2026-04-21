import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Store {

    private final List<items> items;
    private final List<catogeryCon> categories;
    private final List<product> defectItems;
    private final HashMap<Integer, Order> orders;

    public Store() {
        items = new ArrayList<>();
        categories = new ArrayList<>();
        defectItems = new ArrayList<>();
        orders = new HashMap<>();
    }

    public List<items> getItems() {
        return items;
    }

    public List<catogeryCon> getCategories() {
        return categories;
    }

    public LinkedList<catogeryCon> getSubCategoryNames(String categoryName) {
        for (catogeryCon category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                return category.getSubCategory();
            }
        }
        return null;
    }

    public items getItemByName(String itemName) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                return item;
            }
        }
        return null;
    }

    public catogeryCon getCategoryByName(String categoryName) {
        for (catogeryCon category : categories) {
            if (category.GetCategoryName().equals(categoryName)) {
                return category;
            }
        }
        return null;
    }

    public void addCategory(catogeryCon category) {
        categories.add(category);
    }

    public void addItem(items item) {
        items.add(item);

        catogeryCon category = getCategoryByName(item.getCname());
        if (category != null) {
            category.addCategoryItem(item);
        }
    }

    public void removeItem(String itemName) {
        items itemToRemove = getItemByName(itemName);

        if (itemToRemove == null) {
            System.out.println("Item not found.");
            return;
        }

        catogeryCon category = getCategoryByName(itemToRemove.getCname());
        if (category != null) {
            category.removeCategoryItem(itemName);
        }

        items.remove(itemToRemove);
    }

    public void addSubCategory(String categoryName, String subCategoryName) {
        for (catogeryCon category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                category.addsub(new catogeryCon(subCategoryName));
            }
        }
    }

    public void removeCategory(String categoryName) {
        for (catogeryCon category : categories) {
            if (category.getCategoryName().equals(categoryName)) {
                for (items item : category.getCategoryItems()) {
                    items.remove(item);
                    item.setAmount(0);
                }
            }
        }

        categories.removeIf(category -> category.GetCategoryName().equals(categoryName));
    }

    public void addCategoryDiscount(String categoryName, double discountPercentage) {
        for (catogeryCon category : categories) {
            if (category.GetCategoryName().equals(categoryName)) {
                category.addsiscount(discountPercentage);
            }
        }
    }

    public void addFutureCategoryDiscount(String categoryName, Date startDate, Date endDate, double discountPercentage) {
        for (catogeryCon category : categories) {
            if (category.GetCategoryName().equals(categoryName)) {
                category.addCatDiscount_byDate(startDate, endDate, discountPercentage);
                return;
            }
        }
    }

    public void addItemDiscount(String itemName, double discountPercentage) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                item.setDiscount(discountPercentage);
            }
        }
    }

    public void addFutureItemDiscount(String itemName, Date startDate, Date endDate, double discountPercentage) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                item.addFutureDiscount(startDate, endDate, discountPercentage);
                return;
            }
        }
    }

    public void removeCategoryDiscount(String categoryName) {
        for (catogeryCon category : categories) {
            if (category.GetCategoryName().equals(categoryName)) {
                category.removeCatDiscount();
            }
        }
    }

    public void removeFutureCategoryDiscount(String categoryName, Date startDate, Date endDate) {
        for (catogeryCon category : categories) {
            if (category.GetCategoryName().equals(categoryName)) {
                category.remove_future_CatDiscount(startDate, endDate, categoryName);
            }
        }
    }

    public void removeFutureItemDiscount(String itemName) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                item.remove_future_Discount();
            }
        }
    }

    public void removeItemDiscount(String itemName) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                item.removeDiscount();
            }
        }
    }

    public void addDefectiveItem(String itemName, statusDef defectReason, int defectiveAmount) {
        items itemSpecs = getItemByName(itemName);

        if (itemSpecs != null) {
            itemSpecs.add_duf_item(defectiveAmount, defectReason);
            defectItems.addAll(itemSpecs.getItembydate().values());
        }
    }

    public void transferItems(String itemName, int amount) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                item.transfer_item(amount);
            }
        }
    }

    public void moveItemToShelf(String itemName, int amount) {
        for (items item : items) {
            if (item.getIname().equals(itemName)) {
                item.transfer_item(amount);
            }
        }
    }

    public void addMinimumAttributeItem(String itemName, int minimumAmount) {
        items item = getItemByName(itemName);
        if (item != null) {
            item.setMinAmount(minimumAmount);
        }
    }

    public StringBuilder showStatus() {
        StringBuilder status = new StringBuilder();

        for (catogeryCon category : categories) {
            status.append(category).append("\n");
        }

        return status;
    }

    public void showCategories() {
        for (catogeryCon category : categories) {
            System.out.println(category.getCatId() + ": " + category.GetCategoryName());
        }
    }

    public ArrayList<product> getItemsUntilExpDate(Date expDate) {
        ArrayList<product> result = new ArrayList<>();

        for (items itemSpecs : items) {
            for (product item : itemSpecs.getItembydate().values()) {
                if (item.finaldate().before(expDate)) {
                    result.add(item);
                }
            }
        }

        return result;
    }

    public ArrayList<product> getItemsAfterExpDate(Date expDate) {
        ArrayList<product> result = new ArrayList<>();

        for (items itemSpecs : items) {
            for (product item : itemSpecs.getItembydate().values()) {
                if (item.finaldate().after(expDate) || item.finaldate().equals(expDate)) {
                    result.add(item);
                }
            }
        }

        return result;
    }

    public void makeOrder(HashMap<String, Integer> requestedItems, Date orderDate) {
        Set<String> itemNames = requestedItems.keySet();
        HashMap<items, Integer> orderedItems = new HashMap<>();

        for (String itemName : itemNames) {
            String cleanName = itemName.replaceAll("\\P{Print}", "");
            items item = getItemByName(cleanName);
            int amount = requestedItems.get(itemName);

            if (item == null) {
                continue;
            }

            if (item.getAmount() < amount) {
                System.out.println("Store does not have enough amount of " + itemName);
            } else {
                item.setAmount(item.getAmount() - amount);
                orderedItems.put(item, amount);
            }
        }

        Order order = new Order(orderedItems, orderDate);
        orders.put(order.getOrderId(), order);
    }

    public HashMap<String, Integer> demandLevel() {
        HashMap<String, Integer> demand = new HashMap<>();

        for (Order order : orders.values()) {
            for (Map.Entry<items, Integer> entry : order.getItems().entrySet()) {
                String itemName = entry.getKey().getIname();
                int quantity = entry.getValue();

                if (demand.containsKey(itemName)) {
                    demand.put(itemName, demand.get(itemName) + quantity);
                } else {
                    demand.put(itemName, quantity);
                }
            }
        }

        return demand;
    }

    // compatibility methods

    public LinkedList<catogeryCon> getsubCategoryNames(String cname) {
        return getSubCategoryNames(cname);
    }

    public void add_future_CategoryDiscount(String categoryName, Date start, Date end, double discountPercentage) {
        addFutureCategoryDiscount(categoryName, start, end, discountPercentage);
    }

    public void add_future_ItemDiscount(String itemName, Date start, Date end, double discountPercentage) {
        addFutureItemDiscount(itemName, start, end, discountPercentage);
    }

    public void remove_future_CategoryDiscount(String categoryName, Date start, Date end) {
        removeFutureCategoryDiscount(categoryName, start, end);
    }

    public void remove_future_item_Discount(String itemName) {
        removeFutureItemDiscount(itemName);
    }

    public void show_Cat() {
        showCategories();
    }

    public void move_item_to_shelf(String itemName, int amount) {
        moveItemToShelf(itemName, amount);
    }

    public ArrayList<product> ItemsUntilExpDate(Date expDate) {
        return getItemsUntilExpDate(expDate);
    }

    public ArrayList<product> ItemsafterExpDate(Date expDate) {
        return getItemsAfterExpDate(expDate);
    }
}