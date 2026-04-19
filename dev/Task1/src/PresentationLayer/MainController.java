package PresentationLayer;

import ServiceLayer.*;
import BusinessLayer.*;

import java.util.*;

public class MainController {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Service service = new Service();
        loadDemoData(service);
        boolean running = true;

        while (running) {
            System.out.println("\n========== 🍅 Supply Management System ==========");
            System.out.println("Please choose an option:");
            System.out.println("1. Suppliers");
            System.out.println("2. Contracts");
            System.out.println("3. Orders");
            System.out.println("4. Show Data System");
            System.out.println("0. Exit");
            System.out.print("➤ Option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    handleSupplierMenu(scanner, service);
                    break;
                case "2":
                    handleContractMenu(scanner, service);
                    break;
                case "3":
                    handleOrderMenu(scanner, service);
                    break;
                case "4":
                    showSystemData(service);
                    break;
                case "0":
                    System.out.println("👋 Exiting system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private static void handleSupplierMenu(Scanner scanner, Service service) {
        System.out.println("\n📦 Supplier Menu:");
        System.out.println("1. Add Supplier");
        System.out.println("2. Supplier Products");
        System.out.println("3. Supplier Contacts");
        System.out.println("4. Supplier Contracts");
        System.out.println("5. View All Suppliers");
        System.out.println("6. Delete Supplier");
        System.out.println("7. Change Supplier Details");
        System.out.println("8. Supplier Companies");
        System.out.print("➤ Option: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                try {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    if (service.getSupplier(id) != null) {
                        System.out.println("⚠️ Supplier ID already exists.");
                        break;
                    }
                    System.out.print("Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();
                    System.out.print("Bank Name: ");
                    String bankName = scanner.nextLine();
                    System.out.print("Bank Branch: ");
                    String bankBranch = scanner.nextLine();
                    System.out.print("Bank Number: ");
                    int bankNum = Integer.parseInt(scanner.nextLine());

                    BankDetails bank = new BankDetails(bankName, bankBranch, bankNum);
                    ArrayList<Integer> contacts = new ArrayList<>();
                    System.out.println(service.addSupplier(id, name, address, phone, bank, contacts));

                    System.out.print("Enter Supplier type\n1. ScheduledSupplier\n2. OnDemandOrdering\n3. PickAndDeliver\n➤ Option: ");
                    int supType = Integer.parseInt(scanner.nextLine());
                    service.setSupplierType(id, supType);

                    if (service.getSupplier(id).ShceduledSupp()) {
                        ArrayList<Integer> workdaylist = new ArrayList<>();
                        boolean didntFinish = true;
                        while (didntFinish) {
                            System.out.print("Enter Supplier workday (1-7): ");
                            int workday = Integer.parseInt(scanner.nextLine());
                            if (workday < 1 || workday > 7) {
                                System.out.println("Invalid day. Try 1-7.");
                                continue;
                            }
                            workdaylist.add(workday);
                            System.out.print("Add another day? (yes/no): ");
                            if (scanner.nextLine().equalsIgnoreCase("no")) {
                                service.setWorkDays(id, workdaylist);
                                didntFinish = false;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("❌ Failed to add supplier.");
                }
                break;

            case "2":
                System.out.print("Supplier ID: ");
                int supplierId = Integer.parseInt(scanner.nextLine());
                if (service.getSupplier(supplierId) == null) {
                    System.out.println("⚠️ Supplier ID does not exist.");
                    break;
                }

                System.out.println("1. Add Product");
                System.out.println("2. Delete Product");
                System.out.println("3. Show Products");
                System.out.print("➤ Option: ");
                String prodChoice = scanner.nextLine();

                switch (prodChoice) {
                    case "1":
                        System.out.print("Product ID: ");
                        int pid = Integer.parseInt(scanner.nextLine());
                        System.out.print("Name: ");
                        String pname = scanner.nextLine();
                        System.out.print("Price: ");
                        double price = Double.parseDouble(scanner.nextLine());
                        System.out.println(service.addProductToSupplier(supplierId, pid, pname, price));
                        break;
                    case "2":
                        System.out.print("Product ID: ");
                        int delPid = Integer.parseInt(scanner.nextLine());
                        System.out.println(service.removeProductFromSupplier(supplierId, delPid));
                        break;
                    case "3":
                        Map<Integer, Product> products = service.getSupplier(supplierId).getSuppProducts();
                        products.values().forEach(Product::display);
                        break;
                    default:
                        System.out.println("❌ Invalid product option.");
                }
                break;

            case "3":
                System.out.print("Supplier ID: ");
                int suppIdForContacts = Integer.parseInt(scanner.nextLine());
                if (service.getSupplier(suppIdForContacts) == null) {
                    System.out.println("⚠️ Supplier ID does not exists.");
                    break;
                }
                System.out.println("1. Add Contact");
                System.out.println("2. Delete Contact");
                System.out.println("3. Show Contacts");
                System.out.print("➤ Option: ");
                String contactChoice = scanner.nextLine();
                switch (contactChoice) {
                    case "1":
                        System.out.print("Contact ID: ");
                        int cid = Integer.parseInt(scanner.nextLine());
                        System.out.print("Name: ");
                        String cname = scanner.nextLine();
                        System.out.print("Phone: ");
                        int cphone = Integer.parseInt(scanner.nextLine());
                        System.out.print("Address: ");
                        String caddress = scanner.nextLine();
                        System.out.println(service.addContact(suppIdForContacts, cid, cname, cphone, caddress));
                        break;
                    case "2":
                        System.out.print("Phone to delete: ");
                        int deletePhone = Integer.parseInt(scanner.nextLine());
                        System.out.println(service.deleteContact(suppIdForContacts, deletePhone));
                        break;
                    case "3":
                        ArrayList<Integer> contacts = service.getcontacts(suppIdForContacts);
                        for (Integer contact : contacts) {
                            System.out.println(contact);
                        }
                        break;
                    default:
                        System.out.println("Invalid contact option.");
                }
                break;

            case "4":
                System.out.print("Supplier ID: ");
                int sid = Integer.parseInt(scanner.nextLine());
                System.out.println("1. Add Contract");
                System.out.println("2. Show Contracts");
                System.out.print("➤ Option: ");
                String contractChoice = scanner.nextLine();
                switch (contractChoice) {
                    case "1":
                        System.out.print("Contract Name: ");
                        String cname = scanner.nextLine();
                        EnumSuppType type = service.getSupplier(sid).getSuppType();
                        ArrayList<WorkDays> days = service.getSupplier(sid).getSuppWorkDays();
                        System.out.println(service.createContract(sid, cname, type, days));
                        break;
                    case "2":
                        service.displayAllContracts();
                        break;
                    default:
                        System.out.println("Invalid contract option.");
                }
                break;

            case "5":
                ArrayList<Supplier> allSuppliers = service.getAllSuppliers();
                if (allSuppliers.isEmpty()) {
                    System.out.println("📭 No suppliers in system.");
                } else {
                    allSuppliers.forEach(Supplier::display);
                }
                break;
            case "6":
                System.out.print("Supplier ID to delete: ");
                int deleteId = Integer.parseInt(scanner.nextLine());
                if (service.getSupplier(deleteId) == null) {
                    System.out.println("❌ Supplier not found.");
                } else {
                    System.out.println(service.deleteSupplier(deleteId));
                }
                break;
            case "7":
                System.out.print("Supplier ID to update: ");
                int updateId = Integer.parseInt(scanner.nextLine());
                Supplier s = service.getSupplier(updateId);
                if (s == null) {
                    System.out.println("❌ Supplier not found.");
                    break;
                }

                System.out.println("What do you want to change?");
                System.out.println("1. Name");
                System.out.println("2. Address");
                System.out.println("3. Phone Number");
                System.out.println("4. Bank Details");
                System.out.println("5. Work Days");
                System.out.println("6. Supplier Type");
                System.out.print("➤ Option: ");
                String updateOption = scanner.nextLine();

                switch (updateOption) {
                    case "1":
                        System.out.print("New Name: ");
                        String newName = scanner.nextLine();
                        s.setSupplierName(newName);
                        System.out.println("✅ Name updated.");
                        break;
                    case "2":
                        System.out.print("New Address: ");
                        String newAddress = scanner.nextLine();
                        s.setSupplierAddress(newAddress);
                        System.out.println("✅ Address updated.");
                        break;
                    case "3":
                        System.out.print("New Phone Number: ");
                        String newPhone = scanner.nextLine();
                        s.setSupplierPhoneNumber(newPhone);
                        System.out.println("✅ Phone updated.");
                        break;
                    case "4":
                        System.out.print("Bank Name: ");
                        String newBank = scanner.nextLine();
                        System.out.print("Bank Branch: ");
                        String newBranch = scanner.nextLine();
                        System.out.print("Bank Number: ");
                        int newBankNum = Integer.parseInt(scanner.nextLine());
                        BankDetails newBankDetails = new BankDetails(newBank, newBranch, newBankNum);
                        s.setBankDetails(newBankDetails);
                        System.out.println("✅ Bank details updated.");
                        break;
                    case "5":
                        ArrayList<WorkDays> workdays = s.getSuppWorkDays();
                        if (workdays == null || workdays.isEmpty()) {
                            System.out.println("⚠️ No work days set.");
                            break;
                        } else {
                            System.out.println("Current Work Days:");
                            for (WorkDays day : workdays) {
                                System.out.println(" - " + day);
                            }
                        }

                        System.out.println("Choose action: 1. Add Day  2. Delete Day");
                        String dayAction = scanner.nextLine();

                        switch (dayAction) {
                            case "1":
                                System.out.print("Enter day to add (1-7): ");
                                int addDay = Integer.parseInt(scanner.nextLine());
                                service.AddSupplierworkDay(updateId, addDay);
                                System.out.println("✅ Work day added.");
                                break;
                            case "2":
                                System.out.print("Enter day to delete (1-7): ");
                                int delDay = Integer.parseInt(scanner.nextLine());
                                service.DeleteSupplierworkDay(updateId, delDay);
                                System.out.println("✅ Work day deleted.");
                                break;
                            default:
                                System.out.println("❌ Invalid action.");
                        }
                        break;
                    case "6":
                        EnumSuppType currentType = service.getSupplier(updateId).getSuppType();
                        System.out.println("Current type: " + currentType);
                        System.out.println("Choose new type:");
                        System.out.println("1. ScheduledSupplier");
                        System.out.println("2. OnDemandOrdering");
                        System.out.println("3. PickAndDeliver");
                        System.out.print("➤ Option: ");
                        int newType = Integer.parseInt(scanner.nextLine());

                        if (newType < 1 || newType > 3) {
                            System.out.println("❌ Invalid type.");
                            break;
                        }

                        EnumSuppType selectedType = switch (newType) {
                            case 1 -> EnumSuppType.ScheduledSupplier;
                            case 2 -> EnumSuppType.OnDemandOrdering;
                            case 3 -> EnumSuppType.PickAndDeliver;
                            default -> null;
                        };

                        if (currentType == selectedType) {
                            System.out.println("⚠️ Supplier is already set to this type.");
                            break;
                        }


                        if (currentType == EnumSuppType.ScheduledSupplier && selectedType != EnumSuppType.ScheduledSupplier) {
                            System.out.println("ℹ️ Supplier changed from Scheduled to another type. Work days will be removed.");
                            service.setWorkDays(updateId, new ArrayList<>());
                        }


                        System.out.println(service.setSupplierType(updateId, newType));


                        if (currentType != EnumSuppType.ScheduledSupplier && selectedType == EnumSuppType.ScheduledSupplier) {
                            System.out.println("ℹ️ Supplier changed to Scheduled. Please set work days.");
                            ArrayList<Integer> newWorkDays = new ArrayList<>();
                            boolean addingDays = true;
                            while (addingDays) {
                                System.out.print("Enter a work day (1-7): ");
                                int day = Integer.parseInt(scanner.nextLine());
                                if (day < 1 || day > 7) {
                                    System.out.println("Invalid day. Must be 1-7.");
                                    continue;
                                }
                                newWorkDays.add(day);
                                System.out.print("Add another day? (yes/no): ");
                                if (scanner.nextLine().equalsIgnoreCase("no")) {
                                    service.setWorkDays(updateId, newWorkDays);
                                    addingDays = false;
                                }
                            }
                        }
                        break;
                }
            case "8":
                System.out.print("Supplier ID: ");
                int supId = Integer.parseInt(scanner.nextLine());
                if (service.getSupplier(supId) == null) {
                    System.out.println("❌ Supplier not found.");
                    break;
                }

                System.out.println("1. Add Company");
                System.out.println("2. Delete Company");
                System.out.println("3. Show Companies");
                System.out.print("➤ Option: ");
                String companyOption = scanner.nextLine();

                switch (companyOption) {
                    case "1":
                        System.out.print("Enter Company Name to Add: ");
                        String newCompany = scanner.nextLine();
                        System.out.println(service.addCompany(supId, newCompany));
                        break;

                    case "2":
                        System.out.print("Enter Company Name to Delete: ");
                        String delCompany = scanner.nextLine();
                        String result = service.deleteCompany(supId, delCompany);
                        System.out.println(result);
                        break;

                    case "3":
                        ArrayList<String> companies = service.getCompanies(supId);
                        if (companies == null || companies.isEmpty()) {
                            System.out.println("📭 No companies assigned to this supplier.");
                        } else {
                            System.out.println("🏢 Supplier Companies:");
                            companies.forEach(c -> System.out.println(" - " + c));
                        }
                        break;

                    default:
                        System.out.println("❌ Invalid company option.");
                }
        }
    }


    private static void handleContractMenu(Scanner scanner, Service service) {
        System.out.println("\n📜 Contract Menu:");
        System.out.println("1. View All Contracts");
        System.out.println("2. Make a New Contract");
        System.out.println("3. Delete a Contract");
        System.out.print("➤ Option: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":
                service.displayAllContracts();
                break;

            case "2":
                System.out.print("Enter Contract Name (ID): ");
                String contractName = scanner.nextLine();

                if (service.getContract(contractName) != null) {
                    System.out.println("⚠️ Contract already exists! Returning to menu...");
                    break;
                }

                System.out.print("Enter Supplier ID for this contract: ");
                int supplierId = Integer.parseInt(scanner.nextLine());
                Supplier supplier = service.getSupplier(supplierId);

                if (supplier == null) {
                    System.out.println("❌ Supplier not found. Returning to menu...");
                    break;
                }

                System.out.println("📦 Supplier Products:");
                Map<Integer, Product> supplierProducts = supplier.getSuppProducts();
                if (supplierProducts.isEmpty()) {
                    System.out.println("⚠️ Supplier has no products.");
                    break;
                }
                supplierProducts.values().forEach(Product::display);

                EnumSuppType supplierType = supplier.getSuppType();
                ArrayList<WorkDays> workDays = supplier.getSuppWorkDays();
                System.out.println(service.createContract(supplierId, contractName, supplierType, workDays));
                Contract newContract = service.getContract(contractName);
                if (newContract != null) {
                    supplier.addContract(newContract);
                    System.out.println("✅ Contract linked to the supplier.");
                }

                boolean addingProducts = true;
                while (addingProducts) {
                    System.out.print("Enter Product ID you want to add to contract: ");
                    int productId = Integer.parseInt(scanner.nextLine());

                    if (!supplierProducts.containsKey(productId)) {
                        System.out.println("❌ Supplier does not have this product.");
                        continue;
                    }

                    Product product = supplierProducts.get(productId);

                    if (service.isProductInContract(contractName, product.getProductId()) == true) {
                        System.out.println("⚠️ This product is already added to the contract.");
                        continue;
                    }

                    System.out.print("Enter quantity for discount: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    System.out.print("Enter discount percentage (e.g., 10 for 10%): ");
                    int discount = Integer.parseInt(scanner.nextLine());

                    System.out.println(service.defineDiscount(contractName, product, quantity, discount));

                    System.out.print("➤ Do you want to add another product? (yes/no): ");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        addingProducts = false;
                    }
                }

                System.out.println("✅ Contract setup completed. Returning to main menu...");
                break;

            case "3":
                System.out.print("Enter Contract Name (ID) to delete: ");
                String contractToDelete = scanner.nextLine();

                if (service.getContract(contractToDelete) == null) {
                    System.out.println("❌ Contract not found. Returning to menu...");
                    break;
                }
                Supplier s = service.getSupplier(service.getContract(contractToDelete).getSupplierId());
                s.deletContract(service.getContract(contractToDelete));
                System.out.println(service.deleteContract(contractToDelete));
                break;

            default:
                System.out.println("❌ Invalid input.");
        }
    }


    private static void handleOrderMenu(Scanner scanner, Service service) {
        System.out.println("\n🧾 Order Menu:");
        System.out.println("Please choose what to do:");
        System.out.println("1. Add a New Order (Based on Contract)");
        System.out.println("2. Delete Order");
        System.out.println("3. View Order");
        System.out.println("0. Exit");
        System.out.print("➤ Option: ");
        String input = scanner.nextLine();

        switch (input) {
            case "1":

                System.out.println("\n🏢 All Suppliers:");
                ArrayList<Supplier> suppliers = service.getAllSuppliers();
                if (suppliers.isEmpty()) {
                    System.out.println("📭 No suppliers available.");
                    break;
                }
                suppliers.forEach(Supplier::display);

                System.out.print("Enter Supplier ID you want to order from: ");
                int supplierId = Integer.parseInt(scanner.nextLine());
                Supplier supplier = service.getSupplier(supplierId);

                if (supplier == null) {
                    System.out.println("❌ Supplier not found.");
                    break;
                }


                ArrayList<Contract> contracts = supplier.getSuppContracts();
                if (contracts.isEmpty()) {
                    System.out.println("❌ This supplier has no contracts. Cannot proceed.");
                    break;
                }

                System.out.println("\n📜 Contracts for Supplier:");
                for (Contract contract : contracts) {
                    contract.display();
                }

                System.out.print("Enter Contract Name you want to base the order on: ");
                String contractName = scanner.nextLine();
                Contract selectedContract = service.getContract(contractName);

                if (selectedContract == null || selectedContract.getSupplierId() != supplierId) {
                    System.out.println("❌ Contract not found or doesn't belong to this supplier.");
                    break;
                }

                System.out.print("Enter Payment Method (1. Cash, 2. Credit, 3. BankTransfer): ");
                String paymentInput = scanner.nextLine();
                EnumPayment paymentMethod = null;
                switch (paymentInput) {
                    case "1" -> paymentMethod = EnumPayment.cash;
                    case "2" -> paymentMethod = EnumPayment.credit;
                    case "3" -> paymentMethod = EnumPayment.banktransfer;
                    default -> {
                        System.out.println("❌ Invalid payment method. Returning...");
                        break;
                    }
                }

                Order order = service.createOrder(supplierId, paymentMethod.toString());
                if (order == null) {
                    System.out.println("❌ Failed to create order.");
                    break;
                }
                System.out.println("✅ Order created with ID: " + order.getOrderId());

                boolean addingProducts = true;
                Map<Product, Map<Integer, Integer>> contractProducts = selectedContract.QuantityDiscountPerProduct;

                if (contractProducts.isEmpty()) {
                    System.out.println("⚠️ No products under this contract to add.");
                    break;
                }

                while (addingProducts) {
                    System.out.println("\n📦 Products available under Contract:");
                    for (Product prod : contractProducts.keySet()) {
                        prod.display();
                    }

                    System.out.print("Enter Product ID to add: ");
                    int productId = Integer.parseInt(scanner.nextLine());
                    Product selectedProduct = null;
                    for (Product prod : contractProducts.keySet()) {
                        if (prod.getProductId() == productId) {
                            selectedProduct = prod;
                            break;
                        }
                    }

                    if (selectedProduct == null) {
                        System.out.println("❌ Product not found in this contract.");
                        continue;
                    }

                    System.out.print("Enter Quantity: ");
                    int quantity = Integer.parseInt(scanner.nextLine());

                    try {
                        order.addProduct(selectedProduct, quantity);
                        System.out.println("✅ Product added to the order.");
                    } catch (Exception e) {
                        System.out.println("❌ " + e.getMessage());
                    }

                    System.out.print("➤ Add another product? (yes/no): ");
                    if (scanner.nextLine().equalsIgnoreCase("no")) {
                        addingProducts = false;
                    }
                }

                System.out.println("✅ Order completed! Returning to menu...");
                break;

            case "2":
                System.out.print("Enter Order ID to delete: ");
                int deleteOrderId = Integer.parseInt(scanner.nextLine());

                if (service.getOrder(deleteOrderId) == null) {
                    System.out.println("❌ Order not found.");
                } else {
                    System.out.println(service.deleteOrder(deleteOrderId));
                }
                break;

            case "3":
                System.out.print("Enter Order ID to view: ");
                int viewOrderId = Integer.parseInt(scanner.nextLine());

                Order viewOrder = service.getOrder(viewOrderId);
                if (viewOrder == null) {
                    System.out.println("❌ Order not found.");
                } else {
                    viewOrder.displayOrder();
                }
                break;

            case "0":
                System.out.println("🔙 Returning to Main Menu...");
                break;

            default:
                System.out.println("❌ Invalid input.");
        }
    }

    private static void showSystemData(Service service) {
        System.out.println("\n📊 Full System Data Snapshot:");

        System.out.println("\n🏢 Suppliers:");
        ArrayList<Supplier> suppliers = service.getAllSuppliers();
        if (suppliers.isEmpty()) {
            System.out.println("   None.");
        } else {
            suppliers.forEach(Supplier::display);
        }

        System.out.println("\n📜 Contracts:");
        service.displayAllContracts();

        System.out.println("\n🧾 Orders:");
        ArrayList<Order> orders = service.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("   None.");
        } else {
            orders.forEach(Order::displayOrder);
        }

        System.out.println("✅ End of Data Snapshot.");
    }

    private static void loadDemoData(Service service) {
        try {
            BankDetails bank1 = new BankDetails("Hapoalim", "Tel Aviv", 123);
            BankDetails bank2 = new BankDetails("Leumi", "Haifa", 456);

            ArrayList<Integer> contacts1 = new ArrayList<>();
            ArrayList<Integer> contacts2 = new ArrayList<>();

            service.addSupplier(1, "Supplier A", "Tel Aviv", "0501111111", bank1, contacts1);
            service.addSupplier(2, "Supplier B", "Haifa", "0502222222", bank2, contacts2);

            service.setSupplierType(1, 1);
            service.setSupplierType(2, 2);

            ArrayList<Integer> workDays = new ArrayList<>();
            workDays.add(1);
            workDays.add(3);
            workDays.add(5);
            service.setWorkDays(1, workDays);

            service.addProductToSupplier(1, 101, "Milk", 5.5);
            service.addProductToSupplier(1, 102, "Bread", 7.0);
            service.addProductToSupplier(2, 201, "Paper", 10.0);
            service.addProductToSupplier(2, 202, "Pen", 2.5);

            service.addCompany(1, "Tnuva");
            service.addCompany(2, "Office Depot");

            service.addContact(1, 11, "David", 1111111, "Tel Aviv");
            service.addContact(2, 22, "Rami", 2222222, "Haifa");

            Supplier supplier1 = service.getSupplier(1);
            if (supplier1 != null) {
                EnumSuppType supplierType = supplier1.getSuppType();
                ArrayList<WorkDays> supplierDays = supplier1.getSuppWorkDays();

                service.createContract(1, "C1", supplierType, supplierDays);

                Contract contract = service.getContract("C1");
                if (contract != null) {
                    supplier1.addContract(contract);

                    Product milk = supplier1.getSuppProducts().get(101);
                    Product bread = supplier1.getSuppProducts().get(102);

                    if (milk != null) {
                        service.defineDiscount("C1", milk, 10, 5);
                    }
                    if (bread != null) {
                        service.defineDiscount("C1", bread, 20, 10);
                    }
                }

                Order order = service.createOrder(1, "cash");
                if (order != null) {
                    Product milk = supplier1.getSuppProducts().get(101);
                    if (milk != null) {
                        order.addProduct(milk, 5);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("⚠️ Failed to load demo data.");
        }
    }
}