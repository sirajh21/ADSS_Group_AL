import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Bage {

    private static Bage instance;
    private final storelLeader storeController;

    private Bage() {
        storeController = storelLeader.getInstance();
        storeController.addStore();
    }

    public static synchronized Bage instance() {
        if (instance == null) {
            instance = new Bage();
        }
        return instance;
    }

    private List<Store> getStores() {
        return storeController.getStores();
    }

    private StringBuilder collectItem(Date date, boolean expiredOnly) {
        StringBuilder Result = new StringBuilder();
        int itsfound = 0;

        for (int i = 0; i < getStores().size(); i++) {
            ArrayList<product> items;

            if (expiredOnly) {
                items = getStores().get(i).ItemsUntilExpDate(date);
            } else {
                items = getStores().get(i).ItemsafterExpDate(date);
            }

            for (int j = 0; j < items.size(); j++) {
                Result.append(items.get(j));
                itsfound = 1;
            }


        }

        if (itsfound==0) {
            Result.append("No items found\n");
        }

        return Result;
    }

    private catogeryCon findcatbyname(String categoryName) {
        for (Store store : getStores()) {
            for (catogeryCon category : store.getCategories()) {
                if (category.getCategoryName().equals(categoryName)) {
                    return category;
                }
            }
        }
        return null;
    }

    private items finditembyname(String itemName) {
        for (int i = 0; i < getStores().size(); i++) {
            Store store = getStores().get(i);

            for (int j = 0; j < store.getCategories().size(); j++) {
                catogeryCon category = store.getCategories().get(j);

                for (int k = 0; k < category.getCategoryItems().size(); k++) {
                    items item = category.getCategoryItems().get(k);

                    if (item.getName().equals(itemName)) {
                        return item;
                    }
                }
            }
        }
        return null;
    }

    private Store findStoreContainingItem(String itemName) {
        for (Store store : getStores()) {
            if (store.getItemByName(itemName) != null) {
                return store;
            }
        }
        return null;
    }

    public StringBuilder expireditem(Date expDate) {
        return collectItem(expDate, true);
    }

    public StringBuilder available(Date expDate) {
        return collectItem(expDate, false);
    }

    public int getItemsStored(String itemName) {
        return storeController.numStore(itemName);
    }

    public int getItemsShelf(String itemName) {
        return storeController.getAmountInShelf(itemName);
    }

    public void moveItem(String itemName, int amount) {
        for (Store store : getStores()) {
            store.move_item_to_shelf(itemName, amount);
        }
    }

    public void order(HashMap<String, Integer> items, Date expDate) {
        for (Store store : getStores()) {
            store.makeOrder(items, expDate);
        }
    }

    public StringBuilder showStatus() {
        StringBuilder result = new StringBuilder();
        for (Store store : getStores()) {
            result.append(store.showStatus());
        }
        return result;
    }

    public void showMenu() {
        for (Store store : getStores()) {
            store.show_Cat();
        }
    }

    public void removediscount(String itemName) {
        for (Store store : getStores()) {
            store.removeItemDiscount(itemName);
        }
    }

    public void future_dis_cat() {
        for (Store store : getStores()) {
            for (catogeryCon category : store.getCategories()) {
                if (category.getFutureDiscount() != null) {
                    category.apply_future_discounts();
                }
            }
        }
    }

    public void future_dis_item() {
        for (Store store : getStores()) {
            for (catogeryCon category : store.getCategories()) {
                for (items item : category.getCategoryItems()) {
                    if (item.getFutureDis() != null) {
                        item.applyDiscounts();
                    }
                }
            }
        }
    }

    public void remove_future_sid(String itemName) {
        for (Store store : getStores()) {
            store.remove_future_item_Discount(itemName);
        }
    }

    public void remove_future_dis(String categoryName, Date start, Date end) {
        for (Store store : getStores()) {
            store.remove_future_CategoryDiscount(categoryName, start, end);
        }
    }

    public void removecat(String categoryName) {
        for (Store store : getStores()) {
            store.removeCategoryDiscount(categoryName);
        }
    }

    public void itemtransfer(String itemName, int amount) {
        for (Store store : getStores()) {
            store.transferItems(itemName, amount);
        }
    }

    public void MinimumItemamount(String itemName, int minimumAmount) {
        for (Store store : getStores()) {
            store.addMinimumAttributeItem(itemName, minimumAmount);
        }
    }

    public void addDefective(String item, statusDef defectReason, int amount) {
        for (Store store : getStores()) {
            store.addDefectiveItem(item, defectReason, amount);
        }
    }

    public int getitemamount(String item) {

        Store store = findStoreContainingItem(item);
        if (store == null) {
            return 0;
        }

        items it = store.getItemByName(item);
        return it != null ? it.getAmount() : 0;
    }

    public void addSubCategory(String cat, String subCategory) {
        for (Store store : getStores()) {
            store.addSubCategory(cat, subCategory);
        }
    }

    public LinkedList<catogeryCon> getSubCategoryNames(String categoryName) {
        for (Store store : getStores()) {
            LinkedList<catogeryCon> subCategories = store.getsubCategoryNames(categoryName);
            if (subCategories != null) {
                return subCategories;
            }
        }
        return new LinkedList<>();
    }

    public void removeSubCategory(String cat, String subCategory) {
        for (Store store : getStores()) {
            for (catogeryCon category : store.getCategories()) {
                if (category.getCategoryName().equals(cat)) {
                    category.removeSUBCategory(subCategory);
                }
            }
        }
    }

    public boolean cat_exist(String cat_name) {
        return findcatbyname(cat_name) != null;
    }

    public void addItemDiscount(String item, double percentage) {
        for (Store store : getStores()) {
            store.addItemDiscount(item, percentage);
        }
    }

    public void addCategoryDiscount(String cat, double percent) {
        for (Store store : getStores()) {
            store.addCategoryDiscount(cat, percent);
        }
    }

    public void add_fyt_dis(String categoryName, double percentage, Date start, Date end) {
        for (Store store : getStores()) {
            store.add_future_CategoryDiscount(categoryName, start, end, percentage);
        }
    }

    public void addFutureItemDiscount(String itemName, double percentage, Date start, Date end) {
        for (Store store : getStores()) {
            store.add_future_ItemDiscount(itemName, start, end, percentage);
        }
    }

    public int getCategoryCount() {
        return storeController.getCategories().size();
    }

    public void removeCategory(String categoryName) {
        storeController.removeCategory(categoryName);
    }

    public void removeItem(String itemName) {

        for (Store store : getStores()) {
            store.removeItem(itemName);
        }
    }

    public void addCategory(String categoryName) {
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            storeController.addCategory(new catogeryCon(categoryName));
        } else {
            System.out.println("Invalid category name.");
        }
    }

    public catogeryCon getCategoryByName(String categoryName) {
        return findcatbyname(categoryName);
    }

    public items getItemByName(String itemName) {
        return finditembyname(itemName);
    }

    public void addItem(String itemName, String categoryName, Date expDate, double companyPrice,
                        double storePrice, double discount, int minimumAmount,
                        String manufactureName, int amountOfItems) {
        if (itemName != null && !itemName.trim().isEmpty()) {
            storeController.addProduct(
                    itemName,
                    categoryName,
                    minimumAmount,
                    amountOfItems,
                    manufactureName,
                    companyPrice,
                    storePrice,
                    discount,
                    expDate
            );
        } else {
            System.out.println("Invalid item name.");
        }
    }

    public boolean itemExists(String itemName) {
        return findStoreContainingItem(itemName) != null;
    }

    public void makeCategoryReport(String reportName, String reportCategory, String reportDescription, Date reportDate) {
        try {
            storeController.makeCategoryReport(reportName, reportCategory, reportDescription, reportDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeItemReport(String reportName, String itemName, String reportDescription, Date reportDate) {
        try {
            storeController.makeItemReport(reportName, itemName, reportDescription, reportDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void makeLackReport(String reportName, String reportDescription, Date reportDate) {
        try {
            storeController.makeLackReport(reportName, reportDescription, reportDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void makeDefectReport(String reportName, String reportDescription, Date reportDate) {
        try {
            storeController.makeDefectReport(reportName, reportDescription, reportDate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }



    public double getItemPrice(String itemName) {
        Store store = findStoreContainingItem(itemName);
        if (store == null) {
            return 0;
        }

        items item = store.getItemByName(itemName);
        return item != null ? item.get_final_price() : 0;
    }

    public int countItemsInCategory(String categoryName) {
        int count = 0;
        catogeryCon category = findcatbyname(categoryName);

        if (category != null) {
            for (items item : category.getCategoryItems()) {
                count += item.getAmount();
            }
        }

        return count;
    }

    public void addItem(String itemName, int amount, Date date) {
        items item = finditembyname(itemName);
        if (item != null) {
            storeController.addProduct(item, amount, date);
        }
    }

    public Report getReportByName(String reportName) {
        return storeController.report(reportName);
    }

    public void showNotifications() {
        storeController.notflaction();
    }

    public void showDemandLevel() {
        storeController.demand();
    }

    // ===== compatibility methods =====

    public StringBuilder showItemsUntilExpDate(Date exp_date) {
        return expireditem(exp_date);
    }

    public StringBuilder showItems_after_ExpDate(Date exp_date) {
        return available(exp_date);
    }

    public void move_item(String item, int amount) {
        moveItem(item, amount);
    }

    public void showStatus_cat() {
        showMenu();
    }

    public void apply_future_category_Discount() {
        future_dis_cat();
    }

    public void apply_future_item_Discount() {
        future_dis_item();
    }

    public void remove_future_ItemDiscount(String itemName) {
        remove_future_sid(itemName);
    }

    public void remove_future_Car_Discount(String catName, Date start, Date end) {
        remove_future_dis(catName, start, end);
    }

    public int get_item_amount(String item_name) {
        return getitemamount(item_name);
    }

    public LinkedList<catogeryCon> getsubCategoryNames(String cname) {
        return getSubCategoryNames(cname);
    }

    public void removesubcategory(String cname, String subcategory) {
        removeSubCategory(cname, subcategory);
    }

    public boolean IsCategoryExistsByName(String mainCategory) {
        return cat_exist(mainCategory);
    }

    public void add_future_CategoryDiscount(String categotyName, double percentage, Date start, Date end) {
        add_fyt_dis(categotyName, percentage, start, end);
    }

    public void add_future_ItemDiscount(String item, double percentage, Date start, Date end) {
        addFutureItemDiscount(item, percentage, start, end);
    }

    public int get_cat_amount() {
        return getCategoryCount();
    }

    public boolean IsItemExistsByName(String itemName) {
        return itemExists(itemName);
    }

    public double get_item_price(String itemName) {
        return getItemPrice(itemName);
    }

    public int num_items_cat(String categoryName) {
        return countItemsInCategory(categoryName);
    }

    public Report getReportByname(String reportName) {
        return getReportByName(reportName);
    }

    public void showNotification() {
        showNotifications();
    }

    public void demandLevel() {
        showDemandLevel();
    }

    public void addMinimumAttributeItem(String itemName, int minimumAmount) {
        MinimumItemamount(itemName, minimumAmount);
    }
}