package ServiceLayer;

import BusinessLayer.*;

import java.util.ArrayList;
import java.util.HashMap;

public class SupplierService {
    private SupplierControler supplierController = new SupplierControler();

    public SupplierControler getSuppliersController() {
        return supplierController;
    }

    public String addSupplier(int id, String name, String address, String phoneNumber, BankDetails bankDetails, ArrayList<Integer> contacts) {
        try {
            supplierController.createSupplier(id, name, address, phoneNumber, bankDetails, contacts);
            return "✅ Supplier created successfully.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String deleteSupplier(int id) {
        try {
            supplierController.deleteSupplier(id);
            return "✅ Supplier deleted.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String makeSupplierBank1(String bankname, String BaqnkBranch, int BankNum) {
        try {
            supplierController.AddSupplierBank(bankname, BaqnkBranch, BankNum);
            return "✅ Bank added.";
        } catch (Exception e) {
            return "✅ couldnt make bank .";
        }
    }

    public String addProduct(int supplierId, int productId, String name, double price) {
        try {
            supplierController.addProduct(supplierId, productId, name, price);
            return "✅ Product added.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String removeProduct(int supplierId, int productId) {
        try {
            supplierController.removeProduct(supplierId, productId);
            return "✅ Product removed.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String addContact(int supplierId, int contactId, String name, int phoneNumber, String address) {
        try {
            supplierController.addContact(supplierId, contactId, name, phoneNumber, address);
            return "✅ Contact added.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }
    public ArrayList<String> companies(int supId) {
        try {
            return supplierController.companies(supId);
        } catch (Exception e) {
            return null;
        }
    }

    public String deleteContact(int supplierId, int phoneNumber) {
        try {
            supplierController.deleteContact(supplierId, phoneNumber);
            return "✅ Contact deleted.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String addCompany(int supplierId, String companyName) {
        try {
            supplierController.addCompany(supplierId, companyName);
            return "✅ Company added.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String deleteCompany(int supplierId, String companyName) {
        try {
            supplierController.deleteCompany(supplierId, companyName);
            return "✅ Company removed.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String setSupplierType(int supplierId, int typeNum) {
        try {
            supplierController.setSupplierType(supplierId, typeNum);
            return "✅ Type set.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }
    public String addsuppday(int supplierId, int day) {
        try {
            supplierController.addday(supplierId, day);
            return "✅ Type set.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }
    public String deleteSupday(int supplierId, int day) {
        try {
            supplierController.deletday(supplierId, day);
            return "✅ Type set.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }

    public String setWorkDays(int supplierId, ArrayList<Integer> days) {
        try {
            supplierController.setWorkDays(supplierId, days);
            return "✅ Work days set.";
        } catch (Exception e) {
            return "❌ " + e.getMessage();
        }
    }
    public ArrayList<Integer> getContactss(int id) {
        try {
            return new ArrayList<>(supplierController.getContactsPhones(id));
        } catch (Exception e) {
            return null;
        }
    }
    public ArrayList<Supplier> getAllSuppliers() {
        try {
            return new ArrayList<>(supplierController.getSuppliers().values());
        } catch (Exception e) {
            return null;
        }
    }

    public Supplier getSupplier(int id) {
        try {
            return supplierController.getSupplier(id);
        } catch (Exception e) {
            return null;
        }
    }
}
