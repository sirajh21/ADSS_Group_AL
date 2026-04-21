import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class Menu {

    private final Bage facade;
    private final Scanner scanner;

    public Menu() {
        facade = Bage.instance();
        scanner = new Scanner(System.in);
    }

    public void showMenu() {


        while (true) {
            System.out.println("\n========== MENU ==========");
            System.out.println("1. Add Item");
            System.out.println("2. Add Category");
            System.out.println("3. Remove Item");
            System.out.println("4. Remove Category");
            System.out.println("5. Add Discount");
            System.out.println("6. Add Sub-Category");
            System.out.println("7. Add Defective Item");
            System.out.println("8. Set Minimum Amount");
            System.out.println("9. Make Report");
            System.out.println("10. Transfer Items");
            System.out.println("11. Remove Discount");
            System.out.println("12. Show Status");
            System.out.println("13. Refresh Storage");
            System.out.println("14. Make Order");
            System.out.println("15. Show Notifications");
            System.out.println("16. Show Demand");
            System.out.println("17. Exit");
            System.out.print("Choice: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    addCategory();
                    break;
                case 3:
                    removeItem();
                    break;
                case 4:
                    removeCategory();
                    break;
                case 5:
                    addDiscount();
                    break;
                case 6:
                    addSubCategory();
                    break;
                case 7:
                    addDefectiveItem();
                    break;
                case 8:
                    setMinimumAmount();
                    break;
                case 9:
                    makeReport();
                    break;
                case 10:
                    transferItems();
                    break;
                case 11:
                    removeDiscount();
                    break;
                case 12:
                    System.out.println("\n--- STATUS ---");
                    System.out.println(facade.showStatus());
                    break;
                case 13:
                    refresh();
                    break;
                case 14:
                    makeOrder();
                    break;
                case 15:
                    System.out.println("\n--- NOTIFICATIONS ---");
                    facade.showNotification();
                    break;
                case 16:
                    System.out.println("\n--- DEMAND ---");
                    facade.demandLevel();
                    break;
                case 17:
                    System.out.println("Goodbye.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private Date parseDate(String text) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(text);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private Date daysFromNow(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    private HashMap<String, Integer> readItemsFromCsv() {
        HashMap<String, Integer> items = new HashMap<>();

        System.out.print("CSV file path: ");
        String path = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 2) {
                    items.put(values[0].trim(), Integer.parseInt(values[1].trim()));
                }
            }
            System.out.println("CSV loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }

        return items;
    }

    private void fill() {
        facade.addCategory("cat1");
        facade.addCategory("cat2");
        facade.addCategory("cat3");

        facade.addItem("item1", "cat1", daysFromNow(180), 5, 10, 10, 5, "Monkey", 20);
        facade.addItem("item2", "cat2", daysFromNow(365), 3, 20, 0, 4, "Man1", 50);
        facade.addItem("item3", "cat1", daysFromNow(730), 9, 30, 20, 7, "Man1", 70);
    }

    private void addCategory() {
        System.out.print("Category name: ");
        String name = scanner.nextLine();

        if (facade.IsCategoryExistsByName(name)) {
            System.out.println("Category already exists.");
            return;
        }

        facade.addCategory(name);
        System.out.println("Category added.");
    }

    private void removeCategory() {
        System.out.print("Category name: ");
        String name = scanner.nextLine();
        facade.removeCategory(name);
        System.out.println("Category removed.");
    }

    private void addSubCategory() {
        System.out.print("Main category: ");
        String main = scanner.nextLine();

        System.out.print("Sub-category: ");
        String sub = scanner.nextLine();

        facade.addSubCategory(main, sub);
        System.out.println("Sub-category added.");
    }

    private void addItem() {
        System.out.print("Load item from CSV? (y/n): ");
        String ans = scanner.nextLine();

        if (ans.equalsIgnoreCase("y")) {
            importItemsFromCsv();
            return;
        }

        System.out.print("Item name: ");
        String itemName = scanner.nextLine();

        System.out.print("Category name: ");
        String categoryName = scanner.nextLine();

        System.out.print("Manufacturer: ");
        String manufacturer = scanner.nextLine();

        System.out.print("Expiration date (dd/MM/yyyy): ");
        Date expDate = parseDate(scanner.nextLine());

        System.out.print("Minimum amount: ");
        int minAmount = Integer.parseInt(scanner.nextLine());

        System.out.print("Amount: ");
        int amount = Integer.parseInt(scanner.nextLine());

        System.out.print("Company price: ");
        double companyPrice = Double.parseDouble(scanner.nextLine());

        System.out.print("Store price: ");
        double storePrice = Double.parseDouble(scanner.nextLine());

        System.out.print("Discount: ");
        double discount = Double.parseDouble(scanner.nextLine());

        facade.addItem(itemName, categoryName, expDate, companyPrice, storePrice, discount, minAmount, manufacturer, amount);
        System.out.println("Item added.");
    }

    private void importItemsFromCsv() {
        System.out.print("CSV file path: ");
        String path = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values.length == 9) {
                    String itemName = values[0].trim();
                    String categoryName = values[1].trim();
                    String manufacturer = values[2].trim();
                    Date expDate = parseDate(values[3].trim());
                    int minAmount = Integer.parseInt(values[4].trim());
                    int amount = Integer.parseInt(values[5].trim());
                    double companyPrice = Double.parseDouble(values[6].trim());
                    double storePrice = Double.parseDouble(values[7].trim());
                    double discount = Double.parseDouble(values[8].trim());

                    if (!facade.IsCategoryExistsByName(categoryName)) {
                        facade.addCategory(categoryName);
                    }

                    facade.addItem(itemName, categoryName, expDate, companyPrice, storePrice, discount, minAmount, manufacturer, amount);
                }
            }
            System.out.println("Items imported.");
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
    }

    private void removeItem() {
        System.out.print("Item name: ");
        String name = scanner.nextLine();
        facade.removeItem(name);
        System.out.println("Item removed.");
    }

    private void addDiscount() {
        System.out.println("1. Category Discount");
        System.out.println("2. Item Discount");
        System.out.print("Choice: ");
        int type = Integer.parseInt(scanner.nextLine());

        if (type == 1) {
            System.out.print("Category name: ");
            String name = scanner.nextLine();

            System.out.print("Percentage: ");
            double percentage = Double.parseDouble(scanner.nextLine());

            facade.addCategoryDiscount(name, percentage);
            System.out.println("Category discount added.");
        } else if (type == 2) {
            System.out.print("Item name: ");
            String name = scanner.nextLine();

            System.out.print("Percentage: ");
            double percentage = Double.parseDouble(scanner.nextLine());

            facade.addItemDiscount(name, percentage);
            System.out.println("Item discount added.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void removeDiscount() {
        System.out.println("1. Category Discount");
        System.out.println("2. Item Discount");
        System.out.print("Choice: ");
        int type = Integer.parseInt(scanner.nextLine());

        if (type == 1) {
            System.out.print("Category name: ");
            String name = scanner.nextLine();
            facade.removecat(name);
            System.out.println("Category discount removed.");
        } else if (type == 2) {
            System.out.print("Item name: ");
            String name = scanner.nextLine();
            facade.removediscount(name);
            System.out.println("Item discount removed.");
        } else {
            System.out.println("Invalid choice.");
        }
    }

    private void transferItems() {
        System.out.print("Item name: ");
        String name = scanner.nextLine();

        System.out.print("Amount to transfer: ");
        int amount = Integer.parseInt(scanner.nextLine());

        facade.move_item(name, amount);
        System.out.println("Items transferred.");
    }

    private void makeOrder() {
        System.out.print("Load order from CSV? (y/n): ");
        String ans = scanner.nextLine();

        HashMap<String, Integer> items = new HashMap<>();

        if (ans.equalsIgnoreCase("y")) {
            items = readItemsFromCsv();
        } else {
            while (true) {
                System.out.print("Item name (or exit): ");
                String itemName = scanner.nextLine();

                if (itemName.equalsIgnoreCase("exit")) {
                    break;
                }

                System.out.print("Amount: ");
                int amount = Integer.parseInt(scanner.nextLine());
                items.put(itemName, amount);
            }
        }

        facade.order(items, new Date());
        System.out.println("Order created.");
    }

    private void makeReport() {
        System.out.println("1. Category Report");
        System.out.println("2. Item Report");
        System.out.println("3. Defect Report");
        System.out.println("4. Lack Report");
        System.out.print("Choice: ");

        int choice = Integer.parseInt(scanner.nextLine());

        System.out.print("Report name: ");
        String reportName = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        Date reportDate = new Date();

        if (choice == 1) {
            System.out.print("Category name: ");
            String categoryName = scanner.nextLine();
            facade.makeCategoryReport(reportName, categoryName, description, reportDate);
        } else if (choice == 2) {
            System.out.print("Item name: ");
            String itemName = scanner.nextLine();
            facade.makeItemReport(reportName, itemName, description, reportDate);
        } else if (choice == 3) {
            facade.makeDefectReport(reportName, description, reportDate);
        } else if (choice == 4) {
            facade.makeLackReport(reportName, description, reportDate);
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("Report created.");
    }

    private void addDefectiveItem() {
        System.out.print("Item name: ");
        String itemName = scanner.nextLine();

        System.out.println("1. Damaged");
        System.out.println("2. Expired");
        System.out.print("Choice: ");
        int choice = Integer.parseInt(scanner.nextLine());

        statusDef type;
        if (choice == 1) {
            type = statusDef.dammage;
        } else if (choice == 2) {
            type = statusDef.e_end;
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Amount: ");
        int amount = Integer.parseInt(scanner.nextLine());

        facade.addDefective(itemName, type, amount);
        System.out.println("Defective items added.");
    }

    private void setMinimumAmount() {
        System.out.print("Item name: ");
        String itemName = scanner.nextLine();

        System.out.print("Minimum amount: ");
        int minimumAmount = Integer.parseInt(scanner.nextLine());

        facade.addMinimumAttributeItem(itemName, minimumAmount);
        System.out.println("Minimum amount updated.");
    }

    private void refresh() {
        System.out.println("\n--- REFRESH STORAGE ---");
        Date now = new Date();

        System.out.println("Expired items:");
        System.out.println(facade.showItemsUntilExpDate(now));

        System.out.println("Available items:");
        System.out.println(facade.showItems_after_ExpDate(now));

        facade.apply_future_category_Discount();
        facade.apply_future_item_Discount();

        System.out.println("Storage refreshed.");
    }

    // compatibility
    public void showSpecificMenu() {
        showMenu();
    }
}