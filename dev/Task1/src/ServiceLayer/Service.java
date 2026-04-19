package ServiceLayer;

import BusinessLayer.*;

import java.util.*;

public class Service {
    private SupplierService supplierService;
    private OrderService orderService;
    private ContractService contractService;

    public Service() {
        this.supplierService = new SupplierService();
        this.orderService = new OrderService(supplierService.getSuppliersController());
        this.contractService = new ContractService();
    }

    // 📦 SUPPLIER

    public String addSupplier(int id, String name, String address, String phone, BankDetails bank, ArrayList<Integer> contacts) {
        return supplierService.addSupplier(id, name, address, phone, bank, contacts);
    }
    public boolean isProductInContract(String ContractName, int productId) {
        return contractService.IsProductInCon(ContractName,productId);
    }
    public String deleteContract(String contractName) {
        return contractService.deleteContract(contractName);
    }

    public String deleteSupplier(int id) {
        return supplierService.deleteSupplier(id);
    }

    public String makeSuppBank(String bankname, String BaqnkBranch, int BankNum){
        return supplierService.makeSupplierBank1(bankname,BaqnkBranch,BankNum);
    }
    public String addProductToSupplier(int supplierId, int productId, String name, double price) {
        return supplierService.addProduct(supplierId, productId, name, price);
    }

    public String removeProductFromSupplier(int supplierId, int productId) {
        return supplierService.removeProduct(supplierId, productId);
    }

    public String addContact(int supplierId, int contactId, String name, int phone, String address) {
        return supplierService.addContact(supplierId, contactId, name, phone, address);
    }

    public String deleteContact(int supplierId, int phone) {
        return supplierService.deleteContact(supplierId, phone);
    }

    public String addCompany(int supplierId, String companyName) {
        return supplierService.addCompany(supplierId, companyName);
    }

    public String deleteCompany(int supplierId, String companyName) {
        return supplierService.deleteCompany(supplierId, companyName);
    }

    public ArrayList<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }

    public Supplier getSupplier(int id) {
        return supplierService.getSupplier(id);
    }

    public String setSupplierType(int supplierId, int type) {
        return supplierService.setSupplierType(supplierId, type);
    }

    public String setWorkDays(int supplierId, ArrayList<Integer> days) {
        return supplierService.setWorkDays(supplierId, days);
    }

    // 📜 CONTRACT

    public String createContract(int supplierId, String contractName, EnumSuppType method, ArrayList<WorkDays> days) {
        return contractService.createContract(supplierId, contractName, method, days);
    }

    public String addProductToContract(String contractName, Product product) {
        return contractService.addProductToContract(contractName, product);
    }

    public String defineDiscount(String contractName, Product product, int quantity, int discount) {
        return contractService.defineDiscount(contractName, product, quantity, discount);
    }

    public String displayContract(String contractName) {
        return contractService.displayContract(contractName);
    }

    public String displayAllContracts() {
        return contractService.displayAllContracts();
    }

    public Contract getContract(String contractName) {
        return contractService.getContract(contractName);
    }

    // 🧾 ORDER

    public Order createOrder(int supplierId, String paymentType) {
        return orderService.createOrder(supplierId, paymentType);
    }

    public String addProductToOrder(int orderId, int productId, int quantity) {
        return orderService.addProductToOrder(orderId, productId, quantity);
    }
    public ArrayList<Integer> getcontacts(int id) {
        return supplierService.getContactss(id);
    }
    public ArrayList<String> getCompanies(int id) {
        return supplierService.companies(id);
    }
    public String markOrderAsDone(int orderId) {
        return orderService.markOrderAsDone(orderId);
    }

    public String deleteOrder(int orderId) {
        return orderService.deleteOrder(orderId);
    }

    public Order getOrder(int orderId) {
        return orderService.getOrder(orderId);
    }

    public ArrayList<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    public void AddSupplierworkDay(int id , int day){
        supplierService.addsuppday(id, day);
    }
    public void DeleteSupplierworkDay(int id , int day){
        supplierService.deleteSupday(id, day);
    }

    // 🔄 Access Controllers (if needed)
    public SupplierControler getSuppliersController() {
        return supplierService.getSuppliersController();
    }
    public OrderController getOrderController() {
        return orderService.getOrderController();
    }

    public ContractController getContractController() {
        return contractService.getContractController();
    }
}
