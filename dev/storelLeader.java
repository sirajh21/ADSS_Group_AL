import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class storelLeader {

    private static storelLeader instance;

    private final HashMap<String, catogeryCon> categories;
    private final List<items> items;
    private final List<Store> stores;
    private final List<Report> reports;

    public storelLeader() {
        this.categories = new HashMap<>();
        this.items = new ArrayList<>();
        this.stores = new ArrayList<>();
        this.reports = new ArrayList<>();
    }

    public static storelLeader getInstance() {
        if (instance == null) {
            instance = new storelLeader();
        }
        return instance;
    }

    public HashMap<String, catogeryCon> getCategories() {
        return categories;
    }

    public List<items> getItems() {
        return items;
    }

    public List<Store> getStores() {
        return stores;
    }

    public void addStore() {
        stores.add(new Store());
    }

    public void removeStore(String storeName) {
        stores.remove(storeName);
    }

    public void addCategory(catogeryCon category) {
        for (Store store : stores) {
            store.addCategory(category);
        }
        categories.put(category.getCategoryName(), category);
    }

    public void addProduct(items item) {
        items.add(item);
        for (Store store : stores) {
            store.addItem(item);
        }
    }

    public void addProduct(String itemName, String categoryName, int minimumAmount, int amountOfItems,
                           String manufactureName, double companyPrice, double storePrice,
                           double discount, Date expDate) {
        for (items item : items) {
            if (item.getIname().equals(itemName) && item.getCname().equals(categoryName)) {
                item.setAmount(item.getAmount() + amountOfItems);
                for (Store store : stores) {
                    store.addItem(item);
                    return;
                }
            }
        }

        items newItem = new items(itemName,categoryName, minimumAmount, amountOfItems, manufactureName, companyPrice, storePrice, discount);

        newItem.addItem(new product(itemName, expDate, amountOfItems));
        addProduct(newItem);
    }

    public void addProduct(items item, int amount, Date date) {
        item.addItem(new product(item.getName(), date, amount));
    }

    public int numStore(String itemName) {
        for (Store store : stores) {
            for (items item : store.getItems()) {
                if (item.getIname().equals(itemName)) {
                    return item.getAmountstore();
                }
            }
        }
        return 0;
    }

    public int getAmountInShelf(String itemName) {
        for (int i = 0; i < stores.size(); i++) {
            for (int j = 0; j < stores.get(i).getItems().size(); j++) {
                if (stores.get(i).getItems().get(j).getIname().equals(itemName)) {
                    return stores.get(i).getItems().get(j).getAmountShelf();
                }
            }

        }
        return 0;
    }

    public Report report(String reportName) {
        for (Report report : reports) {
            if (report.getReportName().equals(reportName)) {
                return report;
            }
        }
        return null;
    }

    public void addReport(String reportName, String reportDescription, Date reportDate, status_report reason) {
        Report report = new Report(reportDate, reportName, reason, reportDescription);
        reports.add(report);
        System.out.println(report);
    }

    public void makeCategoryReport(String reportName, String reportCategory, String reportDescription, Date reportDate) {
        catogeryCon category = categories.get(reportCategory);
        if (category == null) {
            System.out.println("Category not found: " + reportCategory);
            return;
        }

        addReport(reportName, category + reportDescription, reportDate, status_report.Weekly);
    }

    public void makeItemReport(String reportName, String itemName, String reportDescription, Date reportDate) {
        for (items item : items) {
            if (item.getName().equals(itemName)) {
                addReport(reportName, item + reportDescription, reportDate, status_report.Weekly);
                return;
            }
        }

        System.out.println("Error: Item not found.");
    }

    public void makeDefectReport(String reportName, String reportDescription, Date reportDate) {
        StringBuilder itemsDescription = new StringBuilder();

        for (items itemSpecs : items) {
            List<product> itemsToRemove = new ArrayList<>();

            for (product item : itemSpecs.getItembydate().values()) {
                boolean isExpired = item.finaldate().compareTo(reportDate) <= 0;
                boolean isDamaged = item.getType() == statusDef.dammage;
                boolean isEnded = item.getType() == statusDef.e_end;

                if (isExpired || isDamaged || isEnded) {
                    itemsDescription.append(item).append("\n");
                    itemsToRemove.add(item);
                }
            }

            for (product item : itemsToRemove) {
                itemSpecs.removeDefectedItem(item);
            }
        }

        addReport(reportName, itemsDescription + reportDescription, reportDate, status_report.Defective);
    }



    public void makeLackReport(String reportName, String reportDescription, Date reportDate) {
        StringBuilder itemsDescription = new StringBuilder();

        for (items itemSpecs : items) {
            if (itemSpecs.getAmount() < itemSpecs.getMinAmount()) {
                itemsDescription.append(itemSpecs).append("\n");
            }
        }

        if (itemsDescription.length() == 0) {
            itemsDescription.append("no items \n");
        }

        addReport(reportName, itemsDescription + reportDescription, reportDate, status_report.Missing);
    }

    public void removeCategory(String categoryName) {
        for (Store store : stores) {
            store.removeCategory(categoryName);
        }

        items.removeIf(item -> item.getCname().equals(categoryName));
        categories.remove(categoryName);
    }

    public void notflaction() {
        for (items itemSpecs : items) {
            if (itemSpecs.getAmount() - 3 <= itemSpecs.getMinAmount()) {
                System.out.println("Item " + itemSpecs.getName() + " almost ran out");
            }
        }
        System.out.println();
    }
    public void makeDefectReport(String reportName, String reportDescription, Date reportDate,
                                 HashMap<String, Integer> itemsDefects) {
        StringBuilder itemsDescription = new StringBuilder();

        for (String itemName : itemsDefects.keySet()) {
            for (items itemSpecs : items) {
                if (itemSpecs.getIname().equals(itemName)) {
                    itemSpecs.makeDefectedItem(itemsDefects.get(itemName));
                    itemsDescription.append(itemSpecs).append("\n");
                }
            }
        }

        itemsDescription.append(reportDescription);
        addReport(reportName, itemsDescription.toString(), reportDate, status_report.Defective);
    }

    public void demand() {
        for (Store store : stores) {
            HashMap<String, Integer> demand = store.demandLevel();

            if (demand.isEmpty()) {
                System.out.println("No items have demands");
                continue;
            }
            List<Map.Entry<String, Integer>> sortedDemand = new ArrayList<>(demand.entrySet());
            sortedDemand.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

            System.out.println("Demand levels for store:");
            for (Map.Entry<String, Integer> entry : sortedDemand) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        }
    }
}