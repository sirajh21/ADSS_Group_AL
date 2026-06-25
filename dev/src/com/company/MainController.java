package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Backend.BusinessLayer.Stock.controllers.PeriodicOrder;
import Backend.ServiceLayer.Backend.ServiceLayer.*;
import Backend.BusinessLayer.Stock.objects.*;

public class MainController {
    private static final Service service = new Service();
    public static void loadSupplierDataForStock() {
        service.loadPersistentData();
    }
    public MainController() {
    }

    public static void mainMenu() throws IllegalAccessException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int option;
        try {
            do {
                System.out.println("\n========== MAIN MENU ==========");
                System.out.println("1. Initialize Data");
                System.out.println("2. Suppliers");
                System.out.println("3. Contracts");
                System.out.println("4. Orders");
                System.out.println("5. Show Data System");
                System.out.println("6. Clear Data");
                System.out.println("0. Exit to Main");
                System.out.print("➤ Choose an option: ");

                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1 -> initializeSystem();
                    case 2 -> Suppliers();
                    case 3 -> Contracts();
                    case 4 -> Orders();
                    case 5 -> showDataSystem();
                    case 6 -> clearData();
                    case 0 -> Main.vmain();
                    default -> System.out.println("❌ Invalid option. Try again.");
                }
            } while (option != 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearData() {
        service.deleteAllOrders();
        service.deleteAllContracts();
        service.deleteAllContacts();
        service.deleteAllProducts();
        service.deleteAllsuplier();
        service.clearInMemoryData();
        System.out.println("✅ All data cleared successfully.");
    }

    public static void initializeSystem() throws IllegalAccessException, SQLException {
        // Clean supplier-side data first, so Initialize Data can be run more than once.
        clearData();

        // ===== Suppliers =====
        ArrayList<String> companies1 = new ArrayList<>();
        companies1.add("Strauss");

        ArrayList<String> companies2 = new ArrayList<>();
        companies2.add("Tnuva");

        ArrayList<String> companies3 = new ArrayList<>();
        companies3.add("Osem");

        service.AddSupplier(1, "StraussSupplier", "0500000000", 1, "Bank1", "cash", companies1);
        service.AddSupplier(2, "TnuvaSupplier", "0500000001", 1, "Bank2", "cash", companies2);
        service.AddSupplier(3, "OsemSupplier", "0500000002", 2, "Bank3", "credit", companies3);

        service.AddContact(1, 111111111, "Sam", "0587654321", "sam@gmail.com");
        service.AddContact(2, 222222222, "Dana", "0587654322", "dana@gmail.com");
        service.AddContact(3, 333333333, "Yossi", "0587654323", "yossi@gmail.com");

        // ===== Supplier products =====
        // Product ID 1 must match the stock module product ID used by alert/load data.
        service.AddProduct(10, "Milk", 1);
        service.AddProduct(8, "Bread", 2);
        service.AddProduct(15, "Tuna", 3);

        Product1 milk = service.getAllProducts().get(1);
        Product1 bread = service.getAllProducts().get(2);
        Product1 tuna = service.getAllProducts().get(3);

        // ===== Supplier 1 contract =====
        HashMap<Integer, Double> milkDiscountSupplier1 = new HashMap<>();
        milkDiscountSupplier1.put(10, 0.0);

        HashMap<Integer, Double> breadDiscountSupplier1 = new HashMap<>();
        breadDiscountSupplier1.put(10, 0.0);

        HashMap<Product1, HashMap<Integer, Double>> contract1Products = new HashMap<>();
        contract1Products.put(milk, milkDiscountSupplier1);
        contract1Products.put(bread, breadDiscountSupplier1);
        service.makeContract(1, "StraussContract", true, contract1Products);

        // ===== Supplier 2 contract =====
        // Same product ID 1, but with a discount, so automatic order should choose this supplier.
        HashMap<Integer, Double> milkDiscountSupplier2 = new HashMap<>();
        milkDiscountSupplier2.put(10, 20.0);

        HashMap<Product1, HashMap<Integer, Double>> contract2Products = new HashMap<>();
        contract2Products.put(milk, milkDiscountSupplier2);
        service.makeContract(2, "TnuvaContract", true, contract2Products);

        // ===== Supplier 3 contract =====
        HashMap<Integer, Double> tunaDiscountSupplier3 = new HashMap<>();
        tunaDiscountSupplier3.put(10, 5.0);

        HashMap<Product1, HashMap<Integer, Double>> contract3Products = new HashMap<>();
        contract3Products.put(tuna, tunaDiscountSupplier3);
        service.makeContract(3, "OsemContract", false, contract3Products);

        // ===== Example periodic order =====
        ArrayList<Integer> periodicProducts = new ArrayList<>();
        periodicProducts.add(3);

        HashMap<Integer, Integer> periodicQuantity = new HashMap<>();
        periodicQuantity.put(3, 20);
        service.AddPeriodicOrder(100, periodicProducts, periodicQuantity, LocalDate.now().plusDays(1), 3);

        System.out.println("✅ Initial supplier data loaded successfully.");
        System.out.println("✅ Product ID 1 = Milk exists in suppliers and matches stock alert.");
        System.out.println("✅ Milk is supplied by Supplier 1 and Supplier 2; Supplier 2 has a better discount.");
    }
//        SuppliersDAO.getInstance().loadAll();
//        ContactDAO.getInstance().loadAll();
//        Product1DAO.getInstance().loadAll();
//        HashMap<Integer,Product1> a =    SupplierController.getInstance().getAllProducts();
//        ContractDAO.getInstance().loadAll();
//        ContractController.getInstance().getAllContracts();
//
//    }

    public static void Suppliers() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int action;
        try {
            do {
                System.out.println("\n===== SUPPLIERS MENU =====");
                System.out.println("1. Add Supplier");
                System.out.println("2. Delete Supplier");
                System.out.println("3. Add New Company");
                System.out.println("4. Delete Company");
                System.out.println("5. Add Product");
                System.out.println("6. Add Contact");
                System.out.println("7. Show All Suppliers");
                System.out.println("0. Back to Main Menu");
                System.out.print("➤ Choose an option: ");

                action = Integer.parseInt(reader.readLine());

                switch (action) {
                    case 1 -> {
                        System.out.print("Enter Supplier ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Name: ");
                        String name = reader.readLine();
                        System.out.print("Enter Phone: ");
                        String phone = reader.readLine();
                        System.out.print("Enter Type (1/2/3): ");
                        int type = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Bank Account: ");
                        String bank = reader.readLine();
                        System.out.print("Enter Payment Type: ");
                        String payment = reader.readLine();
                        System.out.print("Number of Companies: ");
                        int num = Integer.parseInt(reader.readLine());
                        ArrayList<String> companies = new ArrayList<>();
                        for (int i = 0; i < num; i++) {
                            System.out.print("Enter Company Name: ");
                            companies.add(reader.readLine());
                        }
                        int newID = service.AddSupplier(id, name, phone, type, bank, payment, companies).getSupplier_id();
                        System.out.println("✅ Supplier added with ID: " + newID);
                    }
                    case 2 -> {
                        System.out.print("Enter Supplier ID to delete: ");
                        int supplierID = Integer.parseInt(reader.readLine());
                        service.DeleteSupplier(supplierID);
                        System.out.println("✅ Supplier deleted successfully.");
                    }
                    case 3 -> {
                        System.out.print("Enter Supplier ID: ");
                        int supplierID = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Company Name to add: ");
                        String company = reader.readLine();
                        service.AddCompany(supplierID, company);
                        System.out.println("✅ Company added successfully.");
                    }
                    case 4 -> {
                        System.out.print("Enter Supplier ID: ");
                        int supplierID = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Company Name to delete: ");
                        String company = reader.readLine();
                        service.DeleteCompany(supplierID, company);
                        System.out.println("✅ Company deleted successfully.");
                    }
                    case 5 -> {
                        System.out.print("Enter Product Name: ");
                        String productName = reader.readLine();
                        System.out.print("Enter Product Price: ");
                        int price = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Product ID: ");
                        int pid = Integer.parseInt(reader.readLine());
                        while (!service.AddProduct(price, productName, pid)) {
                            System.out.print("ID taken. Enter another Product ID: ");
                            pid = Integer.parseInt(reader.readLine());
                        }
                        System.out.println("✅ Product added with ID: " + pid);
                    }
                    case 6 -> {
                        System.out.print("Enter Supplier ID: ");
                        int supplierID = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Contact ID: ");
                        int contactId = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Contact Name: ");
                        String contactName = reader.readLine();
                        System.out.print("Enter Phone: ");
                        String contactPhone = reader.readLine();
                        System.out.print("Enter Email: ");
                        String contactEmail = reader.readLine();
                        service.AddContact(supplierID, contactId, contactName, contactPhone, contactEmail);
                        System.out.println("✅ Contact added successfully.");
                    }
                    case 7 -> {
                        System.out.println("📄 All Suppliers:");
                        for (Supplier s : service.getAllSuppliers()) {
                            System.out.println(s);
                            System.out.println("-------------------");
                        }
                    }
                    case 0 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("❌ Invalid choice. Try again.");
                }
            } while (action != 0);
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }

    public static void Contracts() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int action;
        try {
            do {
                System.out.println("\n===== CONTRACTS MENU =====");
                System.out.println("1. Add Contract");
                System.out.println("2. Delete Contract");
                System.out.println("3. Show All Contracts");
                System.out.println("0. Back to Main Menu");
                System.out.print("➤ Choose an option: ");

                action = Integer.parseInt(reader.readLine());

                switch (action) {
                    case 1 -> {
                        System.out.print("Enter Supplier ID: ");
                        int supplierID = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Contract Name: ");
                        String contractName = reader.readLine();
                        System.out.print("Is Supplying Scheduled? (true/false): ");
                        boolean scheduled = Boolean.parseBoolean(reader.readLine());

                        System.out.print("Number of Products: ");
                        int numberProducts = Integer.parseInt(reader.readLine());
                        HashMap<Product1, HashMap<Integer, Double>> productQuantityDiscount = new HashMap<>();

                        for (int i = 0; i < numberProducts; i++) {
                            System.out.print("Enter Product ID: ");
                            int productId = Integer.parseInt(reader.readLine());
                            Product1 p = service.getAllProducts().get(productId);

                            HashMap<Integer, Double> QuantityDiscount = new HashMap<>();
                            String more;
                            do {
                                System.out.print("Enter Quantity for Discount: ");
                                int quantity = Integer.parseInt(reader.readLine());
                                System.out.print("Enter Discount: ");
                                double discount = Double.parseDouble(reader.readLine());
                                QuantityDiscount.put(quantity, discount);
                                System.out.print("Add More Discounts for this Product? (yes/no): ");
                                more = reader.readLine();
                            } while (more.equalsIgnoreCase("yes"));

                            productQuantityDiscount.put(p, QuantityDiscount);
                        }
                        String st = service.makeContract(supplierID, contractName, scheduled, productQuantityDiscount);
                        System.out.println(st);
                    }
                    case 2 -> {
                        System.out.print("Enter Contract Name to Delete: ");
                        String name = reader.readLine();
                        System.out.println(service.deleteContract(name));
                    }
                    case 3 -> {
                        ArrayList<Contract> contractList = service.getAllContracts();
                        if (contractList.isEmpty()) {
                            System.out.println("No Contracts Found.");
                        } else {
                            contractList.forEach(System.out::println);
                        }
                    }
                    case 0 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("❌ Invalid choice. Try again.");
                }
            } while (action != 0);
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }

    public static void Orders() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int action;
        try {
            do {
                System.out.println("\n===== ORDERS MENU =====");
                System.out.println("1. Make New Order");
                System.out.println("2. Make New Periodic Order");
                System.out.println("3. Update Periodic Order");
                System.out.println("4. Delete Order");
                System.out.println("5. Show Order");
                System.out.println("0. Back to Main Menu");
                System.out.print("➤ Choose an option: ");

                action = Integer.parseInt(reader.readLine());

                switch (action) {
                    case 1 -> {
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Order Date (yyyy-MM-dd): ");
                        LocalDate date = LocalDate.parse(reader.readLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        System.out.print("Number of Products: ");
                        int count = Integer.parseInt(reader.readLine());
                        ArrayList<Integer> productIds = new ArrayList<>();
                        HashMap<Integer, Integer> productsAndQuantity = new HashMap<>();

                        for (int i = 0; i < count; i++) {
                            System.out.print("Enter Product ID: ");
                            int productId = Integer.parseInt(reader.readLine());
                            System.out.print("Enter Quantity: ");
                            int quantity = Integer.parseInt(reader.readLine());
                            productIds.add(productId);
                            productsAndQuantity.put(productId, quantity);
                        }

                        System.out.println("✅ Order added with ID: " + service.AddOrder(id, productIds, productsAndQuantity, date));
                    }

                    case 2 -> {
                        System.out.print("Enter Periodic Order ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Order Date (yyyy-MM-dd): ");
                        LocalDate date = LocalDate.parse(reader.readLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                        System.out.print("Number of Products: ");
                        int count = Integer.parseInt(reader.readLine());
                        ArrayList<Integer> productIds = new ArrayList<>();
                        HashMap<Integer, Integer> productsAndQuantity = new HashMap<>();

                        for (int i = 0; i < count; i++) {
                            System.out.print("Enter Product ID: ");
                            int productId = Integer.parseInt(reader.readLine());
                            System.out.print("Enter Quantity: ");
                            int quantity = Integer.parseInt(reader.readLine());
                            productIds.add(productId);
                            productsAndQuantity.put(productId, quantity);
                        }

                        System.out.print("Enter Arrival Day: ");
                        int arrivalDay = Integer.parseInt(reader.readLine());

                        System.out.println("✅ Periodic Order added with ID: " + service.AddPeriodicOrder(id, productIds, productsAndQuantity, date, arrivalDay));
                    }

                    case 3 -> {
                        System.out.print("Enter Periodic Order ID to Update: ");
                        int periodicId = Integer.parseInt(reader.readLine());

                        System.out.print("Number of Updated Products: ");
                        int count = Integer.parseInt(reader.readLine());
                        HashMap<Integer, Integer> updatedProducts = new HashMap<>();

                        for (int i = 0; i < count; i++) {
                            System.out.print("Enter Product ID: ");
                            int productId = Integer.parseInt(reader.readLine());
                            System.out.print("Enter Quantity: ");
                            int quantity = Integer.parseInt(reader.readLine());
                            updatedProducts.put(productId, quantity);
                        }

                        if (service.updatePeriodicOrder(periodicId, updatedProducts)) {
                            System.out.println("✅ Periodic Order updated successfully.");
                        } else {
                            System.out.println("❌ Failed to update Periodic Order.");
                        }
                    }

                    case 4 -> {
                        System.out.print("Enter Supplier ID: ");
                        int supplierId = Integer.parseInt(reader.readLine());
                        System.out.print("Enter Order ID: ");
                        int orderId = Integer.parseInt(reader.readLine());
                        Order order = service.getIdOrders().get(orderId);
                        System.out.println(service.DeleteOrder(supplierId, order));
                    }

                    case 5 -> {
                        System.out.print("Enter Order ID: ");
                        int orderId = Integer.parseInt(reader.readLine());
                        Order normalOrder = service.getIdOrders().get(orderId);
                        PeriodicOrder periodicOrder = service.getIdPeriodicOrders().get(orderId);
                        if (normalOrder != null) System.out.println(normalOrder);
                        if (periodicOrder != null) System.out.println(periodicOrder);
                    }

                    case 0 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("❌ Invalid choice. Try again.");
                }
            } while (action != 0);
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }

    public static void showDataSystem() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int action;
        try {
            do {
                System.out.println("\n===== SHOW DATA MENU =====");
                System.out.println("1. Show Supplier Data");
                System.out.println("2. Show All Suppliers");
                System.out.println("3. Show Supplier Contacts");
                System.out.println("4. Show Supplier Contracts");
                System.out.println("5. Show Supplier Companies");
                System.out.println("6. Show Supplier Orders");
                System.out.println("7. Show Order Data");
                System.out.println("8. Show Order Price");
                System.out.println("9. Show Order Supplier");
                System.out.println("10. Show All Contracts");
                System.out.println("0. Back to Main Menu");
                System.out.print("➤ Choose an option: ");

                action = Integer.parseInt(reader.readLine());

                switch (action) {
                    case 1 -> {
                        System.out.print("Enter Supplier ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        System.out.println(service.getSupplier(id));
                    }
                    case 2 -> {
                        for (Supplier s : service.getAllSuppliers()) System.out.println(s);
                    }
                    case 3 -> {
                        System.out.print("Enter Supplier ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        ArrayList<Contact> contacts = service.getContacts(id);
                        if (contacts == null || contacts.isEmpty()) System.out.println("No contacts found.");
                        else contacts.forEach(System.out::println);
                    }
                    case 4 -> {
                        System.out.print("Enter Supplier ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        ArrayList<Contract> contracts = service.getContract(id);
                        contracts.forEach(System.out::println);
                    }
                    case 5 -> {
                        System.out.print("Enter Supplier ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        ArrayList<String> companies = service.getCompanies(id);
                        if (companies == null || companies.isEmpty()) System.out.println("No companies found.");
                        else companies.forEach(System.out::println);
                    }
                    case 6 -> {
                        System.out.print("Enter Supplier ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        Supplier supplier = service.getSupplier(id);
                        ArrayList<Order> orders = service.getOrders().get(supplier);
                        ArrayList<PeriodicOrder> periodicOrders = service.getPeriodicOrders().get(supplier);
                        if (orders != null) orders.forEach(System.out::println);
                        if (periodicOrders != null) periodicOrders.forEach(System.out::println);
                    }
                    case 7 -> {
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        Order o = service.getIdOrders().get(id);
                        PeriodicOrder po = service.getIdPeriodicOrders().get(id);
                        if (o != null) System.out.println(o);
                        if (po != null) System.out.println(po);
                    }
                    case 8 -> {
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        Order o = service.getIdOrders().get(id);
                        PeriodicOrder po = service.getIdPeriodicOrders().get(id);
                        if (o != null) System.out.println("Total: " + service.getTotalOrder(o));
                        if (po != null) System.out.println("Total: " + service.getTotalOrder(po));
                    }
                    case 9 -> {
                        System.out.print("Enter Order ID: ");
                        int id = Integer.parseInt(reader.readLine());
                        Order o = service.getIdOrders().get(id);
                        PeriodicOrder po = service.getIdPeriodicOrders().get(id);
                        if (o != null) System.out.println(service.getSupplier(o));
                        if (po != null) System.out.println(service.getSupplier(po));
                    }
                    case 10 -> {
                        ArrayList<Contract> contracts = service.getAllContracts();
                        if (contracts.isEmpty()) System.out.println("No contracts found.");
                        else contracts.forEach(System.out::println);
                    }
                    case 0 -> System.out.println("Returning to Main Menu...");
                    default -> System.out.println("❌ Invalid choice. Try again.");
                }
            } while (action != 0);
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }


}