//package ServiceLayer;
//
//import BusinessLayer.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class SupplierService {
//    public SupplierController supplierController= new SupplierController();
//
//    public SupplierController getSuppliersController() {
//        return supplierController;
//    }
//
//    public SupplierService() {
//
//    }
//
//    public Supplier AddSupplier( String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType, ArrayList<String> companies) {
//        try {
//            Supplier output = supplierController.AddSupplier( name, phoneNumber, intSupplierType, BankAccount, paymentType, companies);
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public String DeleteSupplier(String phoneNumber) {
//        try {
//            String output = supplierController.DeleteSupplier(phoneNumber);
//            return output;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
//
//    public String AddContact(String phoneNumber,int contact_id, String name, String phone_number, String email) {
//        try {
//            String output = supplierController.AddContact( phoneNumber,contact_id,name,phone_number,email);
//            return output;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
//
//    public boolean AddProduct(int price, String product_name,int pid) {
//        try {
//            return supplierController.AddProduct(price, product_name,pid);
//        }catch (Exception e) {return false;}
//    }
//
//    public String AddCompany(String phoneNumber, String companyName){
//        try {
//            String output = supplierController.AddCompany( phoneNumber, companyName);
//            return output;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
//
//    public String DeleteCompany(String phoneNumber, String companyName){
//        try {
//            String output = supplierController.DeleteCompany(phoneNumber, companyName);
//            return output;
//        } catch (Exception e) {
//            return e.getMessage();
//        }
//    }
//
//    public ArrayList<Supplier> getAllSuppliers() {
//        try {
//            ArrayList<Supplier> output = supplierController.getAllSuppliers();
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public Supplier getSupplier(String phoneNumber){
//        try {
//            Supplier output = supplierController.getSupplier(phoneNumber);
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//    public HashMap<Integer, Product> getAllProducts(){
//        try {
//            HashMap<Integer, Product> output = supplierController.getAllProducts();
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public ArrayList<Contact> getContacts(String phoneNumber){
//        try {
//            ArrayList<Contact> output = supplierController.getContacts(phoneNumber);
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public String toString(String phoneNumber){
//        try {
//            String output = supplierController.toString(phoneNumber);
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public Contract getContract(String phoneNumber){
//        try {
//            Contract output = supplierController.getContract(phoneNumber);
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//
//    public ArrayList<String> getCompanies(String phoneNumber){
//        try {
//            ArrayList<String> output = supplierController.getCompanies(phoneNumber);
//            return output;
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//
//
//
//}
package Backend.ServiceLayer.Backend.ServiceLayer;

import java.util.ArrayList;
import java.util.HashMap;


import Backend.BusinessLayer.Stock.objects.*;
import Backend.BusinessLayer.Stock.controllers.*;

public class SupplierService {
    public SupplierController supplierController = SupplierController.getInstance();

    public SupplierController getSuppliersController() {
        return supplierController;
    }

    public SupplierService() {

    }

    public Supplier AddSupplier(int id,String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType, ArrayList<String> companies) {
        try {
            Supplier output = supplierController.AddSupplier(id,name, phoneNumber, intSupplierType, BankAccount, paymentType, companies);
            return output;
        } catch (Exception e) {
System.out.println(e.getMessage());        }
        return null;
    }

    public String DeleteSupplier(int supplierId) {
        try {
            String output = supplierController.DeleteSupplier(supplierId);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteAllsuplier() {
        try {
            String output = supplierController.deleteAllsuplier();
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String deleteAllcontacts() {
        try {
            String output = supplierController.deleteAllcontact();
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public String deleteAllProducts() {
        try {
            String output = supplierController.deleteAllProducts();
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }



    public String AddContact(int supplierId, int contact_id, String name, String phone_number, String email) {
        try {
            String output = supplierController.AddContact(supplierId, contact_id, name, phone_number, email);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean AddProduct(int price, String product_name, int pid) {
        try {
            return supplierController.AddProduct(price, product_name, pid);
        } catch (Exception e) {
            return false;
        }
    }

    public String AddCompany(int supplierId, String companyName) {
        try {
            String output = supplierController.AddCompany(supplierId, companyName);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String DeleteCompany(int supplierId, String companyName) {
        try {
            String output = supplierController.DeleteCompany(supplierId, companyName);
            return output;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public ArrayList<Supplier> getAllSuppliers() {
        try {
            ArrayList<Supplier> output = supplierController.getAllSuppliers();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public Supplier getSupplier(int supplierId) {
        try {
            Supplier output = supplierController.getSupplier(supplierId);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public HashMap<Integer, Product1> getAllProducts() {
        try {
            HashMap<Integer, Product1> output = supplierController.getAllProducts();
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Contact> getContacts(int supplierId) {
        try {
            ArrayList<Contact> output = supplierController.getContacts(supplierId);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public String toString(int supplierId) {
        try {
            String output = supplierController.toString(supplierId);
            return output;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<Contract> getContract(int supplierId) {
        try {
            ArrayList<Contract> contracts = supplierController.getContract(supplierId);
            return contracts;
        } catch (Exception e) {
            return null;
        }
    }

    public ArrayList<String> getCompanies(int supplierId) {
        try {
            ArrayList<String> output = supplierController.getCompanies(supplierId);
            return output;
        } catch (Exception e) {
            return null;
        }
    }
}
