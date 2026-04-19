package BusinessLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SupplierControler {
    private Map<Integer, Supplier> suppliers;

    public SupplierControler() {
        this.suppliers = new HashMap<>();
    }

    public void createSupplier(int id, String name, String address, String phone, BankDetails bankDetails, ArrayList<Integer> contacts) {
        if (suppliers.containsKey(id)) {
            throw new IllegalArgumentException("⚠️ Supplier ID already exists.");
        }
        Supplier newSupplier = new Supplier(id, name, address, phone, bankDetails, contacts);
        suppliers.put(id, newSupplier);
    }

    public void deleteSupplier(int id) {
        if (!suppliers.containsKey(id)) {
            System.out.println("⚠️ Supplier not found.");
            return;
        }
        suppliers.remove(id);
        System.out.println("✅ Supplier deleted.");
    }
    public BankDetails AddSupplierBank(String bankname,String BaqnkBranch,int BankNum){
        BankDetails newBank = new BankDetails(bankname,BaqnkBranch,BankNum);
        if(newBank == null){
            return null;
        }
        return newBank;
    }

    public Supplier getSupplier(int id) {
        return suppliers.get(id);
    }

    public void displaySupplier(int id) {
        Supplier supplier = suppliers.get(id);
        if (supplier == null) {
            System.out.println("⚠️ Supplier not found.");
            return;
        }
        supplier.display();
        System.out.println("------------------------------------------------");
    }

    public void displayAllSuppliers() {
        if (suppliers.isEmpty()) {
            System.out.println("📭 No suppliers found.");
            return;
        }
        for (Supplier s : suppliers.values()) {
            s.display();
            System.out.println("------------------------------------------------");
        }
    }

    public void setSupplierType(int supplierId, int typeNum) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.setSupplierType(typeNum);
        }
    }

    public void setWorkDays(int supplierId, ArrayList<Integer> days) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.setWorkDays(days);
        }
    }
    public ArrayList<Integer> getContactsPhones(int id) {
        Supplier supplier = suppliers.get(id);
        return supplier.getContactsPhoneNum();
    }
    public void addProduct(int supplierId, int productId, String name, double price) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.AddProduct(productId, name, price);
        }
    }

    public void removeProduct(int supplierId, int productId) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.deleteProduct(productId);
        }
    }

    public void addContact(int supplierId, int contactId, String name, int phoneNumber, String address) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.AddContact(contactId, name, phoneNumber, address);
        }
    }
    public void deletday(int supplierId,int daytodelet) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.deleteworkday(daytodelet);
        }
    }
    public void addday(int supplierId,int daytodelet) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.addWorkday(daytodelet);
        }
    }
    public void deleteContact(int supplierId, int phoneNumber) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.DeleteContact(phoneNumber);
        }
    }

    public void addCompany(int supplierId, String companyName) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.AddCompany(companyName);
        }
    }

    public void deleteCompany(int supplierId, String companyName) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.deleteCompany(companyName);
        }
    }

    public void makeContract(int supplierId, String contractName) {
        Supplier supplier = suppliers.get(supplierId);
        if (supplier != null) {
            supplier.MakeContract(contractName);
        }
    }
    public ArrayList<String> companies(int supId) {
        Supplier supplier = suppliers.get(supId);
        if (supplier != null) {
            return supplier.companies();
        }
        return null;
    }
    public Map<Integer, Supplier> getSuppliers() {
        return suppliers;
    }
}
