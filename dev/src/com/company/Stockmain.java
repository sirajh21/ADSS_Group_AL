package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import Backend.ServiceLayer.Backend.ServiceLayer.*;

import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;



public class Stockmain {
    static StoreService stores = StoreService.getInstance();
    static FactoryService store;
    static boolean stockDataLoaded = false;
    static boolean supplierDataLoaded = false;
    public static void mainMenu() throws SQLException, IllegalAccessException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        pickStore();

        if (!supplierDataLoaded) {
            MainController.loadSupplierDataForStock();
            supplierDataLoaded = true;
            System.out.println("Supplier data loaded from DB.");
        }

        if (!stockDataLoaded) {
            LoadData();
            stockDataLoaded = true;
            System.out.println("Stock data loaded from DB.");
        }

        int menu;

        try {
            do {
                System.out.println("Please choose an option:");
                System.out.println("1. Products");
                System.out.println("2. Categories");
                System.out.println("3. Reports");
                System.out.println("4. Discounts");
                System.out.println("5. Items");
                System.out.println("6. remove data");
                System.out.println("7. load data");
                System.out.println("8. minreports");
                System.out.println("9. addorderreport");
                System.out.println("10. receive");
                System.out.println("11. alert");


                System.out.println("0. Exit");

                menu = Integer.parseInt(reader.readLine());

                switch (menu) {
                    case 1:
                        Products();
                        break;

                    case 2:
                        Categories();
                        break;
                    case 3:
                        Reports();
                        break;

                    case 4:
                        discounts();
                        break;
                    case 5:
                        Items();
                        break;
                    case 6:
                        removeData();
                        break;
                    case 7:
                        if (!stockDataLoaded) {
                            LoadData();
                            stockDataLoaded = true;
                            System.out.println("Stock data loaded from DB.");
                        } else {
                            System.out.println("Stock data already loaded.");
                        }
                        break;
                    case 8:
                        ReportController.getInstance().printmin();
                        break;
                    case 9:
                        newReport();
                        break;
                    case 10:
                        System.out.print("Enter the order ID: ");
                        int reportid = Integer.parseInt(reader.readLine());
                        productscontroller.getInstance().receiveorder(reportid);
                        break;
                    case 11:
                        productscontroller.getInstance().Alert();
                        break;
                    case 0:
                        Main.vmain();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
            } while (menu != 0);
        } catch (NumberFormatException | IOException e) {
            System.out.println("Invalid input. Please try again.");
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void pickStore() {


        store = stores.pickStore(1);

    }

    public static void Categories() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.add Category");
            System.out.println("2.delete Category");
            System.out.println("3.add SubCategory");
            System.out.println("4.add FatherCategory");
            System.out.println("5.change Category Name");
            System.out.println("6.return all categories");
            System.out.println("7.return category hierarchy by name");

            System.out.println("0. Exit");

            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        System.out.println("Pick a name for the category:");
                        String name = (reader.readLine());
                        System.out.println("Pick a Father for the category:");
                        Integer faInteger = Integer.parseInt(reader.readLine());
                        store.addCategory(name, faInteger);
                        break;
                    case 2:
                        System.out.println("Pick a category to delete:");
                        int categoryID = Integer.parseInt(reader.readLine());
                        store.deleteCategory(categoryID);
                        break;
                    case 3:
                        System.out.println("Pick a category to add a subcategory to:");
                        categoryID = Integer.parseInt(reader.readLine());
                        System.out.println("Pick a subcategory :");
                        int subCategory = Integer.parseInt(reader.readLine());

                        store.addSubCategory(categoryID, subCategory);
                        break;

                    case 4:
                        System.out.println("Pick a category to delete:");
                        categoryID = Integer.parseInt(reader.readLine());
                        System.out.println("Pick a Father for the category:");
                        int fatherCategory = Integer.parseInt(reader.readLine());
                        store.addFatherCategory(categoryID, fatherCategory);
                        break;
                    case 5:
                        System.out.println("pick a category :");
                        int category = Integer.parseInt(reader.readLine());
                        System.out.println("Pick a name for the category:");
                        name = (reader.readLine());
                        store.changeCategoryName(category, name);
                        break;
                    case 6:
                        store.returnCategories();
                        break;
                    case 7:
                        System.out.println("Enter category name:");
                        String catName = reader.readLine();
                        store.displayCategoryHierarchy(catName);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please try again.");
            }

        } while (action != 0);

    }

    public static void discounts() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.add Product Discount");
            System.out.println("2.add Category Discount");
            System.out.println("3.return all category discounts");
            System.out.println("4.return all product discounts");
            System.out.println("0. Exit");

            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        LocalDate start = LocalDate.now();
                        LocalDate finish = LocalDate.now();

                        System.out.println("Pick a product for the discount:");
                        int productID = Integer.parseInt(reader.readLine());
                        System.out.println("add dicount amount:");
                        Double discount = Double.parseDouble(reader.readLine());
                        System.out.println("When does the discount start yyyy-MM-DD:");
                        try {
                            start = LocalDate.parse(reader.readLine());

                        } catch (Exception e) {
                            System.out.println("invalid date:");
                            break;
                        }
                        System.out.println("When does the discount finish yyyy-MM-DD:");

                        try {
                            finish = LocalDate.parse(reader.readLine());


                        } catch (Exception e) {
                            System.out.println("invalid date:");
                            break;
                        }
                        store.buildProductDiscount(productID, discount, start, finish);
                        break;
                    case 2:
                        System.out.println("Pick a category for the discount:");
                        int categoryID = Integer.parseInt(reader.readLine());
                        System.out.println("add dicount amount:");
                        discount = Double.parseDouble(reader.readLine());
                        System.out.println("When does the discount start yyyy-MM-DD:");
                        try {
                            start = LocalDate.parse(reader.readLine());

                        } catch (Exception e) {
                            System.out.println("invalid date:");
                            break;
                        }
                        System.out.println("When does the discount finish yyyy-MM-DD:");

                        try {
                            finish = LocalDate.parse(reader.readLine());


                        } catch (Exception e) {
                            System.out.println("invalid date:");
                            break;
                        }
                        store.buildCategoryDiscount(categoryID, discount, start, finish);
                        break;
                    case 3:
                        store.getactiveCategoriesDiscounts();
                        break;
                    case 4:
                        store.getactiveProductsDiscounts();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please try again.");
            }

        } while (action != 0);

    }

    public static void Products() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.add Product");
            System.out.println("2.change Product Price");
            System.out.println("3.change Product Name");
            System.out.println("4.change Product Min Amount");
            System.out.println("5.move Product To Category");
            System.out.println("6.return Information for allProducts");
            System.out.println("7.return Information for one Product");


            System.out.println("0. Exit");

            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        System.out.print("Enter the product barcode: ");
                        int barcode = Integer.parseInt(reader.readLine());
                        System.out.print("Enter product name: ");
                        String name = (reader.readLine());

                        System.out.print("Enter minimum stock amount: ");
                        int amount = Integer.parseInt(reader.readLine());

                        System.out.print("Enter selling price: ");
                        double sellingPrice = Double.parseDouble(reader.readLine());

                        System.out.print("Enter manufacturing price: ");
                        double manufacturePrice = Double.parseDouble(reader.readLine());

                        System.out.print("Enter shelf number: ");
                        int shelfNumber = Integer.parseInt(reader.readLine());

                        System.out.print("Enter manufacturer name: ");
                        String manufacturer = (reader.readLine());

                        System.out.print("Enter category: ");
                        int category = Integer.parseInt(reader.readLine());
                        store.addProductToStore(barcode, name, amount, sellingPrice, manufacturePrice, shelfNumber, manufacturer,
                                category);
                        break;
                    case 2:
                        System.out.print("Enter a product : ");
                        int ProductID = Integer.parseInt(reader.readLine());

                        System.out.print("Enter new  selling price: ");
                        double newPrice = Double.parseDouble(reader.readLine());
                        store.changeProductPrice(ProductID, newPrice);
                        break;
                    case 3:
                        System.out.print("Enter productID: ");
                        ProductID = Integer.parseInt(reader.readLine());
                        System.out.print("Enter product name: ");
                        String newname = (reader.readLine());
                        store.changeProductName(ProductID, newname);
                        break;

                    case 4:
                        System.out.print("enter ProductID: ");
                        ProductID = Integer.parseInt(reader.readLine());

                        System.out.print("Enter new min: ");
                        int newmin = Integer.parseInt(reader.readLine());
                        store.changeProductMinAmount(ProductID, newmin);
                        break;
                    case 5:
                        System.out.print("enter ProductID: ");
                        ProductID = Integer.parseInt(reader.readLine());

                        System.out.print("enter a categoryID: ");
                        int newCategoryID = Integer.parseInt(reader.readLine());
                        store.moveProductToCategory(ProductID, newCategoryID);
                        break;
                    case 6:
                        store.returnInformationProducts();
                        break;
                    case 7:
                        System.out.println("Pick a product to get its info:");
                        int productID = Integer.parseInt(reader.readLine());
                        store.returnInformationProduct(productID);
                        break;


                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please try again.");
            }

        } while (action != 0);

    }

    public static void Reports() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int x = 1;
        List<Integer> cats = new LinkedList<Integer>();

        int action = -1;
        System.out.println("1.all cats:");
        System.out.println("2.custom (-1 to exit) ");
        while (true) {
            try {

                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        for (Category integer : store.getCategoryService().categoryController.returnCategories()) {
                            cats.add(integer.getId());
                        }
                        break;
                    case 2:
                        System.out.println("pick category:");

                        int input;
                        while (true) {
                            input = Integer.parseInt(reader.readLine());
                            if (input == -1) {
                                break;
                            }
                            try {
                                if (store.getCategoryService().categoryController.returnCategory1(input) != false) {
                                    cats.add(input);
                                } else {
                                    System.out.println("Invalid category.");
                                }
                            } catch (Exception e) {
                                System.out.println("No such category.");
                            }
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.DefectiveItemsReport");
            System.out.println("2.Stock");
            System.out.println("3.DamagedItemReport");
            System.out.println("4.ExpiredItemReport");
            System.out.println("5.MinReportt");


            System.out.println("0. Exit");

            try {
                action = Integer.parseInt(reader.readLine());


                switch (action) {
                    case 1:

                        store.getDefectiveItemsByStore(cats);
                        break;
                    case 2:
                        store.getProductsAndAmount(cats);
                        break;

                    case 3:
                        store.getDamagedItemReportsByStore(cats);
                        break;

                    case 4:
                        store.getExpiredItemReportsByStore(cats);
                        break;

                    case 5:
                        store.getMinReport(cats);
                        break;


                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please try again.");
            }

        } while (action != 0);

    }


    public static void Items() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int action = -1;
        do {
            System.out.println("Please choose what to do:");
            System.out.println("1.add Storage Item");
            System.out.println("2.add Store Item");
            System.out.println("3.add defictive item from store");
            System.out.println("4.add defictive item from storage");
            System.out.println("5.remove item from storage");
            System.out.println("6.remove item from store");

            System.out.println("0. Exit");

            try {
                action = Integer.parseInt(reader.readLine());
                switch (action) {
                    case 1:
                        System.out.println("Pick a product to add items to:");
                        int productID = Integer.parseInt(reader.readLine());
                        LocalDate expdate = LocalDate.now();

                        System.out.println("When does the item expire yyyy-MM-DD:");
                        try {
                            expdate = LocalDate.parse(reader.readLine());

                        } catch (Exception e) {
                            System.out.println("Not a vaild date");

                            break;
                        }
                        System.out.println("Pick how many items to add :");
                        int amount = Integer.parseInt(reader.readLine());
                        store.addStorageItem(productID, expdate, amount);
                        break;
                    case 2:
                        System.out.println("Pick a product to add items to:");
                        productID = Integer.parseInt(reader.readLine());

                        System.out.println("When does the item expire yyyy-MM-DD:");

                        try {
                            expdate = LocalDate.parse(reader.readLine());

                        } catch (Exception e) {
                            System.out.println("Not a vaild date");

                            break;
                        }
                        System.out.println("Pick how many items to add :");
                        amount = Integer.parseInt(reader.readLine());
                        store.addStoreItem(expdate, productID, amount);
                        break;
                    case 3:
                        System.out.print("Enter ProductID: ");
                        int storageProductID = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Expiration Date (yyyy-MM-DD): ");
                        try {
                            LocalDate storeExpDate = LocalDate.parse(reader.readLine());
                            System.out.print("Enter amount: ");
                            int amount1 = Integer.parseInt(reader.readLine());
                            store.removeStoreItem(storageProductID, storeExpDate, amount1);
                            productscontroller.getInstance().Alert();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please try again.");

                        }
                        break;
                    case 4:
                        System.out.print("Enter ProductID: ");
                        int storageProductID1 = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Expiration Date (yyyy-MM-DD): ");
                        try {
                            LocalDate storageExpDate = LocalDate.parse(reader.readLine());
                            System.out.print("Enter amount: ");
                            int amount1 = Integer.parseInt(reader.readLine());
                            store.removeStorageItem(storageProductID1, storageExpDate, amount1);
                            productscontroller.getInstance().Alert();
                        } catch (Exception e) {
                            System.out.println("Invalid input. Please try again.");
                        }

                        break;
                    case 5:
                        System.out.println("Pick a product to add items to:");
                        productID = Integer.parseInt(reader.readLine());

                        System.out.println("When does the item expire yyyy-MM-DD:");

                        try {
                            expdate = LocalDate.parse(reader.readLine());

                        } catch (Exception e) {
                            System.out.println("Not a vaild date");

                            break;
                        }
                        System.out.println("Pick how many items to remove :");
                        amount = Integer.parseInt(reader.readLine());
                        store.removeStorageItem(productID, expdate, amount);

                        break;
                    case 6:
                        System.out.println("Pick a product to add items to:");
                        productID = Integer.parseInt(reader.readLine());

                        System.out.println("When does the item expire yyyy-MM-DD:");

                        try {
                            expdate = LocalDate.parse(reader.readLine());

                        } catch (Exception e) {
                            System.out.println("Not a vaild date");

                            break;
                        }
                        System.out.println("Pick how many items to remove :");
                        amount = Integer.parseInt(reader.readLine());
                        store.removeStoreItem(productID, expdate, amount);
                        ;
                        break;

                    case 0:
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        continue;
                }

            } catch (NumberFormatException | IOException e) {
                System.out.println("Invalid input. Please try again.");
            }

        } while (action != 0);

    }


    public static void removeData() {
        store.removeData();

    }

    public static void newReport() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        productscontroller controller = productscontroller.getInstance();
        HashMap<Integer, Integer> orderMap = new HashMap<>(); // Class-level variable to store orders
        orderMap.put(0, 0);

        try {
            for (Product p : controller.getProducts().values()) {
                System.out.println("Product in the store:");
                System.out.println("Product ID: " + p.getId());
            }

            int productId = -1;

            while (productId != 0) {
                System.out.print("Enter the product ID you want to order (or '0' to finish): ");
                try {
                    productId = Integer.parseInt(reader.readLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid product ID.");
                    continue; // Restart the loop if input is not a number
                }

                if (productId == 0) {
                    break; // Exit loop if user enters '0'
                }

                if (!controller.getProducts().containsKey(productId)) {
                    System.out.println("Product ID " + productId + " does not exist. Please choose another.");
                } else {
                    System.out.print("How many of this product do you want to order? ");
                    int quantity;
                    try {
                        quantity = Integer.parseInt(reader.readLine());
                        orderMap.put(productId, quantity); // Store product ID and quantity in the map
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter a valid quantity.");
                    }
                }
            }
            controller.sendorder(orderMap);


        } catch (IOException | SQLException e) {
            System.err.println("Error reading input: " + e.getMessage());
        }

    }

    public static void LoadData() {
        store.LoadData();

    }
}