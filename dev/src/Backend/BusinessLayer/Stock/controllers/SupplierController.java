//package BusinessLayer;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class SupplierController {
//    private static SupplierController instance= new SupplierController();
//    private int supplierId;
//    private int productId;
//    private ArrayList<Supplier> suppliers;
//    private HashMap<Supplier, Integer> suppliers_id ;
//    private HashMap<Integer, Product> products_id ;
//    private HashMap<String, Supplier> suppliers_phoneNumber ;
//
//    public SupplierController() {
//        this.suppliers = new ArrayList<>();
//        this.suppliers_id = new HashMap<>();
//        this.products_id = new HashMap<>();
//        this.suppliers_phoneNumber = new HashMap<>();
//    }
//
//    public static SupplierController getInstance(){
//        return instance;
//    }
//
//    public String toString(String phoneNumber){
//        //System.out.println(suppliers_phoneNumber);
//        return suppliers_phoneNumber.get(phoneNumber).toString();
//    }
//
//    public Supplier AddSupplier( String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType,ArrayList<String> companies) throws IllegalAccessException {
//        if (name==null || phoneNumber==null || BankAccount==null || paymentType == null ){
//            throw new IllegalArgumentException("Data supplier cannot be empty");
//        }
//        if(intSupplierType < 1 || intSupplierType >3){
//            throw new IllegalArgumentException("Supplier type must be 1 or 2 or 3");
//        }
//        if(companies == null){
//            throw new IllegalArgumentException("Companies cannot be empty");
//        }
//        Supplier supplier1 = new Supplier(name,phoneNumber,intSupplierType,BankAccount, paymentType, companies);
//       // System.out.println(suppliers);
//        suppliers.add(supplier1);
//        suppliers_phoneNumber.put(phoneNumber,supplier1);
//        suppliers_id.put(supplier1, supplierId++);
//        return supplier1;
//    }
//
//    public String DeleteSupplier(String phoneNumber) throws IllegalAccessException {
//        if(suppliers_phoneNumber.containsKey(phoneNumber)){
//            Supplier supplier = suppliers_phoneNumber.get(phoneNumber);
//            suppliers.remove(supplier);
//            suppliers_phoneNumber.remove(phoneNumber);
//            suppliers_id.remove(supplier);
//            return "Supplier removed successfully";
//        }else{
//            throw new IllegalAccessException("There is no supplier with this phone number");
//        }
//
//    }
//
//    public String AddContact(String phoneNumber,int contact_id, String name, String phone_number, String email) throws IllegalAccessException {
//        if(contact_id == 0 || name==null || phone_number==null || email==null){
//            throw new IllegalAccessException("Data contact cannot be empty");
//        }
//        Supplier supplier= suppliers_phoneNumber.get(phoneNumber);
//        supplier.addcontact(contact_id,name,phone_number,email);
//        return "Contact added successfully";
//    }
//
//    public boolean AddProduct(int price, String product_name,int pid) throws IllegalAccessException {
//        if(price==0 || product_name==null){
//            throw new IllegalAccessException("Data product cannot be empty");
//        }
//        if(products_id.containsKey(pid)){
//            return false;
//        }
//        Product product=new Product(pid, price, product_name);
//        products_id.put(pid, product);
//        return true;
//    }
//
//    public String AddCompany(String phoneNumber, String companyName) throws IllegalAccessException {
//        if( phoneNumber==null || companyName == null){
//            throw new IllegalAccessException("Data incorrect");
//        }
//        Supplier supplier= suppliers_phoneNumber.get(phoneNumber);
//        supplier.addcompany(companyName);
//        return "Company added succeffully";
//    }
//
//    public String DeleteCompany(String phoneNumber, String companyName) throws IllegalAccessException {
//        if( phoneNumber==null || companyName == null){
//            throw new IllegalAccessException("Data incorrect");
//        }
//        Supplier supplier= suppliers_phoneNumber.get(phoneNumber);
//        supplier.deletecompany(companyName);
//        return "Company deleted succeffully";
//    }
//
//    public ArrayList<Supplier> getAllSuppliers(){
//        return suppliers;
//    }
//    public Supplier getSupplier(String phoneNumber) throws IllegalAccessException {
//        if(suppliers_phoneNumber.containsKey(phoneNumber)){
//            return suppliers_phoneNumber.get(phoneNumber);
//        }
//        else{
//            throw new IllegalAccessException("There is no supplier with this phone number in the system");
//        }
//    }
//
//    public ArrayList<Contact> getContacts(String phoneNumber) throws IllegalAccessException {
//        return getSupplier(phoneNumber).getContacts();
//    }
//
//    public ArrayList<String> getCompanies(String phoneNumber) throws IllegalAccessException {
//        return getSupplier(phoneNumber).getCompanies();
//    }
//
//    public Contract getContract(String phoneNumber) throws IllegalAccessException {
//        return getSupplier(phoneNumber).getContract();
//    }
//
//    public HashMap<Integer, Product> getAllProducts(){
//        return products_id;
//    }
//
//}
package Backend.BusinessLayer.Stock.controllers;

import Backend.DataAccessLayer.*;
import Backend.BusinessLayer.Stock.objects.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SupplierController {
    private static SupplierController instance = new SupplierController();
    private int supplierIdCounter = 1;
    private int productIdCounter = 1;
    public ArrayList<Supplier> suppliers;
    public HashMap<Integer, Supplier> suppliersById;
    public HashMap<Integer, Product1> productsById;

    public SupplierController() {
        this.suppliers = new ArrayList<>();
        this.suppliersById = new HashMap<>();
        this.productsById = new HashMap<>();
    }

    DataBaseController dataBaseController=DataBaseController.getInstance();

    public static SupplierController getInstance() {
        return instance;
    }

    public String toString(int supplierId) throws IllegalAccessException {
        Supplier supplier = getSupplier(supplierId);
        return supplier.toString();
    }

    public Supplier AddSupplier(int supplierId, String name, String phoneNumber, int intSupplierType, String BankAccount, String paymentType, ArrayList<String> companies) throws IllegalArgumentException, SQLException {
        if (name == null || phoneNumber == null || BankAccount == null || paymentType == null || companies == null) {
            throw new IllegalArgumentException("Supplier data cannot be empty");
        }
        if (intSupplierType < 1 || intSupplierType > 3) {
            throw new IllegalArgumentException("Supplier type must be 1, 2, or 3");
        }

        Supplier supplier = new Supplier(supplierId,name, phoneNumber, intSupplierType, BankAccount, paymentType, companies);
        suppliers.add(supplier);
        suppliersById.put(supplier.getSupplier_id(), supplier);
        try {
            dataBaseController.addSupplier(supplier);

        } catch (Exception e) {
            // TODO: handle exception
        }
        return supplier;
    }

    public String DeleteSupplier(int supplierId) throws IllegalAccessException, SQLException {
        if (suppliersById.containsKey(supplierId)) {
            Supplier supplier = suppliersById.get(supplierId);
            suppliers.remove(supplier);
            suppliersById.remove(supplierId);
            dataBaseController.delete(supplier.getSupplier_id());
            return "Supplier removed successfully";
        } else {
            throw new IllegalAccessException("There is no supplier with this ID");
        }
    }

    public String AddContact(int supplierId, int contactId, String name, String phoneNumber, String email) throws IllegalAccessException, SQLException {
        if (contactId == 0 || name == null || phoneNumber == null || email == null) {
            throw new IllegalAccessException("Contact data cannot be empty");
        }
        try {
            
        
        Supplier supplier = getSupplier(supplierId);
        supplier.addcontact(contactId, name, phoneNumber, email);
        Contact contact= new Contact(contactId, name, phoneNumber, email);
        ContactDTO contactDTO= contact.objectDTOContact(supplierId);
        dataBaseController.addContact(contactDTO);
        dataBaseController.insert_supplier_contact(supplierId, contactId);
        return "Contact added successfully";
    } catch (Exception e) {
        // TODO: handle exception
    }
    try {
        dataBaseController.insert_supplier_contact(supplierId, contactId);

    } catch (Exception e) {
        // TODO: handle exception
    }

        return email;
    }

    public boolean AddProduct(int price, String productName, int productId) throws IllegalAccessException, SQLException {
        if (price == 0 || productName == null) {
            throw new IllegalAccessException("Product data cannot be empty");
        }
        if (productsById.containsKey(productId)) {
            return false;
        }
        try {
            
       
        Product1 product = new Product1(productId, price, productName);
        productsById.put(productId, product);
        dataBaseController.insert_product(product.objectDTOProduct());
        return true;
    } catch (Exception e) {
return true;    }
    }

    public String AddCompany(int supplierId, String companyName) throws IllegalAccessException {
        if (companyName == null) {
            throw new IllegalAccessException("Company data cannot be empty");
        }
        Supplier supplier = getSupplier(supplierId);
        supplier.addcompany(companyName);
        return "Company added successfully";
    }

    public String DeleteCompany(int supplierId, String companyName) throws IllegalAccessException {
        if (companyName == null) {
            throw new IllegalAccessException("Company data cannot be empty");
        }
        Supplier supplier = getSupplier(supplierId);
        supplier.deletecompany(companyName);
        return "Company deleted successfully";
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return suppliers;
    }

    public Supplier getSupplier(int supplierId) throws IllegalAccessException {
        if (suppliersById.containsKey(supplierId)) {
            return suppliersById.get(supplierId);
        } else {
            throw new IllegalAccessException("There is no supplier with this ID in the system");
        }
    }

    public ArrayList<Contact> getContacts(int supplierId) throws IllegalAccessException {
        return getSupplier(supplierId).getContacts();
    }

    public ArrayList<String> getCompanies(int supplierId) throws IllegalAccessException {
        return getSupplier(supplierId).getCompanies();
    }

    public ArrayList<Contract> getContract(int supplierId) throws IllegalAccessException {
        return getSupplier(supplierId).getContracts();
    }

    public HashMap<Integer, Product1> getAllProducts() {
        return productsById;
    }

    public Product1 getProductById(Integer productId){
        Product1 p=productsById.get(productId);
        return p;
    }


    public String editSupplier_name(int supplier_id,String name) throws Exception{
        if (!suppliers.contains(getSupplier(supplier_id))) {
            throw new Exception("This supplier id does not appear to be in the system!");
        }
        suppliers.get(supplier_id).setName(name);

        dataBaseController.update(suppliers.get(supplier_id).objectDTO());
        return "Name changed successfully!";
    }


    public String changeBankAccount(int supplier_id, String newBanckAcc) throws Exception {
        if (!suppliers.contains(getSupplier(supplier_id))) {
            throw new Exception("This in no working Suppliers for this company");
        }
        suppliers.get(supplier_id).setBankAccount(newBanckAcc);

        dataBaseController.update(suppliers.get(supplier_id).objectDTO());
        return "Bank Account changed successfully";
    }

    public String ChangePhoneNumber(int supp_id,String newPhone) throws Exception{
        if (!suppliers.contains(getSupplier(supp_id))) {
            throw new Exception("This in no working Suppliers for this company");
        }
        suppliers.get(supp_id).setPhoneNumber(newPhone);
        dataBaseController.update(suppliers.get(supp_id).objectDTO());
        return "PhoneNumber changed successfully!";
    }

    public String deleteAllsuplier() throws SQLException, IllegalAccessException {
        List<Supplier> suppliers1 = dataBaseController.selectAllSuppliers();
        for (Supplier s: suppliers1){
//            DeleteSupplier(s.getSupplier_id());
            dataBaseController.delete(s.getSupplier_id());
        }
        return "All suppliers deleted";
    }

    public String deleteAllcontact() throws SQLException, IllegalAccessException {
        List<Contact> contacts1= dataBaseController.selectAllContacts();
        for (Contact c: contacts1){
//            DeleteSupplier(s.getSupplier_id());
            dataBaseController.deleteContact(c.getContact_id());
        }
        return "All contacts deleted";
    }

    public String deleteAllProducts() throws SQLException, IllegalAccessException {
        List<Product1> products= dataBaseController.selectAllProducts();
        for (Product1 p: products){
//            DeleteSupplier(s.getSupplier_id());
            dataBaseController.delete_Product(p.getProduct_id());
        }
        return "All products deleted";
    }


}
